// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParseException;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DoNothing;
import frc.robot.filters.FilterDeadband;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.utils.FileBasedTrajectory;
import frc.robot.utils.FilteredJoystick;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private enum AutoCommands {
    DoNothing, DriveSpline
  };

  private SendableChooser<AutoCommands> autoCommandSelector = new SendableChooser<AutoCommands>();

  private SendableChooser<Function<TrajectoryConfig, Trajectory>> splineCommandSelector = new SendableChooser<Function<TrajectoryConfig, Trajectory>>();

  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  // The driver's controller
  private final FilteredJoystick m_driverController = new FilteredJoystick(0);
  private final FilterDeadband m_deadbandThrottle = new FilterDeadband(0.05, -1.0);
  private final FilterDeadband m_deadbandTurn = new FilterDeadband(0.05, 1.0);

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);

  // private Trajectory m_trajectory;
  // private final String trajectoryJSON =
  // "/home/lvuser/deploy/paths/StartupPath1.wpilib.json";

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    configureShuffleboard();
    // Configure the button bindings
    configureButtonBindings();
    m_driverController.setFilter(Ports.OIDriverTurn, m_deadbandTurn);
    m_driverController.setFilter(Ports.OIDriverMove, m_deadbandThrottle);

    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    m_robotDrive.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new RunCommand(() -> m_robotDrive.arcadeDrive(m_driverController.getFilteredAxis(Ports.OIDriverMove),
            m_driverController.getFilteredAxis(Ports.OIDriverTurn)), m_robotDrive));
  }

  private void configureShuffleboard() {

    File f = new File(Constants.SPLINE_DIRECTORY);

    File[] pathNames = f.listFiles();
    // auto commands
    autoCommandSelector.setDefaultOption("DoNothing", AutoCommands.DoNothing);
    autoCommandSelector.addOption("DriveSpline", AutoCommands.DriveSpline);

    SmartDashboard.putData("Auto Chooser", autoCommandSelector);
    try {

      for (File pathname : pathNames) {
        // Print the names of files and directories
        System.out.println(pathname);
        splineCommandSelector.addOption(pathname.getName(), new FileBasedTrajectory(pathname));
      }
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    splineCommandSelector.addOption("builtIn", this::getTrajectory);
    SmartDashboard.putData(splineCommandSelector);

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * calling passing it to a {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    // Drive at half speed when the right bumper is held
    new JoystickButton(m_driverController, Button.kBumperRight.value).whenPressed(() -> m_robotDrive.setMaxOutput(0.5))
        .whenReleased(() -> m_robotDrive.setMaxOutput(1));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    var autoSelector = autoCommandSelector.getSelected();

    switch (autoSelector) {
    case DoNothing:
      return new DoNothing();

    case DriveSpline:
      var spline = splineCommandSelector.getSelected();
      return makeTrajectoryCommand(spline);
    }

    return new DoNothing();

  }

  private Command makeTrajectoryCommand(Function<TrajectoryConfig, Trajectory> trajectoryFunction) {
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
            DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics, 10);

    // Create config for trajectory
    TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(DriveConstants.kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow. All units in meters.

    /*
     * try { File deployDir = Filesystem.getDeployDirectory(); Path deployPathDir =
     * deployDir.toPath(); Path trajectoryPath =
     * deployPathDir.resolve(trajectoryJSON); m_trajectory =
     * TrajectoryUtil.fromPathweaverJson(trajectoryPath); } catch(IOException ex) {
     * System.out.println("No trajectory file found!" + ex); }
     */

    Trajectory exampleTrajectory = trajectoryFunction.apply(config);

    RamseteCommand ramseteCommand = new RamseteCommand(exampleTrajectory, m_robotDrive::getPose,
        new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
            DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics, m_robotDrive::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0), new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        m_robotDrive::tankDriveVolts, m_robotDrive);

    // Reset odometry to the starting pose of the trajectory.
    m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
  }

  private Trajectory getTrajectory(TrajectoryConfig config) {
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
        // List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        List.of(new Translation2d(2, 1), new Translation2d(3, -1)),
        // new Pose2d(3, 0, new Rotation2d(0)),
        new Pose2d(5, 0, new Rotation2d(0)), config);
    return exampleTrajectory;
  }

  public void AutoSelector() {
    switch (autoCommandSelector.getSelected()) {
    case DoNothing:
      System.out.println("Doing Nothing");
      break;
    case DriveSpline:
      System.out.println("Driving Spline");
      break;
    }
  }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
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
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.DisableShotCommand;
import frc.robot.commands.DoNothingCommand;
import frc.robot.commands.ExtendIntakeCommand;
import frc.robot.commands.LauncherSpeedCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.commands.SetHoodHighShotCommand;
import frc.robot.commands.SetHoodLowShotCommand;
import frc.robot.commands.StartIndexerCommand;
import frc.robot.commands.StopIndexerCommand;
import frc.robot.commands.TriggerShotCommand;
import frc.robot.subsystems.SK21BallIndexer;
import frc.robot.subsystems.SK21Drive;
import frc.robot.subsystems.SK21Intake;
import frc.robot.subsystems.SK21Launcher;
import frc.robot.subsystems.base.Dpad;
import frc.robot.subsystems.base.DpadDownButton;
import frc.robot.subsystems.base.DpadUpButton;
import frc.robot.subsystems.base.TriggerButton;
import frc.robot.utils.FilteredJoystick;
import frc.robot.utils.filters.FilterDeadband;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    public static UsbCamera camera;

    private enum AutoCommands
    {
        DoNothing, DriveSplineFromJSON, DriveSplineCanned
    };

    private SendableChooser<AutoCommands> autoCommandSelector = new SendableChooser<AutoCommands>();

    private SendableChooser<File> splineCommandSelector = new SendableChooser<File>();

    // The Robot controllers
    private final FilteredJoystick driverJoystick = new FilteredJoystick(0);
    private final FilterDeadband m_deadbandThrottle = new FilterDeadband(0.05, -1.0);
    private final FilterDeadband m_deadbandTurn = new FilterDeadband(0.05, 1.0);
    private final Joystick operatorJoystick = new Joystick(Ports.OIOperatorJoystick);
  
    // The robot's subsystems are defined here...
    private final SK21Drive m_driveSubsystem = new SK21Drive();
    private final SK21Launcher m_launcherSubsystem = new SK21Launcher();
    private final SK21BallIndexer m_ballIndexerSubsystem = new SK21BallIndexer();
    private final SK21Intake m_Intake = new SK21Intake();

    // Intake control buttons
    private final Dpad dpad = new Dpad(operatorJoystick);
    private final DpadUpButton extendIntakeButton = new DpadUpButton(dpad);
    private final DpadDownButton retractIntakeButton = new DpadDownButton(dpad);
    private final JoystickButton reverseIntake = new JoystickButton(operatorJoystick, Ports.OIOperatorReverseIntake);

    // Launcher control buttons
    private final JoystickButton launchBall = new JoystickButton(operatorJoystick, Ports.OIOperatorShootBall);
    private final JoystickButton setHighAngle = new JoystickButton(operatorJoystick, Ports.OIOperatorHighHoodAngle);
    private final JoystickButton setLowAngle = new JoystickButton(operatorJoystick, Ports.OIOperatorLowHoodAngle);
    private final JoystickButton toggleLauncherSpeed = new JoystickButton(operatorJoystick,
            Ports.OIOperatorSetLauncherSpeed);

    //Indexer control buttons
    private final TriggerButton startIndexer = new TriggerButton(operatorJoystick, Ports.OIOperatorActivateIBM);
    private final TriggerButton stopIndexer = new TriggerButton(operatorJoystick,Ports.OIOperatorDeactivateBMI);

    // TODO Climb Buttons
    // TODO Color wheel buttons
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
    public RobotContainer()
    {

        configureShuffleboard();

        // Configure the button bindings
        configureButtonBindings();
    
        driverJoystick.setFilter(Ports.OIDriverTurn, m_deadbandTurn);
        driverJoystick.setFilter(Ports.OIDriverMove, m_deadbandThrottle);

        // Configure default commands
        // Set the default drive command to split-stick arcade drive
        m_driveSubsystem.setDefaultCommand(new DefaultDriveCommand(m_driveSubsystem, driverJoystick));

        // Driver camera configuration.
        if (RobotBase.isReal()) {
            camera = CameraServer.getInstance().startAutomaticCapture("Driver Front Camera", 0);
            camera.setResolution(240, 240);
            camera.setFPS(15);
        }
    }

    private void configureShuffleboard()
    {
        // auto commands
        autoCommandSelector.setDefaultOption("Do Nothing", AutoCommands.DoNothing);
        autoCommandSelector.addOption("Drive path from JSON", AutoCommands.DriveSplineFromJSON);
        autoCommandSelector.addOption("Drive canned path", AutoCommands.DriveSplineCanned);
    
        SmartDashboard.putData("Auto Chooser", autoCommandSelector);

        File f = new File(Constants.kSplineDirectory);

        File[] pathNames = f.listFiles();
            for (File pathname : pathNames) {
            // Print the names of files and directories
            System.out.println(pathname);
            splineCommandSelector.addOption(pathname.getName(), pathname);
        }

        SmartDashboard.putData(splineCommandSelector);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings()
    {
        // TODO: Is the following control in the robot driver user interface specification?
        // TODO: Test this implementation to make sure that it works as expected since it's 
        // different from the way we've done this in the past.
        new JoystickButton(driverJoystick, Button.kBumperRight.value)
            .whenPressed(() -> m_driveSubsystem.setMaxOutput(0.5))
            .whenReleased(() -> m_driveSubsystem.setMaxOutput(1));

        //Intake
        extendIntakeButton.whenPressed(new ExtendIntakeCommand(m_Intake));
        retractIntakeButton.whenPressed(new RetractIntakeCommand(m_Intake));
        reverseIntake.whenPressed(new ReverseIntakeCommand(m_Intake));
        reverseIntake.whenReleased(new DefaultIntakeCommand(m_Intake));

        //Indexer
        startIndexer.whenPressed(new StartIndexerCommand(m_ballIndexerSubsystem));
        stopIndexer.whenPressed(new StopIndexerCommand(m_ballIndexerSubsystem));
        launchBall.whenPressed(new TriggerShotCommand(m_ballIndexerSubsystem));
        launchBall.whenReleased(new DisableShotCommand(m_ballIndexerSubsystem));

        //Launcher
        setHighAngle.whenPressed(new SetHoodHighShotCommand(m_launcherSubsystem));
        setLowAngle.whenPressed(new SetHoodLowShotCommand(m_launcherSubsystem));
        toggleLauncherSpeed.whenPressed(new LauncherSpeedCommand(m_launcherSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {

        var autoSelector = autoCommandSelector.getSelected();

        switch (autoSelector)
        {
            case DoNothing:
                return new DoNothingCommand();

            case DriveSplineFromJSON:
                // TODO: This path (loading the path from a JSON) does not seem to allow us to specify
                // constraints via a TrajectoryConfig. Are these baked into the JSON files already? - Yes
                File splineFile = splineCommandSelector.getSelected();
                Trajectory trajectory = new Trajectory();
                try {
                    Path trajectoryPath = splineFile.toPath();
                    trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
                    } catch (IOException ex) {
                        DriverStation.reportError("Unable to open trajectory: " + splineFile, ex.getStackTrace());
                    }
                return makeTrajectoryCommand(trajectory);
            
            case DriveSplineCanned:
                // Create a voltage constraint to ensure we don't accelerate too fast
                var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                    new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                    DriveConstants.kDriveKinematics, 10);
                TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond,
                    AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(DriveConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);
                Trajectory cannedTrajectory = TrajectoryGenerator.generateTrajectory(
                                new Pose2d(0, 0, new Rotation2d(0)),
                                List.of(new Translation2d(2, 1), new Translation2d(3, -1)),
                                new Pose2d(5, 0, new Rotation2d(0)), config);
                return makeTrajectoryCommand(cannedTrajectory);
    }

    return new DoNothingCommand();

    }

  private Command makeTrajectoryCommand(Trajectory trajectory) 
  {
    RamseteCommand ramseteCommand = new RamseteCommand(trajectory, m_driveSubsystem::getPose,
        new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
        new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
            DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.kDriveKinematics, m_driveSubsystem::getWheelSpeeds,
        new PIDController(DriveConstants.kPDriveVel, 0, 0), new PIDController(DriveConstants.kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        m_driveSubsystem::tankDriveVolts, m_driveSubsystem);

    // Reset odometry to the starting pose of the trajectory.
    m_driveSubsystem.resetOdometry(trajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> m_driveSubsystem.tankDriveVolts(0, 0));
  }
}

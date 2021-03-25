/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
import frc.robot.commands.testcommands.TestIndexerCommand;
import frc.robot.commands.testcommands.TestIntakeCommand;
import frc.robot.commands.testcommands.TestLauncherCommand;
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
public class RobotContainer
{

    public static UsbCamera camera;

    private enum AutoCommands
    {
        DoNothing, DriveSplineFromJSON, DriveSplineCanned, Drive1mForwardBackward, DriveBounce
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
    private Optional<SK21Launcher> m_launcherSubsystem = Optional.empty();
    private Optional<SK21BallIndexer> m_ballIndexerSubsystem = Optional.empty();
    private Optional<SK21Intake> m_Intake = Optional.empty();

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
    private final TriggerButton stopIndexer = new TriggerButton(operatorJoystick, Ports.OIOperatorDeactivateBMI);

    // TODO Climb Buttons
    // TODO Color wheel buttons
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
    public RobotContainer()
    {

        configureShuffleboard();

        m_launcherSubsystem  = Optional.of(new SK21Launcher());
        m_ballIndexerSubsystem  = Optional.of(new SK21BallIndexer());
        m_Intake  = Optional.of(new SK21Intake());

        // Configure the button bindings
        configureButtonBindings();
    
        driverJoystick.setFilter(Ports.OIDriverTurn, m_deadbandTurn);
        driverJoystick.setFilter(Ports.OIDriverMove, m_deadbandThrottle);

        // Configure default commands
        // Set the default drive command to split-stick arcade drive
        m_driveSubsystem.setDefaultCommand(new DefaultDriveCommand(m_driveSubsystem, driverJoystick));

        // Driver camera configuration.
        if (RobotBase.isReal())
        {
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
        autoCommandSelector.addOption("Drive forwards then backwards 1m", AutoCommands.Drive1mForwardBackward);
        autoCommandSelector.addOption("Drive bounce path", AutoCommands.DriveBounce);
    
        SmartDashboard.putData("Auto Chooser", autoCommandSelector);


        File splineDirectory = new File(Constants.kSplineDirectory);

        if (!splineDirectory.exists())
        {
            splineDirectory = new File(Constants.kSplineDirectoryWindows);
        }
        
        File[] pathNames = splineDirectory.listFiles();
        for (File pathname : pathNames)
        {
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

        if (m_Intake.isPresent())
        {
            var intake = m_Intake.get();
            extendIntakeButton.whenPressed(new ExtendIntakeCommand(intake));
            retractIntakeButton.whenPressed(new RetractIntakeCommand(intake));
            reverseIntake.whenPressed(new ReverseIntakeCommand(intake));
            reverseIntake.whenReleased(new DefaultIntakeCommand(intake));
        }

        //Indexer
        if (m_ballIndexerSubsystem.isPresent())
        {
            var indexer = m_ballIndexerSubsystem.get();
            startIndexer.whenPressed(new StartIndexerCommand(indexer));
            stopIndexer.whenPressed(new StopIndexerCommand(indexer));
            launchBall.whenPressed(new TriggerShotCommand(indexer));
            launchBall.whenReleased(new DisableShotCommand(indexer));
        }

        //Launcher
        if (m_launcherSubsystem.isPresent())
        {
            var launcher = m_launcherSubsystem.get();
            setHighAngle.whenPressed(new SetHoodHighShotCommand(launcher));
            setLowAngle.whenPressed(new SetHoodLowShotCommand(launcher));
            toggleLauncherSpeed.whenPressed(new LauncherSpeedCommand(launcher));
        }
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        File splineDirectory = new File(Constants.kSplineDirectory);

        if (!splineDirectory.exists())
        {
            splineDirectory = new File(Constants.kSplineDirectoryWindows);
        }

        var autoSelector = autoCommandSelector.getSelected();

        switch (autoSelector)
        {
            case DoNothing:
                return new DoNothingCommand();

            case DriveSplineFromJSON:
                // Note that the drive constraints are baked into the PathWeaver output so they are not
                // mentioned here.
                File splineFile = splineCommandSelector.getSelected();
                Trajectory trajectory = makeTrajectoryFromJSON(splineFile);
                if (trajectory == null)
                {
                    return new DoNothingCommand();
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
            
            // This sequentially runs thorugh the 2 sub-paths of the Drive1mForwardBackward path defined in PathWeaver 
            case Drive1mForwardBackward:
                
                // Generate a command for driving 1m forward from trajectory created from PathWeaver JSON file
                File drive1mf = new File(splineDirectory + "/1m Forwards.wpilib.json");
                Trajectory drive1mfTrajectory = makeTrajectoryFromJSON(drive1mf);
                if (drive1mfTrajectory == null)
                {
                    return new DoNothingCommand();
                }
                Command drive1mfCommand = makeTrajectoryCommand(drive1mfTrajectory);

                // Generate a command for driving 1m backward from trajectory created from PathWeaver JSON file
                File drive1mb = new File(splineDirectory + "/1m Backwards.wpilib.json");
                Trajectory drive1mbTrajectory = makeTrajectoryFromJSON(drive1mb);
                if (drive1mbTrajectory == null)
                {
                    return new DoNothingCommand();
                }
                Command drive1mbCommand = makeTrajectoryCommand(drive1mbTrajectory);

                // Execute each of the single commands in chronological order
                return new SequentialCommandGroup(drive1mfCommand, drive1mbCommand);
            
            // This sequentially runs thorugh the 4 sub-paths of the Bounce Path defined in PathWeaver  
            case DriveBounce:
                
                // Generate a command for driving the first segment Bounce Path trajectory defined by PathWeaver JSON file 
                File bounceSeg1 = new File(splineDirectory + "/Bounce Segment 1.wpilib.json");
                Trajectory bounceSeg1Trajectory = makeTrajectoryFromJSON(bounceSeg1);
                if (bounceSeg1Trajectory == null)
                {
                    return new DoNothingCommand();
                }
                Command driveBounceSeg1 = makeTrajectoryCommand(bounceSeg1Trajectory);

                // Generate a command for driving the second segment Bounce Path trajectory defined by PathWeaver JSON file
                File bounceSeg2 = new File(splineDirectory + "/Bounce Segment 2.wpilib.json");
                Trajectory bounceSeg2Trajectory = makeTrajectoryFromJSON(bounceSeg2);
                if (bounceSeg2Trajectory == null) 
                {
                    return new DoNothingCommand();
                }
                Command driveBounceSeg2 = makeTrajectoryCommand(bounceSeg2Trajectory);

                // Generate a command for driving the third segment Bounce Path trajectory defined by PathWeaver JSON file
                File bounceSeg3 = new File(splineDirectory + "/Bounce Segment 3.wpilib.json");
                Trajectory bounceSeg3Trajectory = makeTrajectoryFromJSON(bounceSeg3);
                if (bounceSeg3Trajectory == null)
                {
                    return new DoNothingCommand();
                }
                Command driveBounceSeg3 = makeTrajectoryCommand(bounceSeg3Trajectory);
                
                // Generate a command for driving the fourth segment Bounce Path trajectory defined by PathWeaver JSON file
                File bounceSeg4 = new File(splineDirectory + "/Bounce Segment 4.wpilib.json");
                Trajectory bounceSeg4Trajectory = makeTrajectoryFromJSON(bounceSeg4);
                if (bounceSeg4Trajectory == null)
                {
                    return new DoNothingCommand();
                }
                Command driveBounceSeg4 = makeTrajectoryCommand(bounceSeg4Trajectory);

                // Execute each of the single commands in chronological order
                return new SequentialCommandGroup(driveBounceSeg1, driveBounceSeg2, driveBounceSeg3, driveBounceSeg4);

            default:
                DriverStation.reportError("Uncoded selection from autoSelector chooser!", false);
                return new DoNothingCommand();

        }
    }

    private Trajectory makeTrajectoryFromJSON(File trajectoryJSON)
    {
    Trajectory trajectory = new Trajectory();
    try
    {
         trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryJSON.toPath());
    }
    catch (IOException ex)
    {
        // If we are unable to open the file the method returns a null object
        DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON.getName(), ex.getStackTrace());
        return null;
    }
    return trajectory;
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

  public void enterTestMode()
  {

    if (m_Intake.isPresent())
    {
        var intake = m_Intake.get();
        intake.setDefaultCommand(new TestIntakeCommand(intake)); 
    }
    if (m_ballIndexerSubsystem.isPresent())
    {
        var indexer = m_ballIndexerSubsystem.get();
        indexer.setDefaultCommand(new TestIndexerCommand(indexer));
    }
    if (m_launcherSubsystem.isPresent())
    {
        var launcher = m_launcherSubsystem.get();
        launcher.setDefaultCommand(new TestLauncherCommand(launcher));
    }
  }

  public void exitTestMode()
  {
    if (m_Intake.isPresent())
    {
        var intake = m_Intake.get();
        intake.resetDefaultCommand();
    }
    if (m_ballIndexerSubsystem.isPresent())
    {
        var indexer = m_ballIndexerSubsystem.get();
        indexer.resetDefaultCommand();
    }
    if (m_launcherSubsystem.isPresent())
    {
        var launcher = m_launcherSubsystem.get();
        launcher.resetDefaultCommand();
    }
    }
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.DisableShotCommand;
import frc.robot.commands.ExtendIntakeCommand;
import frc.robot.commands.LauncherSpeedCommand;
import frc.robot.commands.RetractIntakeCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.commands.SetHoodHighShotCommand;
import frc.robot.commands.SetHoodLowShotCommand;
import frc.robot.commands.StartIndexerCommand;
import frc.robot.commands.StopIndexerCommand;
import frc.robot.commands.TriggerShotCommand;
import frc.robot.subsystems.SK20Drive;
import frc.robot.subsystems.SK21BallIndexer;
import frc.robot.subsystems.SK21Intake;
import frc.robot.subsystems.SK21Launcher;
import frc.robot.subsystems.base.Dpad;
import frc.robot.subsystems.base.DpadDownButton;
import frc.robot.subsystems.base.DpadUpButton;
import frc.robot.subsystems.base.TriggerButton;
import frc.robot.utils.FilteredJoystick;
import frc.robot.utils.filters.CubicDeadbandFilter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    public static UsbCamera camera;

    //public static SendableChooser<String> chooser = new SendableChooser<>();

    private enum TestModeChoice {
        DRIVE, LAUNCHER, CLIMB, INTAKE, COLOR_WHEEL, OTHER
    };

    private enum AutoCommands {
        DriveForward, DriveBackward, DriveForwardShoot, ShootDriveForward, OffAngleShot, OffAngleRecollectShot,
        StraightShot, StraightRecollectShot, DriveSpline
    };

    


    //These are just place holder subsystems because we don't have the AutoCommands yet
    //private final Command simpleAut1 = new DriveStraightCommand(m_driveSubsystem, 0);

    //private final Command simpleAuto2 = new DriveStraightCommandNew(m_driveSubsystem, 0);

    
    SendableChooser<AutoCommands> autoCommandSelector = new SendableChooser<AutoCommands>();

    SendableChooser<File> splineSelector = new SendableChooser<File>();

    SendableChooser<TestModeChoice> testModeSelector = new SendableChooser<TestModeChoice>();


    File f = new File("C:/Users/Owner/Documents/WeaverOutput/output");

    File[] pathNames = f.listFiles();

    private final FilteredJoystick joystickDriver = new FilteredJoystick(Ports.OIDriverJoystick);
    private final Joystick joystickOperator = new Joystick(Ports.OIOperatorJoystick);

    private final SK21Intake m_Intake = new SK21Intake();

    // Intake controls
    private final Dpad dpad = new Dpad(joystickOperator);
    private final DpadUpButton extendIntakeButton = new DpadUpButton(dpad);
    private final DpadDownButton retractIntakeButton = new DpadDownButton(dpad);
    private final JoystickButton reverseIntake = new JoystickButton(joystickOperator, Ports.OIOperatorReverseIntake);

    

    // Launcher control buttons
    private final JoystickButton launchBall = new JoystickButton(joystickOperator, Ports.OIOperatorShootBall);
    private final JoystickButton setHighAngle = new JoystickButton(joystickOperator, Ports.OIOperatorHighHoodAngle);

    private final JoystickButton setLowAngle = new JoystickButton(joystickOperator, Ports.OIOperatorLowHoodAngle);

    private final JoystickButton toggleLauncherSpeed = new JoystickButton(joystickOperator,
            Ports.OIOperatorSetLauncherSpeed);

    private final TriggerButton startIndexer = new TriggerButton(joystickOperator, Ports.OIOperatorActivateIBM);
    private final TriggerButton stopIndexer = new TriggerButton(joystickOperator,Ports.OIOperatorDeactivateBMI);

    

    

    // Climb Buttons
    // public static JoystickButton operatorClimbArmDeploy = new
    // JoystickButton(joystickOperator,
    // Ports.OIOperatorDeployArm);
    private final JoystickButton runWinchRobot = new JoystickButton(joystickOperator, Ports.OIOperatorRunWinchArm);
    private final JoystickButton armClimbSystem = new JoystickButton(joystickOperator, Ports.OIOperatorArmClimb);

    // The robot's subsystems and commands are defined here...
    // private final SK20ColorWheel m_colorWheelSubsystem = new SK20ColorWheel();
    private final SK20Drive m_driveSubsystem = new SK20Drive(joystickDriver);
    // private final SK20Climb m_climbSubsystem = new SK20Climb();
    // private final SK20Intake m_intakeSubsystem = new SK20Intake();
    private final SK21Launcher m_launcherSubsystem = new SK21Launcher();
    private final SK21BallIndexer m_ballIndexerSubsystem = new SK21BallIndexer(joystickOperator);

    // TODO: Reinstate color wheel later
    // Color wheel buttons
    // public static JoystickButton startThreeRotate = new
    // JoystickButton(joystickOperator,
    // Ports.OIOperatorStartThreeRotate);
    // public static JoystickButton startSetColor = new
    // JoystickButton(joystickOperator, Ports.OIOperatorStartSetColor);
    // public static JoystickButton stopColorWheel = new
    // JoystickButton(joystickOperator, Ports.OIOperatorStopColorWheel);
    // public static InternalButton spinColorWheel = new InternalButton();
    // public static JoystickButton toggleColorWheelLift = new
    // JoystickButton(joystickOperator, Ports.OIOperatorColorWheelLift);

    public RobotContainer() {
        // auto commands
        autoCommandSelector.setDefaultOption("Drive forwards", AutoCommands.DriveForward);
        autoCommandSelector.addOption("Drive backwards", AutoCommands.DriveBackward);
        autoCommandSelector.addOption("Drive forward then shoot", AutoCommands.DriveForwardShoot);
        autoCommandSelector.addOption("Shoot then drive forwards", AutoCommands.ShootDriveForward);
        autoCommandSelector.addOption("Drive Spline", AutoCommands.DriveSpline);

        SmartDashboard.putData("Auto Chooser", autoCommandSelector);

        for (File pathname : pathNames) {
            // Print the names of files and directories
            System.out.println(pathname);
            splineSelector.addOption(pathname.getName(),pathname );
        }
    
        
    
    
        SmartDashboard.putData(splineSelector);

        // TODO: add extra auto commands once tested
        // autoCommandSelector.addOption("OffAngleShooting", AutoCommands.OffAngleShot);
        // autoCommandSelector.addOption("OffAngleShooting/Recollection",
        // AutoCommands.OffAngleRecollectShot);
        // autoCommandSelector.addOption("StraightShooting", AutoCommands.StraightShot);
        // autoCommandSelector.addOption("StraightShooting/Recollection",
        // AutoCommands.StraightRecollectShot);

        joystickDriver.setFilter(Ports.OIDriverLeftDrive, new CubicDeadbandFilter(0.60, 0.06, true));
        joystickDriver.setFilter(Ports.OIDriverRightDrive, new CubicDeadbandFilter(0.60, 0.06, true));

        // Configure the button bindings
        configureButtonBindings();

        // Driver camera configuration.
        if (RobotBase.isReal()) {
            camera = CameraServer.getInstance().startAutomaticCapture("Driver Front Camera", 0);
            camera.setResolution(240, 240);
            camera.setFPS(15);
        }
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        toggleLauncherSpeed.whenPressed(new LauncherSpeedCommand(m_launcherSubsystem));
        extendIntakeButton.whenPressed(new ExtendIntakeCommand(m_Intake));
        retractIntakeButton.whenPressed(new RetractIntakeCommand(m_Intake));

        reverseIntake.whenPressed(new ReverseIntakeCommand(m_Intake));
        reverseIntake.whenReleased(new DefaultIntakeCommand(m_Intake));

        startIndexer.whenPressed(new StartIndexerCommand(m_ballIndexerSubsystem));
        stopIndexer.whenPressed(new StopIndexerCommand(m_ballIndexerSubsystem));

        launchBall.whenPressed(new TriggerShotCommand(m_ballIndexerSubsystem));
        launchBall.whenReleased(new DisableShotCommand(m_ballIndexerSubsystem));

        setHighAngle.whenPressed(new SetHoodHighShotCommand(m_launcherSubsystem));
        setLowAngle.whenPressed(new SetHoodLowShotCommand(m_launcherSubsystem));

        // Sets robot buttons for the climb command
        // operatorClimbArmDeploy.whenPressed(new ClimbReleaseCommand(m_climbSubsystem,
        // this));
        // runWinchRobot.whenPressed(new WinchRobotCommand(m_climbSubsystem, true,
        // this));
        // runWinchRobot.whenReleased(new WinchRobotCommand(m_climbSubsystem, false,
        // this));

        // Sets the buttons to activate/deactivate intake
        // toggleIntake.whenPressed(new ToggleIntakeCommand(m_intakeSubsystem));
        // reverseIntake.whenPressed(new ReverseIntakeCommand(m_intakeSubsystem));

        // Toggles ball management on and off
        // toggleBallManagement.whileHeld(new
        // ToggleBallManagementCommand(m_ballIndexerSubsystem));

        // Set the ball launcher buttons to do correct commands
        // setHighAngle.whenPressed(new SetAngleCommand(m_launcherSubsystem, true));
        // setHighAngle.whenReleased(new SetAngleCommand(m_launcherSubsystem, false));
        // launchBall.whenPressed(new LaunchBallCommand(m_launcherSubsystem));

        // Sets robot buttons for the control panel command
        // TODO: Enter code back in again once we're testing the color wheel subsystem.
        // stopColorWheel.whenPressed(new StopColorWheelCommand(m_colorWheelSubsystem));
        // startThreeRotate.whenPressed(new
        // ThreeRotateCommandGroup(m_colorWheelSubsystem,
        // TuningParams.COLOR_WHEEL_TRANSITIONS));
        // startSetColor.whenPressed(new
        // TurnToColorCommandGroup(m_colorWheelSubsystem));
        // spinColorWheel.whenPressed(new
        // ManualColorWheelControlCommand(m_colorWheelSubsystem, true));
        // spinColorWheel.whenReleased(new
        // ManualColorWheelControlCommand(m_colorWheelSubsystem, false));
        // toggleColorWheelLift.whenPressed(new
        // ToggleColorWheelLiftCommand(m_colorWheelSubsystem));

    }

    public void testSelector() {
        switch (testModeSelector.getSelected()) {
            case OTHER:
                // add later:
                // CommandScheduler.getInstance().schedule(Command);
                System.out.println("Doing OTHER");
                break;
            case DRIVE:
                System.out.println("Doing Driver");

                break;
            case LAUNCHER:
                System.out.println("Doing LAUNCHER");
                break;
            case INTAKE:
                System.out.println("Doing INTAKE");
                break;
            case CLIMB:
                System.out.println("Doing CLIMB");
                break;
            case COLOR_WHEEL:
                System.out.println("Doing COLOR_WHEEL");
                break;
        }
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        AutoCommands myAuto = autoCommandSelector.getSelected();

        // StraightShotToMoveAuto autoCommand = new
        // StraightShotToMoveAuto(m_driveSubsystem, m_launcherSubsystem,
        // m_ballIndexerSubsystem);
        // return autoCommand.getCommandGroup();

        // TODO: Put this back in later!!

        switch (myAuto) {
            case DriveForward:
                //return new DriveStraightCommand(m_driveSubsystem, TuningParams.AUTO_DRIVE_DISTANCE);
                
            case DriveBackward:
                //return new DriveStraightCommand(m_driveSubsystem, -(TuningParams.AUTO_DRIVE_DISTANCE));
                
            case DriveForwardShoot:
                // DriveForwardShootAuto m_DriveShoot = new
                // DriveForwardShootAuto(m_driveSubsystem, m_launcherSubsystem);
                // return m_DriveShoot.getCommandGroup();

            case ShootDriveForward:
                // ShootDriveForwardAuto m_ShootDrive = new
                // ShootDriveForwardAuto(m_driveSubsystem, m_launcherSubsystem);
                // return m_ShootDrive.getCommandGroup();

                // case OffAngleShot:
                // OffAngleShotToMoveAuto m_autoPath = new
                // OffAngleShotToMoveAuto(m_driveSubsystem, m_launcherSubsystem);
                // return m_autoPath.getCommandGroup();

                // case OffAngleRecollectShot:
                // OffAngleShotToTrenchAuto m_autoPath1 = new
                // OffAngleShotToTrenchAuto(m_driveSubsystem, m_intakeSubsystem,
                // m_launcherSubsystem);
                // return m_autoPath1.getCommandGroup();

                // case StraightShot:
                // StraightShotToMoveAuto m_autoPath2 = new
                // StraightShotToMoveAuto(m_driveSubsystem,
                // m_launcherSubsystem,m_ballIndexerSubsystem);

                // return m_autoPath2.getCommandGroup();
                // case StraightRecollectShot:
                // StraightShotToTrenchAuto m_autoPath3 = new
                // StraightShotToTrenchAuto(m_driveSubsystem, m_launcherSubsystem,
                // m_intakeSubsystem);

                // return m_autoPath3.getCommandGroup();
            default:
                return (Command) null;

        }
        // return new DriveStraightCommand(m_driveSubsystem, 50);

    }

    public boolean isClimbArmed() {
        String debugger = DriverStation.getInstance().getGameSpecificMessage();
        double time = DriverStation.getInstance().getMatchTime();

        if ((time <= 30 || debugger == "D") && armClimbSystem.get()) {
            return true;
        } else {
            return false;
        }
    }

    public void runIntake(){
        m_Intake.extendIntake();
    }
}

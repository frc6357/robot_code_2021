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

    private enum AutoCommands {
        DoNothing, DriveSpline
    };

    private SendableChooser<AutoCommands> autoCommandSelector = new SendableChooser<AutoCommands>();

    private SendableChooser<File> splineCommandSelector = new SendableChooser<File>();

    // The Robot controllers
    private final FilteredJoystick driverJoystick = new FilteredJoystick(Ports.OIDriverJoystick);
    private final Joystick operatorJoystick = new Joystick(Ports.OIOperatorJoystick);

    // The robot's subsystems are defined here...
    private final SK21Drive m_driveSubsystem = new SK21Drive(driverJoystick);
    private final SK21Launcher m_launcherSubsystem = new SK21Launcher();
    private final SK21BallIndexer m_ballIndexerSubsystem = new SK21BallIndexer(operatorJoystick);
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

    public RobotContainer() {

        configureShuffleboard();

        // Configure the button bindings
        configureButtonBindings();
    
        driverJoystick.setFilter(Ports.OIDriverLeftDrive, new CubicDeadbandFilter(0.60, 0.06, true));
        driverJoystick.setFilter(Ports.OIDriverRightDrive, new CubicDeadbandFilter(0.60, 0.06, true));


        // Driver camera configuration.
        if (RobotBase.isReal()) {
            camera = CameraServer.getInstance().startAutomaticCapture("Driver Front Camera", 0);
            camera.setResolution(240, 240);
            camera.setFPS(15);
        }
    }

    private void configureShuffleboard(){
        // auto commands
        autoCommandSelector.setDefaultOption("DoNothing", AutoCommands.DoNothing);
        autoCommandSelector.addOption("DriveSpline", AutoCommands.DriveSpline);
    
        SmartDashboard.putData("Auto Chooser", autoCommandSelector);

        File f = new File(TuningParams.SPLINE_DIRECTORY);

        File[] pathNames = f.listFiles();
        for (File pathname : pathNames) {
            // Print the names of files and directories
            System.out.println(pathname);
            splineCommandSelector.addOption(pathname.getName(),pathname );
        }
    
        SmartDashboard.putData(splineCommandSelector);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        //Inake
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
    public Command getAutonomousCommand() {

        var autoSelector = autoCommandSelector.getSelected();

        //This is a dummy
        return new DoNothingCommand();
    }
}

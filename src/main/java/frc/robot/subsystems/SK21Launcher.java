package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.LauncherActivateCommand;
import frc.robot.subsystems.base.BaseRoller;

/**
 * This is the launcher subsystem that controls everything that has to do with the
 * launcher including releasing a ball into the launcher, the speed of the launcher and
 * the position of the hood.
 */
public class SK21Launcher extends SKSubsystemBase
{

    private final CANSparkMax launcherMotor =
            new CANSparkMax(Ports.ballLauncherMotor, MotorType.kBrushless);
    private final CANPIDController pidControl = launcherMotor.getPIDController();
    private final CANEncoder launcherMotorEncoder = launcherMotor.getEncoder();
    private final CANSparkMax releaseMotor =
            new CANSparkMax(Ports.ballReleaseMotor, MotorType.kBrushless);

    private final BaseRoller releaseRoller =
            new BaseRoller(releaseMotor, TuningParams.RELEASE_MOTOR_SPEED);
    private final DoubleSolenoid hoodMover =
            new DoubleSolenoid(Ports.pcm, Ports.launcherHoodExtend, Ports.launcherHoodRetract);

    private double launcherSetpoint = 0.0;
    private double lastSetSpeed = 0.0;
    private LauncherActivateCommand defaultCommand;

    private NetworkTableEntry launcherMotorEntry;
    private NetworkTableEntry releaseMotorEntry;
    private NetworkTableEntry releaseRollerEntry;

    SendableChooser<DoubleSolenoid.Value> solenoidChooser =
            new SendableChooser<DoubleSolenoid.Value>();

    /**
     * Constructs a new SK21Launcher.
     */
    public SK21Launcher()
    {
        /*
         * Set the launcher motor into coast mode. If we don't do this and we stop the
         * motor quickly, there's a very good chance we'll damage something since there's
         * a large flywheel attached to this subsystem!
         */
        launcherMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        setPIDValues();
        setHoodForHighAngleShot(false);
        /*
         * TODO "this" escaping from a constructor should be avoided if possible - it
         * indicates a circular reference, and in certain conditions can cause programs to
         * crash. A better methodology here is similar to what is used in SK21Drive, where
         * the default command is external to the subsystem.
         */
        defaultCommand = new LauncherActivateCommand(this);
        resetDefaultCommand();
    }

    /**
     * Resets the default command for this subsystem to the command used during
     * auto/teleop.
     */
    public void resetDefaultCommand()
    {
        setDefaultCommand(defaultCommand);
    }

    /**
     * Returns the current encoder speed of the launcher.
     * 
     * @return The current speed of the launcher motor in RPM.
     */
    public double getLauncherSpeed()
    {
        return launcherMotorEncoder.getVelocity();
    }

    /**
     * Sets the PID values for the controller.
     */
    private void setPIDValues()
    {
        pidControl.setP(TuningParams.LAUNCHER_P_VALUE);
        pidControl.setI(TuningParams.LAUNCHER_I_VALUE);
        pidControl.setD(TuningParams.LAUNCHER_D_VALUE);
        pidControl.setOutputRange(-1, 0);
        pidControl.setIZone(TuningParams.LAUNCHER_IZONE_VALUE);
        pidControl.setFF(0.0);
    }

    /**
     * Sets the setpoint of the PID controller.
     * 
     * @param value
     *            The set double value
     */
    private void setSetpoint(double value)
    {
        launcherSetpoint = value * TuningParams.LAUNCHER_MAX_RPM;
        pidControl.setReference(launcherSetpoint, ControlType.kVelocity);
        SmartDashboard.putNumber("Launcher Setpoint", launcherSetpoint);
    }

    /**
     * Sets the hood according to the given value (true = set hood high).
     * 
     * @param value
     *            The value used to determine how the Hood should be set
     */
    public void setHoodForHighAngleShot(boolean value)
    {
        DoubleSolenoid.Value sendVal = value ? Value.kForward : Value.kReverse;
        hoodMover.set(sendVal);
    }

    /**
     * Returns true if the hood is currently set to shoot high.
     * 
     * @return true if the hood is currently set to shoot high; false otherwise
     */
    public boolean isHoodSetToShootHigh()
    {
        DoubleSolenoid.Value value = hoodMover.get();
        return (value == DoubleSolenoid.Value.kForward);
    }

    /**
     * This sets the speed of the motor using the PID control loop.
     * 
     * @param speed
     *            The speed that we want the motor to be runinng at
     */
    public void setLauncherSpeed(double speed)
    {
        setSetpoint(speed);
        lastSetSpeed = speed;
    }

    /**
     * Returns the last set speed for the launcher.
     * 
     * @return The speed that was last set to the launcher
     */
    public double getLastSetSpeed()
    {
        return lastSetSpeed;
    }

    /**
     * Starts the launcher's release roller motor. Turning it on causes the shooter to
     * fire balls.
     */
    public void startLaunchReleaseMotor()
    {
        releaseRoller.setForwards();
    }

    /**
     * Stops the launcher's release roller motor.
     */
    public void stopLaunchReleaseMotor()
    {
        releaseRoller.setStop();
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Launcher Encoder Value", launcherMotorEncoder.getVelocity());
    }

    @Override
    public void initializeTestMode()
    {
        solenoidChooser.setDefaultOption("Neutral", DoubleSolenoid.Value.kOff);
        solenoidChooser.addOption("Forwards", DoubleSolenoid.Value.kForward);
        solenoidChooser.addOption("Backwards", DoubleSolenoid.Value.kReverse);

        launcherMotorEntry = Shuffleboard.getTab("Launcher").add("launcherMotor", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 0).getEntry();

        Shuffleboard.getTab("Launcher").add("hoodMover", solenoidChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser).withSize(1, 1).withPosition(0, 4);
        releaseMotorEntry = Shuffleboard.getTab("Launcher").add("releaseMotor", 3)
            .withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(0, 6).getEntry();
        releaseRollerEntry = Shuffleboard.getTab("Launcher").add("releaseRoller", 3)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(1, 1).withPosition(3, 6).getEntry();
    }

    @Override
    public void testModePeriodic()
    {
        launcherMotor.set(launcherMotorEntry.getValue().getDouble());
        releaseMotor.set(releaseMotorEntry.getValue().getDouble());
        releaseRoller.setSpeed(releaseRollerEntry.getValue().getDouble());
        
        DoubleSolenoid.Value value = solenoidChooser.getSelected();
        hoodMover.set(value);
    }
}

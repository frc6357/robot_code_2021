package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.LauncherActivate;
import frc.robot.subsystems.base.BaseRoller;

/**
 * This is the launcher subsystem that controls everything that has to do with 
 * the launcher including the transfer from the handling system to the launcher
 * as well the speed of the launcher and the position of the hood to change the set angle.
 * 
 * launcherMotor is the motor used to spin the high energy flywheel launch roller.
 * releaseMotor is the motor driving the roller which feeds balls into the launch roller.
 */
public class SK21Launcher extends SubsystemBase
{
    private final CANSparkMax launcherMotor = new CANSparkMax(Ports.ballLauncherMotor, MotorType.kBrushless);
    private final CANPIDController PIDControl = launcherMotor.getPIDController();
    private final CANEncoder launcherMotorEncoder = launcherMotor.getEncoder();

    private final CANSparkMax releaseMotor = new CANSparkMax(Ports.ballReleaseMotor, MotorType.kBrushless);
    private final BaseRoller releaseRoller = new BaseRoller(releaseMotor, TuningParams.RELEASE_MOTOR_SPEED);

    private final DoubleSolenoid hoodMover = new DoubleSolenoid(Ports.pcm, Ports.launcherHoodExtend, Ports.launcherHoodRetract);

    private double launcherSetpoint = 0.0;
    private double lastSetSpeed = 0.0;

    /**
     * This does nothing as everything is intialized inside of the class before the constructor is even called so that 
     * we can guarantee everything is set up the way that we need it to be set up at.
     */
    public SK21Launcher()
    {
        // Set the launcher motor into coast mode. If we don't do this and we stop the motor quickly,
        // there's a very good chance we'll damage something since there's a large flywheel attached 
        // to this subsystem!
        launcherMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        setPIDValues();
        setHoodForHighAngleShot(false);
        setDefaultCommand(new LauncherActivate(this, false));
    }

    /**
     * This returns the current encoder speed of the launcher
     * 
     * @return The current speed of the launcher motor in RPM.
     */
    public double getLauncherSpeed()
    {
        return launcherMotorEncoder.getVelocity();
    }

    /**
     * Sets the PID values for the controller
     */
    private void setPIDValues()
    {
        PIDControl.setP(TuningParams.LAUNCHER_P_VALUE);
        PIDControl.setI(TuningParams.LAUNCHER_I_VALUE);
        PIDControl.setD(TuningParams.LAUNCHER_D_VALUE);
        PIDControl.setOutputRange(-1, 0);
        PIDControl.setIZone(TuningParams.LAUNCHER_IZONE_VALUE);
        PIDControl.setFF(0.0);
    }

    /**
     * Sets the setpoint of the PID controller
     * @param value The set double value
     */
    private void setSetpoint(double value)
    {
        launcherSetpoint = value * TuningParams.LAUNCHER_MAX_RPM;
        PIDControl.setReference(launcherSetpoint, ControlType.kVelocity);
        SmartDashboard.putNumber("Launcher Setpoint", launcherSetpoint);
    }

    /**
     * It sets the hood according to whatever position it is passed
     */
    public void setHoodForHighAngleShot(boolean value)
    {
        DoubleSolenoid.Value sendVal = value ? Value.kForward: Value.kReverse;
        hoodMover.set(sendVal);
    }

    /**
     * Checks to see what position the hood is in
     * @return The value of the double solenoid
     */
    public boolean isHoodSetToShootHigh()
    {
        DoubleSolenoid.Value value = hoodMover.get();
        return (value == DoubleSolenoid.Value.kForward);
    }

    /**
     * This sets the speed of the motor using the PID control loop
     * @param speed The speed that we want the motor to be runinng at
     */
    public void setLauncherSpeed(double speed)
    {
        setSetpoint(speed);
        lastSetSpeed = speed;
    }

    /**
     * This returns the last set speed for the launcher
     * @return The speed that was last set to the launcher
     */
    public double getLastSetSpeed() {
        return lastSetSpeed;
    }

    /**
     * This starts the launcher's release roller motor. Turning it on causes the shooter to
     * fire balls.
     */
    public void startLaunchReleaseMotor()
    {
        releaseRoller.setForwards();
    }

    /**
     * This stops the launcher's release roller motor.
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
}
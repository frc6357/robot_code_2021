package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.subsystems.base.BaseRoller;

/**
 * The SK21Intake class is the subsystem that interacts with the intake to
 * both set its speed and deploy or retract it and get its status.
 */
public class SK21Intake extends SubsystemBase
{
    private BaseRoller intakeRoller;
    private DoubleSolenoid intakeMover;
    private CANSparkMax intakeRollerMotor;
    private CANEncoder intakeRollerEncoder;

    private NetworkTableEntry intakeEntry;

    /**
     * Sets up the intake control such that it takes the values that are 
     * declared for it in Ports and assigns them to a BaseRoller and a double solenoid.
     */
    public SK21Intake()
    {
        intakeRollerMotor = new CANSparkMax(Ports.intakeMotor, MotorType.kBrushless);
        intakeRoller = new BaseRoller(intakeRollerMotor, TuningParams.INTAKE_MAX_SPEED);
        intakeRollerEncoder = intakeRollerMotor.getEncoder();

        intakeMover = new DoubleSolenoid(Ports.pcm, Ports.intakeMoverDrop, Ports.intakeMoverRaise);

        intakeEntry = Shuffleboard.getTab("Intake")
            .add("solenoid",1)
            .withWidget(BuiltInWidgets.kToggleButton)
            .withSize(2,1)
            .withPosition(0, 0)
            .getEntry();

            /*
            .add("motor",3)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withSize(1,1)
            .withPosition(0,4)
            .getEntry();
            */
    }

    /**
     * When extend intake is called the solenoid will activate and it will 
     * push the intake outside the robot perimeter ready to be started to pick up power cells.
     */
    public void extendIntake()
    {
        intakeMover.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * When retract intake is called the solenoid will retract and pull 
     * the intake mechanism back inside of the frame perimeter.
     */
    public void retractIntake()
    {
        intakeMover.set(DoubleSolenoid.Value.kReverse);
    }   

    /**
     * Start the intake roller motor running in the forward (normal) direction.
     */
    public void startIntakeRoller()
    {
        intakeRoller.setForwards();
    }

    /**
     * Start the intake roller motor running in the reverse direction.
     */
    public void reverseIntakeRoller()
    {
        intakeRoller.setBackwards();
    }

    /**
     * Stops the intake roller motor.
     */
    public void stopIntakeRoller()
    {
        intakeRoller.setStop();
    }

    /**
     * Checks whether the intake is extended or retracted based on the current solenoid state
     * @return Returns true if the intake is extended, false otherwise.
     */
    public boolean isIntakeExtended()
    {
        DoubleSolenoid.Value currentState = intakeMover.get();
        return (currentState.equals(Value.kForward)) ? true : false;
    }

    /**
     * Returns the current speed of the intake roller motor.
     * @return The speed of the intake.
     */
    public double getIntakeRollerSpeed()
    {
        return intakeRollerEncoder.getVelocity();
    }
}
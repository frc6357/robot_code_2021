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

        intakeMover = new DoubleSolenoid(Ports.pcm, Ports.intakeMoverExtend, Ports.intakeMoverRetract);

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
     * push it out to be able to turn on the motors.
     */
    public void extendIntake()
    {
        intakeMover.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * When retract intake is called the solenoid will retract and pull 
     * the intake mechanism back inside of the frame perimeter
     */
    public void retractIntake()
    {
        intakeMover.set(DoubleSolenoid.Value.kReverse);
    }   

    /**
     * When activate intake is called the motor on the intake turns on 
     * up to the set speed until it is deactivated
     */
    public void startIntakeRoller()
    {
        intakeRoller.setForwards();
    }

    /**
     * When reverse intake is called the motor on the intake turns the opposite direction on 
     * up to the set speed until it is deactivated
     */
    public void reverseIntakeRoller()
    {
        intakeRoller.setBackwards();
    }

    /**
     * When deactivate intake is called on the motor the intake is turned completely off
     */
    public void stopIntakeRoller()
    {
        intakeRoller.setStop();
    }

    /**
     * Checks whether the intake is open or closed using the limit switch that's installed
     * @return The intake position. kForward for out, kReverse for in
     */
    public boolean isIntakeExtended()
    {
        DoubleSolenoid.Value currentState = intakeMover.get();
        return (currentState.equals(Value.kForward)) ? true : false;
    }

    /**
     * Checks that the intake speed is using the encoder that's there
     * @return The speed of the intake.
     */
    public double getIntakeRollerSpeed()
    {
        return intakeRollerEncoder.getVelocity();
    }
}
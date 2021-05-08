package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.subsystems.base.BaseRoller;

/**
 * The SK21Intake class is the subsystem that interacts with the intake to both set its
 * speed and deploy or retract it and get its status.
 */
public class SK21Intake extends SKSubsystemBase
{
    private final BaseRoller intakeRoller;

    private final DoubleSolenoid intakeMover;

    private final CANEncoder intakeRollerEncoder;

    private final DefaultIntakeCommand intakeCommand;

    private boolean intakeMotorIsStarted = false;

    private boolean intakeIsReversed = false;

    private NetworkTableEntry intakeRollerEntry;

    SendableChooser<DoubleSolenoid.Value> solenoidChooser =
            new SendableChooser<DoubleSolenoid.Value>();

    /**
     * Sets up the intake control such that it takes the values that are declared for it
     * in Ports and assigns them to a BaseRoller and a double solenoid.
     */
    public SK21Intake()
    {
        CANSparkMax intakeRollerMotor = new CANSparkMax(Ports.intakeMotor, MotorType.kBrushless);
        intakeRoller = new BaseRoller(intakeRollerMotor, TuningParams.INTAKE_MAX_SPEED);
        intakeRollerEncoder = intakeRollerMotor.getEncoder();
        intakeMover = new DoubleSolenoid(Ports.pcm, Ports.intakeMoverDrop, Ports.intakeMoverRaise);
        /*
         * TODO "this" escaping from a constructor should be avoided if possible - it
         * indicates a circular reference, and in certain conditions can cause programs to
         * crash. A better methodology here is similar to what is used in SK21Drive, where
         * the default command is external to the subsystem.
         */
        intakeCommand = new DefaultIntakeCommand(this);
        resetDefaultCommand();
    }

    /**
     * Resets the default command for this subsystem to the command used during
     * auto/teleop.
     */
    public void resetDefaultCommand()
    {
        setDefaultCommand(intakeCommand);
    }

    /**
     * When extend intake is called the solenoid will activate and it will push the intake
     * outside the robot perimeter ready to be started to pick up power cells.
     */
    public void extendIntake()
    {
        intakeMover.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * When retract intake is called the solenoid will retract and pull the intake
     * mechanism back inside of the frame perimeter.
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
        intakeMotorIsStarted = true;

        if (intakeIsReversed)
        {
            intakeRoller.setBackwards();
        }
        else
        {
            intakeRoller.setForwards();
        }
    }

    /**
     * Set whether the intake roller motor should be running in the reverse direction;
     * start the intake roller motor if necessary.
     * 
     * @param isBackwards
     *            indicates if the intake roller motor should be reversed.
     */
    public void setIntakeRollerBackwards(boolean isBackwards)
    {
        intakeIsReversed = isBackwards;

        if (isIntakeExtended())
        {
            startIntakeRoller();
        }
    }

    /**
     * Stops the intake roller motor.
     */
    public void stopIntakeRoller()
    {
        intakeRoller.setStop();
        intakeMotorIsStarted = false;
    }

    /**
     * Checks whether the intake is extended or retracted based on the current solenoid
     * state
     * 
     * @return true if the intake is extended, false otherwise.
     */
    public boolean isIntakeExtended()
    {
        DoubleSolenoid.Value currentState = intakeMover.get();
        return currentState.equals(Value.kForward);
    }

    /**
     * Returns the current speed of the intake roller motor.
     * 
     * @return The speed of the intake.
     */
    public double getIntakeRollerSpeed()
    {
        return intakeRollerEncoder.getVelocity();
    }

    /**
     * Returns current state of the Intake Motor
     * 
     * @return current state of the intake motor (true=yes/false=no)
     */
    public boolean isIntakeMotorStarted()
    {
        return intakeMotorIsStarted;
    }

    /**
     * Returns current state of the Intake Direction
     * 
     * @return current direction of intake rollers (true=yes/false=no)
     */
    public boolean isIntakeReversed()
    {
        return intakeIsReversed;
    }

    @Override
    public void initializeTestMode()
    {
        solenoidChooser.setDefaultOption("Neutral", DoubleSolenoid.Value.kOff);
        solenoidChooser.addOption("Forwards", DoubleSolenoid.Value.kForward);
        solenoidChooser.addOption("Backwards", DoubleSolenoid.Value.kReverse);

        // Toggle widget that controls the extension state of the color wheel mechanism
        Shuffleboard.getTab("Intake").add("Extension", solenoidChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser).withSize(2, 1).withPosition(0, 0);

        intakeRollerEntry = Shuffleboard.getTab("Intake").add("roller", 3)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(1, 1).withPosition(0, 4).getEntry();
    }

    @Override
    public void testModePeriodic()
    {
        intakeRoller.setSpeed(intakeRollerEntry.getValue().getDouble());
        DoubleSolenoid.Value value = solenoidChooser.getSelected();
        intakeMover.set(value);
    }
}

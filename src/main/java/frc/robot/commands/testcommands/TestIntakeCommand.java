package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Intake;

/**
 * A Command for
 */
public class TestIntakeCommand extends CommandBase
{
    /**
     * The Intake subsystem under test.
     */
    private final SK21Intake intakeSubsystem;

    /**
     * NetworkTableEntry for the Deployment Solenoid.
     */
    private ComplexWidget intakeDeployEntry;

    /**
     * NetworkTableEntry for the Roller Motor.
     */
    private NetworkTableEntry intakeRollerEntry;

    SendableChooser<DoubleSolenoid.Value> solenoidChooser = new SendableChooser<DoubleSolenoid.Value>();


    /**
     * Creates a new TestIntakeCommand for thegiven intakeSubsystem.
     *
     * @param intakeSubsystem
     *            The subsystem used by this command.
     */
    public TestIntakeCommand(SK21Intake intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        solenoidChooser.setDefaultOption("Neutral", DoubleSolenoid.Value.kOff);
        solenoidChooser.addOption("Forwards", DoubleSolenoid.Value.kForward);
        solenoidChooser.addOption("Backwards", DoubleSolenoid.Value.kReverse);

        // Toggle widget that controls the extension state of the color wheel mechanism
        intakeDeployEntry = Shuffleboard.getTab("Color Wheel").add("Extension", solenoidChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser).withSize(2, 1).withPosition(0, 0);

        intakeRollerEntry = Shuffleboard.getTab("Intake").add("roller", 3)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(1, 1).withPosition(0, 4).getEntry();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {

        intakeSubsystem.intakeRoller.setSpeed(intakeRollerEntry.getValue().getDouble());
        DoubleSolenoid.Value value = solenoidChooser.getSelected();
        intakeSubsystem.intakeMover.set(value);

    }

    // False as test commands are intended to run the entire test mode.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

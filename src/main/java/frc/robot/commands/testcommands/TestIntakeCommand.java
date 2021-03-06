package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
    private NetworkTableEntry intakeDeployEntry;

    /**
     * NetworkTableEntry for the Roller Motor.
     */
    private NetworkTableEntry intakeRollerEntry;

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
        intakeDeployEntry = Shuffleboard.getTab("Intake").add("extension", 1)
            .withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1)
            .withPosition(0, 0).getEntry();

        intakeRollerEntry = Shuffleboard.getTab("Intake").add("roller", 3)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(1, 1)
            .withPosition(0, 4).getEntry();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        //TODO: Attach motors/solenoid to the NetworkTableEntry objects
    }

    // False as test commands are intended to run the entire test mode.
    @Override
    public boolean isFinished()
    {
        return false;
    }

}

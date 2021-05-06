package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Climb;

/**
 * A Command for climb testing
 */
public class TestClimbCommand extends CommandBase
{
    /**
     * The climb subsystem under test.
     */
    private final SK21Climb climbSubsystem;

    /**
     * NetworkTableEntry for the Climb motor.
     */
    private NetworkTableEntry climbEntry;

    /**
     * Creates a new TestIntakeCommand for the given climb Subsystem.
     *
     * @param climbSubsystem
     *            The subsystem used by this command.
     */
    public TestClimbCommand(SK21Climb climbSubsystem)
    {
        this.climbSubsystem = climbSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climbSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        climbEntry = Shuffleboard.getTab("Climb").add("Speed", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 0).getEntry();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {

        climbSubsystem.winchMotorGroup.set(climbEntry.getValue().getDouble());

    }

    // False as test commands are intended to run the entire test mode.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * A Command to exercise the hardware components in the BallIndexer subsystem.
 */
public class TestIndexerCommand extends CommandBase
{
    /**
     * The Indexer subsystem under test.
     */
    private final SK21BallIndexer indexerSubsystem;

    /**
     * The NetworkTableEntry for the Indexer spin motor.
     */
    private NetworkTableEntry indexerSpinEntry;

    /**
     * The NetworkTableEntry for the Indexer launch motor.
     */
    private NetworkTableEntry indexerMotorEntry;

    /**
     * Creates a new TestIndexerCommand for the given Ball Indexer subsystem.
     *
     * @param indexerSubsystem
     *            The subsystem used by this command.
     */
    public TestIndexerCommand(SK21BallIndexer indexerSubsystem)
    {
        this.indexerSubsystem = indexerSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(indexerSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        indexerSpinEntry = Shuffleboard.getTab("Indexer").add("Spin", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 0).getEntry();

        indexerMotorEntry = Shuffleboard.getTab("Indexer").add("Roller", 3).withSize(1, 1)
            .withPosition(0, 6).getEntry();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        indexerSubsystem.indexerMotor.set(indexerMotorEntry.getValue().getDouble());
        indexerSubsystem.indexerRoller.setSpeed(indexerSpinEntry.getValue().getDouble());
    }

    // False as test commands are intended to run the entire test mode.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

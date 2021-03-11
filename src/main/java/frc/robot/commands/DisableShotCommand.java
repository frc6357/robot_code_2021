package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * Disables the shoot activity (retracts the arm that pushes a ball into the launcher and
 * stops the feeder motor).
 */
public class DisableShotCommand extends CommandBase
{
    /**
     * The Ball Indexer Subsystem.
     */
    private final SK21BallIndexer indexerSubsystem;

    /**
     * Creates a new DisableShotCommand which takes in the required subsystem
     *
     * @param indexerSubsystem
     *            The indexer subsystem used by this command.
     */
    public DisableShotCommand(SK21BallIndexer indexerSubsystem)
    {
        this.indexerSubsystem = indexerSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(indexerSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    @Override
    public void initialize()
    {
        indexerSubsystem.stopLauncherFeederMotor();
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

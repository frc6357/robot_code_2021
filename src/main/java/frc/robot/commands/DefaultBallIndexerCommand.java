package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * A placeholder command for the Ball Indexer. This does not change behavior; there are
 * separate commands to start and stop the indexer.
 */
public class DefaultBallIndexerCommand extends CommandBase
{

    /**
     * Constructs a new DefaultBallIndexerCommand for the given Ball Indexer Subsystem.
     * 
     * @param subsystem
     *            The Ball Indexer Subsystem on which this DefaultBallIndexerCommand will
     *            operate
     */
    public DefaultBallIndexerCommand(SK21BallIndexer subsystem)
    {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // False as default commands are intended to not end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * A command that runs the ball transfer motors. Motors run whenever we do not have a full
 * compliment of 5 balls in the intake/transfer mechanism.
 */
public class DefaultBallIndexerCommand extends CommandBase
{

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

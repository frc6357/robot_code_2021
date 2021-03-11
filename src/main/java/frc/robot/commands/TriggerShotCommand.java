package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * A Command to trigger a shot, by starting the feeder motor and extending the feeder arm.
 */
public class TriggerShotCommand extends CommandBase
{

    /**
     * The Ball Indexer Subsystem.
     */
    private final SK21BallIndexer indexerSubsystem;

    /**
     * Creates a new ReverseIntake which takes in the required subsystem
     *
     * @param indexerSubsystem
     *            The indexer subsystem used by this command.
     */
    public TriggerShotCommand(SK21BallIndexer indexerSubsystem)
    {
        this.indexerSubsystem = indexerSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(indexerSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    /**
     * We assume here that the motor can be started coincidentally with thet feeder arm
     * being extended.
     */
    @Override
    public void initialize()
    {
        indexerSubsystem.startLauncherFeederMotor();
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

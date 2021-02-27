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
     * The Ball Indexer Subsystem
     */
    private final SK21BallIndexer subsystem;

    /**
     * Creates a new DisableShotCommand which takes in the required subsystem
     *
     * @param subsystem
     *            The indexer subsystem used by this command.
     */
    public DisableShotCommand(SK21BallIndexer subsystem)
    {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void initialize()
    {
        subsystem.retractLauncherFeederArm();
        subsystem.stopLauncherFeederMotor();
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}

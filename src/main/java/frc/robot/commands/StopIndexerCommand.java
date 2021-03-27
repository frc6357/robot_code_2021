// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * An intake command that stops the ball indexer.
 */
public class StopIndexerCommand extends CommandBase
{

    /**
     * The Ball Indexer Subsystem.
     */
    private final SK21BallIndexer indexerSubsystem;

    /**
     * Creates a new StopIndexerCommand which takes in the required subsystem.
     *
     * @param indexerSubsystem
     *            The indexer subsystem used by this command.
     */
    public StopIndexerCommand(SK21BallIndexer indexerSubsystem)
    {
        this.indexerSubsystem = indexerSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(indexerSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    @Override
    public void initialize()
    {
        indexerSubsystem.stopIndexerRotation();
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

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
     * The Ball Indexer Subsystem
     */
    private final SK21BallIndexer subsystem;

    /**
     * Creates a new StopIndexerCommand which takes in the required subsystem
     *
     * @param subsystem
     *            The indexer subsystem used by this command.
     */
    public StopIndexerCommand(SK21BallIndexer subsystem)
    {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    /**
     * Stops the indexer.
     */
    @Override
    public void initialize()
    {
        subsystem.stopIndexerRotation();
    }

    /*
     * Returns true when the command should end, which should always be true as the
     * functionality ends immediately after the intialize function.
     */
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

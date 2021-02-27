// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * An intake command that starts the ball indexer.
 */
public class StartIndexerCommand extends CommandBase
{

    /**
     * The Ball Indexer Subsystem
     */
    private final SK21BallIndexer subsystem;

    /**
     * Creates a new StartIndexerCommand which takes in the required subsystem
     *
     * @param subsystem
     *            The indexer subsystem used by this command.
     */
    public StartIndexerCommand(SK21BallIndexer subsystem)
    {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    /**
     * Starts the indexer.
     */
    @Override
    public void initialize()
    {
        subsystem.startIndexerRotation();
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

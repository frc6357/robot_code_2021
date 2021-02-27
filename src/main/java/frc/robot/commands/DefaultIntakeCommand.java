// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK21Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An intake command that normalizes the direction of the intake rollers when it is
 * extended.
 */
public class DefaultIntakeCommand extends CommandBase
{

    /**
     * The Ball Intake Subsystem
     */
    private final SK21Intake m_subsystem;

    /**
     * Creates a new DefaultIntakeCommand which takes in the required subsystem
     *
     * @param subsystem
     *            The intake subsystem used by this command.
     */
    public DefaultIntakeCommand(SK21Intake subsystem)
    {
        m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    /**
     * It will set the intake to go to the right direction and will only set it if the
     * intake is extended out
     */
    @Override
    public void initialize()
    {
        if (m_subsystem.isIntakeExtended())
        {
            m_subsystem.startIntakeRoller();
        }
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

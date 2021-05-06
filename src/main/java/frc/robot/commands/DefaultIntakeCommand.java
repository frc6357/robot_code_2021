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
     * Construct a new IntakeIdleCommand.
     * 
     * @param intakeSubsystem
     *            The Intake Subsystem this Command depends upon
     */
    public DefaultIntakeCommand(SK21Intake intakeSubsystem)
    {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK21Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An intake command that reverses the direction of the intake rollers when it is
 * extended.
 */
public class ReverseIntakeCommand extends CommandBase
{

    /**
     * The Ball Intake Subsystem.
     */
    private final SK21Intake intakeSubsystem;

    /**
     * Creates a new ReverseIntakeCommand which takes in the required subsystem.
     *
     * @param intakeSubsystem
     *            The intake subsystem used by this command.
     */
    public ReverseIntakeCommand(SK21Intake intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    /**
     * It will set the intake to go to the reverse direction and will only set it if the
     * intake is extended.
     * 
     * TODO: This is inconsistent with the documentation - which is hold to activate, so
     * this should not be using initialize (which is a one-shot)
     */
    @Override
    public void initialize()
    {
        if (intakeSubsystem.isIntakeExtended())
        {
            intakeSubsystem.reverseIntakeRoller();
        }
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

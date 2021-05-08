// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK21Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An intake command that sets the direction of the intake rollers to forward when it is
 * extended.
 */
public class ForwardIntakeCommand extends CommandBase
{
    /**
     * The Ball Intake Subsystem.
     */
    private final SK21Intake intakeSubsystem;

    /**
     * Creates a new ForwardIntakeCommand which takes in the required subsystem.
     *
     * @param intakeSubsystem
     *            The intake subsystem used by this command.
     */
    public ForwardIntakeCommand(SK21Intake intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    /**
     * It will set the intake to go to the reverse direction and will only set it if the
     * intake is extended.
     */
    @Override
    public void initialize()
    {
        intakeSubsystem.setIntakeRollerBackwards(false);
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

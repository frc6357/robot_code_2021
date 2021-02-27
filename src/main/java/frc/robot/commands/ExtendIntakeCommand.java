/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Intake;

/**
 * This command extends the intake and starts the intake rollers.
 */
public class ExtendIntakeCommand extends CommandBase
{
    /**
     * The Ball Intake Subsystem.
     */
    private final SK21Intake intakeSubsystem;

    /**
     * Creates a new ExtendIntakeCommand which takes in the required subsystem
     *
     * @param intakeSubsystem
     *            The intake subsystem used by this command.
     */
    public ExtendIntakeCommand(SK21Intake intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    @Override
    public void initialize()
    {
        intakeSubsystem.extendIntake();
        intakeSubsystem.startIntakeRoller();
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

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
 * This command retracts the intake.
 */
public class RetractIntakeCommand extends CommandBase
{
    /**
     * The Ball Intake Subsystem.
     */
    private final SK21Intake subsystem;

    /**
     * Creates a new RetractIntakeCommand which takes in the required subsystem
     *
     * @param subsystem
     *            The intake subsystem used by this command.
     */
    public RetractIntakeCommand(SK21Intake subsystem)
    {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize()
    {
        subsystem.retractIntake();
        subsystem.stopIntakeRoller();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

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
 * This command toggles the intake position to be extended or retracted.
 */
public class ToggleIntakeCommand extends CommandBase 
{
    private final SK21Intake subsystem;

    public ToggleIntakeCommand(SK21Intake subsystem) 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() 
    {
        if(subsystem.isIntakeExtended())
        {
            subsystem.retractIntake();
            subsystem.stopIntakeRoller();
        }
        else
        {
            subsystem.extendIntake();
            subsystem.startIntakeRoller();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() 
    {
        return true;
    }
}

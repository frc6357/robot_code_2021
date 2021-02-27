/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Launcher;

/**
 * This command toggles the hood angle to low.
 */
public class SetHoodLowShotCommand extends CommandBase 
{
    private final SK21Launcher subsystem;

    public SetHoodLowShotCommand(SK21Launcher subsystem) 
    {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() 
    {
        subsystem.setHoodForHighAngleShot(false);        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() 
    {
        return true;
    }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;
import frc.robot.subsystems.SK21Intake;

/**
 * A default command for the intake system which does nothing.
 */
public class IntakeIdleCommand extends CommandBase
{
    public IntakeIdleCommand(SK21Intake intakeSubsystem)
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

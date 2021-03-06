// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DoNothingCommand extends CommandBase
{
 // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return true;
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK21BallIndexer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SetLauncherFeederCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SK21BallIndexer m_subsystem;
  private final boolean launcherFeederPosition;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SetLauncherFeederCommand(boolean launcherFeederPosition, SK21BallIndexer m_subsystem) 
  {
    this.m_subsystem = m_subsystem;
    this.launcherFeederPosition = launcherFeederPosition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    //TODO: write this
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() 
  {
    return true;
  }
}

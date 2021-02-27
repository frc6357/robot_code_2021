package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

public class DisableShotCommand extends CommandBase {

  private final SK21BallIndexer m_subsystem;

  public DisableShotCommand(SK21BallIndexer subsystem) {
    m_subsystem = subsystem;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

  }

  @Override
  public boolean isFinished() {
    return true;
  }
}

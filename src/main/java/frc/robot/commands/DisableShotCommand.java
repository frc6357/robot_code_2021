package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;
import frc.robot.subsystems.SK21Intake;

public class DisableShotCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SK21BallIndexer m_subsystem;

 
  public DisableShotCommand(SK21BallIndexer subsystem) {
    m_subsystem = subsystem;
   
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_subsystem.retractLauncherFeederArm();
  }
  @Override
  public boolean isFinished() {
    return true;
  }

  
}

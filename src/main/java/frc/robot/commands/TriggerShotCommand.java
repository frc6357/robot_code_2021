package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;
import frc.robot.subsystems.SK21Intake;

public class TriggerShotCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SK21BallIndexer m_subsystem;

  /**
   * Creates a new ReverseIntake which takes in the
   * required subsystem
   *
   * @param subsystem The intake subsystem used by this command.
   */
  public TriggerShotCommand(SK21BallIndexer subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  /**
   * It will set the intake to go to the right direction
   * and will only set it if the intake is extended out
   */
  @Override
  public void initialize() {
    
  }

  /*
   * Returns true when the command should end, which should always be true
   * as the functionality ends immediately after the intialize function.
   */
  @Override
  public boolean isFinished() {
    return true;
  }

  
}

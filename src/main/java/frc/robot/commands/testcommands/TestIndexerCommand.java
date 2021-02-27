package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21BallIndexer;

public class TestIndexerCommand extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SK21BallIndexer indexerSubsystem;

  private NetworkTableEntry indexerSpinEntry;
  private NetworkTableEntry indexerLaunchArmEntry;
  private NetworkTableEntry indexerMotorEntry;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem
   *          The subsystem used by this command.
   */
  public TestIndexerCommand(SK21BallIndexer indexerSubsystem)
  {
    this.indexerSubsystem = indexerSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(indexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    indexerSpinEntry = Shuffleboard.getTab("Indexer")
      .add("Spin", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withSize(2, 1)
      .withPosition(0, 0)
      .getEntry();

    indexerLaunchArmEntry = Shuffleboard.getTab("Indexer")
      .add("Arm", 3)
      .withWidget(BuiltInWidgets.kToggleButton)
      .withSize(1, 1)
      .withPosition(0, 4)
      .getEntry();
      
    indexerMotorEntry = Shuffleboard.getTab("Indexer")
      .add("roller", 3)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withSize(1, 1)
      .withPosition(0, 6)
      .getEntry();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }

}

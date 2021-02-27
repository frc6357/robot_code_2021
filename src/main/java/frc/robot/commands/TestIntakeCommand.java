package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Intake;

public class TestIntakeCommand extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SK21Intake intakeSubsystem;

  private NetworkTableEntry intakeDeployEntry;
  private NetworkTableEntry intakeRollerEntry;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem
   *          The subsystem used by this command.
   */
  public TestIntakeCommand(SK21Intake intakeSubsystem)
  {
    this.intakeSubsystem = intakeSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intakeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    intakeDeployEntry = Shuffleboard.getTab("Intake")
            .add("extension",1)
            .withWidget(BuiltInWidgets.kToggleButton)
            .withSize(2,1)
            .withPosition(0, 0)
            .getEntry();

        intakeRollerEntry = Shuffleboard.getTab("Intake")
            .add("roller",3)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withSize(1,1)
            .withPosition(0,4)
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

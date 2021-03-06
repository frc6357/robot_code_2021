package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Launcher;

public class TestLauncherCommand extends CommandBase
{
    /*
     * The launch subsystem under test
     */
    private final SK21Launcher launcherSubsystem;

    /**
     * NetworkTableEntry for the launcher motor.
     */
    private NetworkTableEntry launcherMotorEntry;

    /**
     * NetworkTableEntry for the release motor.
     */
    private NetworkTableEntry releaseMotorEntry;

    /**
     * NetworkTableEntry for the Hood Solenoid.
     */
    private NetworkTableEntry hoodMoverEntry;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem
     *            The subsystem used by this command.
     */
    public TestLauncherCommand(SK21Launcher launcherSubsystem)
    {
        this.launcherSubsystem = launcherSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(launcherSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        launcherMotorEntry = Shuffleboard.getTab("Launcher")
            .add("launcherMotor", 1).withWidget(BuiltInWidgets.kNumberSlider)
            .withSize(2, 1).withPosition(0, 0).getEntry();

        hoodMoverEntry = Shuffleboard.getTab("Launcher").add("hoodMover", 3)
            .withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1)
            .withPosition(0, 4).getEntry();

        releaseMotorEntry = Shuffleboard.getTab("Launcher")
            .add("releaseMotor", 3).withWidget(BuiltInWidgets.kNumberSlider)
            .withSize(1, 1).withPosition(0, 6).getEntry();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        //TODO: Attach motors/solenoid to the NetworkTableEntry objects
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

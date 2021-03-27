package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Drive;
import frc.robot.subsystems.SK21Intake;

/**
 * A Command for
 */
public class TestDriveCommand extends CommandBase
{
    /**
     * The Intake subsystem under test.
     */
    private final SK21Drive driveSubsystem;


    private NetworkTableEntry leftLeaderEntry;

    private NetworkTableEntry leftFollowerEntry;
  
    private NetworkTableEntry rightLeaderEntry;

    private NetworkTableEntry rightFollowerEntry;
  
    private NetworkTableEntry SpeedControllerGroupLeftEntry;

    private NetworkTableEntry SpeedControllerGroupRightEntry;

    /**
     * Creates a new TestIntakeCommand for thegiven intakeSubsystem.
     *
     * @param driveSubsystem
     *            The subsystem used by this command.
     */
    public TestDriveCommand(SK21Drive driveSubsystem)
    {
        this.driveSubsystem = driveSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        leftLeaderEntry = Shuffleboard.getTab("Drive").add("leftLeader", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(0, 0).getEntry();
        
        leftFollowerEntry = Shuffleboard.getTab("Drive").add("leftFollower", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(2, 0).getEntry();

        rightLeaderEntry = Shuffleboard.getTab("Drive").add("rightLeader", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(0, 1).getEntry();

        rightFollowerEntry = Shuffleboard.getTab("Drive").add("rightFollower", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(2, 1).getEntry();

        SpeedControllerGroupLeftEntry = Shuffleboard.getTab("Drive").add("SpeedControllerGroupLeft", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(0, 2).getEntry();

        SpeedControllerGroupRightEntry = Shuffleboard.getTab("Drive").add("SpeedControllerGroupRight", 1)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1)
            .withPosition(2, 2).getEntry();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {

        driveSubsystem.m_leftLeader.set(leftLeaderEntry.getValue().getDouble());

        driveSubsystem.m_leftFollower.set(leftFollowerEntry.getValue().getDouble());

        driveSubsystem.m_rightLeader.set(rightLeaderEntry.getValue().getDouble());

        driveSubsystem.m_rightFollower.set(rightFollowerEntry.getValue().getDouble());

        driveSubsystem.m_leftGroup.set(SpeedControllerGroupLeftEntry.getValue().getDouble());

        driveSubsystem.m_rightGroup.set(SpeedControllerGroupRightEntry.getValue().getDouble());

    }

    // False as test commands are intended to run the entire test mode.
    @Override
    public boolean isFinished()
    {
        return false;
    }

}

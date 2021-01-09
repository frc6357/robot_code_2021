package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Ports;
import frc.robot.RobotContainer;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK20Drive;

/**
 * A default drive command that takes in the filtered joysticks such that the
 * robot drives in teloperated mode.
 */
public class DefaultDriveCommand extends CommandBase {
    private final SK20Drive m_subsystem;

    /**
     * Creates a new DefaultDriveCommand that sets up the member subsystem.
     *
     * @param subsystem The subsystem used by the command to set drivetrain motor
     *                  speeds.
     */
    public DefaultDriveCommand(SK20Drive subsystem) {
        m_subsystem = subsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    /**
     * This method, which is usually run every 20ms, takes in the filtered joystick
     * values and sets the speeds that the drivetrain motors need to achieve.
     */
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double rightTriggerValue = RobotContainer.joystickDriver.getRawAxis(Ports.OIDriverSlowmode);
        m_subsystem.setSlowmode((rightTriggerValue >= TuningParams.SLOWMODE_TRIGGER_THRESHOLD) ? true : false);

        double speedLeft = RobotContainer.joystickDriver.getFilteredAxis(Ports.OIDriverLeftDrive);
        double speedRight = RobotContainer.joystickDriver.getFilteredAxis(Ports.OIDriverRightDrive);
        m_subsystem.setSpeeds(speedLeft, speedRight);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}

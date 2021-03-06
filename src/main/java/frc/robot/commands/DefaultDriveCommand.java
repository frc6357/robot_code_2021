package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK21Drive;
import frc.robot.utils.FilteredJoystick;

/**
 * A default drive command that takes in the filtered joysticks such that the
 * robot drives in teloperated mode.
 */
public class DefaultDriveCommand extends CommandBase {
    private final SK21Drive m_subsystem;
    private final FilteredJoystick joystickDriver;

    /**
     * Creates a new DefaultDriveCommand that sets up the member subsystem.
     *
     * @param subsystem The subsystem used by the command to set drivetrain motor
     *                  speeds.
     * @param joystickDriver The Joystick used for driving
     */
    public DefaultDriveCommand(SK21Drive subsystem, FilteredJoystick joystickDriver) {
        m_subsystem = subsystem;
        this.joystickDriver = joystickDriver;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_subsystem);
    }

    /**
     * This method, which is usually run every 20ms, takes in the filtered joystick
     * values and sets the speeds that the drivetrain motors need to achieve.
     */
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double rightTriggerValue = joystickDriver.getRawAxis(Ports.OIDriverSlowmode);
        m_subsystem.setSlowmode(rightTriggerValue >= TuningParams.SLOWMODE_TRIGGER_THRESHOLD);

        double speedLeft = joystickDriver.getFilteredAxis(Ports.OIDriverLeftDrive);
        double speedRight = joystickDriver.getFilteredAxis(Ports.OIDriverRightDrive);
        m_subsystem.setSpeeds(speedLeft, speedRight);
    }

    // False as default commands are intended to not end.
    @Override
    public boolean isFinished() {
        return false;
    }
}

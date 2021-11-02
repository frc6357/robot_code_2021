package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Ports;
import frc.robot.subsystems.SK21Drive;
import frc.robot.utils.FilteredJoystick;
import frc.robot.utils.filters.CubicDeadbandFilter;

/**
 * A default drive command that takes in the filtered joysticks such that the robot drives
 * in teloperated mode.
 */
public class DefaultDriveCommand extends CommandBase
{
    /**
     * The Drive subsystem for this DefaultDriveCommand.
     */
    private final SK21Drive driveSubsystem;

    /**
     * The Joystick for the Driver.
     */
    private final FilteredJoystick joystickDriver;

    /**
     * Creates a new DefaultDriveCommand that sets up the member subsystem.
     *
     * @param driveSubsystem
     *            The subsystem used by the command to set drivetrain motor speeds
     * @param joystickDriver
     *            The Joystick used for driving
     */
    public DefaultDriveCommand(SK21Drive driveSubsystem, FilteredJoystick joystickDriver)
    {
        this.driveSubsystem = driveSubsystem;
        this.joystickDriver = joystickDriver;
        
        // Applies a Cubic filter with a Deadband to the Turning axis of the joystick.
        // This Cubic filter will have a moderate curvature with a coefficient of 0.6.
        // The Deadband will have a width of 0.05.
        joystickDriver.setFilter(Ports.OIDriverTurn, new CubicDeadbandFilter(0.0, 0.0,0.5, false));
        // no deadband here as the SK21Drive implements the deadband 
        

        // Applies a Cubic Filter with a Deadband to the Moving axis of the joystick.
        // This Cubic filter will have maxmimum curvature with a coefficient of 1.
        // The Deadband will have a width of 0.05.
        // The throttle axis' inputs will be flipped
        joystickDriver.setFilter(Ports.OIDriverMove, new CubicDeadbandFilter(0.0, 0.0, 0.8 ,true));

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    /**
     * This method, which is usually run every 20ms, takes in the filtered joystick values
     * and sets the speeds that the drivetrain motors need to achieve.
     */
    @Override
    public void execute()
    {
        /*
         * TODO: This used to call the slowMode() method on the driveSubsystem dependent
         * upon the value of the joystick bumper. This is now handled by creating a
         * joystick button command which calls setMaxOutput(). Check to make sure this
         * still works!
         */
        double throttle = joystickDriver.getFilteredAxis(Ports.OIDriverMove);
        double turnRate = joystickDriver.getFilteredAxis(Ports.OIDriverTurn);

        

        driveSubsystem.arcadeDrive(throttle, turnRate);
    }

    // False as default commands are intended to not end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Climb;

/**
 * WinchRobotCommand is a Command to control the Climb subsystem on the robot.
 */
public class WinchRobotCommand extends CommandBase
{
    private final SK21Climb subsystem;

    private boolean startWinch; // tells whether or not winch is turned on or not

    /**
     * WinchRobot command tells whether or not to winch or stop winching the robot.
     * 
     * @param subsystem
     *            The SK20Climb subsystem
     * @param startMotor
     *            Indicates whether or not the winch motor should be on or off
     */
    public WinchRobotCommand(SK21Climb subsystem, Boolean startMotor)
    {
        this.subsystem = subsystem;
        startWinch = startMotor;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    @Override
    public void initialize()
    {
        // when true start winch
        if (startWinch)
        {
            subsystem.startWinchRobot();
        }
        // when false stop winch
        else
        {
            subsystem.stopWinchRobot();
        }
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

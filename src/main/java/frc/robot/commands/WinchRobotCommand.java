package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Climb;

/**
 * An example command that uses an example subsystem.
 */
public class WinchRobotCommand extends CommandBase
{
    private final SK21Climb subsystem;

    private boolean startWinch; // tells whether or not winch is turned on or not

    /**
     * WinchRobot command tells whether or not to winch or stop winching the robot.
     * 
     * @param subsystem
     *            is the SK20Climb subsystem
     * @param startMotor
     *            tells whether or not the winch motor should be on or off
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
            this.subsystem.startWinchRobot();
        }
        // when false stop winch
        else
        {
            this.subsystem.stopWinchRobot();
        }
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

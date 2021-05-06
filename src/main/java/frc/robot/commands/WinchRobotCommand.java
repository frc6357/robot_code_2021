package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Climb;

/**
 * An example command that uses an example subsystem.
 */
public class WinchRobotCommand extends CommandBase {
    private final SK21Climb m_subsystem;
    private boolean startWinch; // tells whether or not winch is turned on or not

    /**
     * WinchRobot command tells whether or not to winch or stop winching the robot.
     * 
     * @param subsytem   is the SK20Climb subsystem
     * @param startMotor tells whether or not the winch motor should be on or off
     */
    public WinchRobotCommand(SK21Climb subsystem, Boolean startMotor) 
    {
        m_subsystem = subsystem;
        startWinch = startMotor;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }
 
    @Override
    public void initialize() {
        // when true start winch
        if (startWinch) 
        {
            m_subsystem.startWinchRobot();
        }
        // when false stop winch
        else 
        {
            m_subsystem.stopWinchRobot();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Ports;
import frc.robot.RobotContainer;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK21BallHandling;

/**
 * A command that runs the ball transfer motors. Motors run whenever we do not
 * have a full compliment of 5 balls in the intake/transfer mechanism.
 */
public class DefaultBallHandlingCommand extends CommandBase {
    private final SK21BallHandling subsystem;
    private final boolean endable;

    /**
     * The
     */
    public DefaultBallHandlingCommand(SK21BallHandling subsystem, boolean endable) 
    {
        this.subsystem = subsystem;
        addRequirements(subsystem);
        this.endable = endable;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        if (endable)
        {
            subsystem.startRoller();
        }
        else
        {
            subsystem.stopRoller();
        }
    }

    // Runs once every 20ms
    @Override
    public void execute() {
        if (RobotContainer.joystickOperator.getRawAxis(Ports.OIOperatorActivateIBM) > TuningParams.TRIGGER_THRESHOLD) 
        {
            subsystem.stopRoller();
        } 
        else if (RobotContainer.joystickOperator.getRawAxis(Ports.OIOperatorDeactivateBMI) > TuningParams.TRIGGER_THRESHOLD)
        {
            subsystem.startRoller();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return endable;
    }
}

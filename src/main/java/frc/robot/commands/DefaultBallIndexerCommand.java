package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK21BallIndexer;

/**
 * A command that runs the ball transfer motors. Motors run whenever we do not
 * have a full compliment of 5 balls in the intake/transfer mechanism.
 */
public class DefaultBallIndexerCommand extends CommandBase {
    private final SK21BallIndexer subsystem;
    private final boolean endable;
    private final Joystick joystickOperator;

    /**
     * This is the constructor for the ball hadling command.
     * @param subsystem is the Ball Handling subsystem object that this command uses.
     * @param joystickOperator is the operator joystick object.
     * @param endable 
     * 
     * TODO: What is endable?
     */
    public DefaultBallIndexerCommand(SK21BallIndexer subsystem, Joystick joystickOperator, boolean endable) 
    {
        this.subsystem = subsystem;
        addRequirements(subsystem);
        this.endable = endable;
        this.joystickOperator = joystickOperator;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        if (endable)
        {
            subsystem.startIndexerRotation();
        }
        else
        {
            subsystem.stopIndexerRotation();
        }
    }

    // Runs once every 20ms
    @Override
    public void execute() {
        if (this.joystickOperator.getRawAxis(Ports.OIOperatorActivateIBM) > TuningParams.TRIGGER_THRESHOLD) 
        {
            subsystem.stopIndexerRotation();
        } 
        else if (this.joystickOperator.getRawAxis(Ports.OIOperatorDeactivateBMI) > TuningParams.TRIGGER_THRESHOLD)
        {
            subsystem.startIndexerRotation();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return endable;
    }
}

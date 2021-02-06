package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultBallHandlingCommand;
import frc.robot.subsystems.base.BaseRoller;

/**
 * Sets the methods that are used to hold and control the balls inside of the
 * robot.
 */
public class SK21BallIndexer extends SubsystemBase {
    private CANSparkMax indexerMotor;
    private BaseRoller ballIndexerRoller; 
    private final DefaultBallHandlingCommand ballHandling;
    private boolean systemMotorsAreEnabled = false;
    


    /**
     * Activates the motor that rotates the ball indexer
     */
    public SK21BallIndexer(Joystick joystickOperator)
    {
        indexerMotor = new CANSparkMax(Ports.ballHandlingBelt, MotorType.kBrushless); //change ballHandlingBelt to something else?
        ballIndexerRoller = new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        ballHandling = new DefaultBallHandlingCommand(this, joystickOperator, false);
        setDefaultCommand(ballHandling);
    }

    /**
     * This method starts the motor that spins the indexer
     */
    public void startIndexerRotation()
    {
        ballIndexerRoller.setForwards();
        systemMotorsAreEnabled = true;
    }

    /**
     * This method stops the motor that spins the indexer
     */
    public void stopIndexerRotation()
    {
        ballIndexerRoller.setStop();
        systemMotorsAreEnabled = false;
    }


    public boolean motorsAreEnabled() 
    {
        return systemMotorsAreEnabled;
    }

    /**
     * Activates or deactivates the launcher feeder
     */
    public void dropLauncherFeederArm() 
    {
       //TODO: write this
    }

    public void raiseLauncherFeederArm()
    {
       //TODo: write this
    }

    public void startLauncherFeederRoller()
    {
       //TODO: write this
    }

    public void stopLauncherFeederRoller()
    {
       //TODO: write this
    }

    /**
     * Returns current state of launcher feeder
     * @return current state of launcher feeder (true=yes/false=no)
     */
    public boolean isLauncherFeederArmDropped() 
    {
        // TODO: Implement this later
        return true;
    }

    public boolean isLauncherFeederRollerStarted() 
   {
        return true;
   }
}
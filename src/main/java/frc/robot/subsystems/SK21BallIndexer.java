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
     * Activates the roller that is used for the main ballIndexRoller
     */
    public SK21BallIndexer(Joystick joystickOperator)
    {
        indexerMotor = new CANSparkMax(Ports.ballHandlingBelt, MotorType.kBrushless);
        ballIndexerRoller = new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        ballHandling = new DefaultBallHandlingCommand(this, joystickOperator, false);
        setDefaultCommand(ballHandling);
    }

    /**
     * When activated the rollers are set to run at whatever speed that they're set to run at
     */
    public void startRoller()
    {
        ballIndexerRoller.setForwards();
        systemMotorsAreEnabled = true;
    }

    /**
     * When activated the rollers are set to run at 0 speed
     */
    public void stopRoller()
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
     * @param feederSetState - Tells the setLauncherFeeder if it will be on or off
     */
    public void setLauncherFeeder(boolean feederSetState)
    {

    }

    /**
     * Returns current state of launcher feeder
     * @return current state of launcher feeder
     */
    public boolean getLauncherFeeder()
    {
        // TODO: Implement this later
        return true;
    }
}
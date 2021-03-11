package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultBallIndexerCommand;
import frc.robot.subsystems.base.BaseRoller;

/**
 * Sets the methods that are used to hold and control the balls inside of the robot.
 */
public class SK21BallIndexer extends SubsystemBase
{
    public final CANSparkMax indexerMotor;
    public final BaseRoller indexerRoller;
    private CANSparkMax feederMotor;
    private BaseRoller feederRoller;
    private boolean indexerMotorIsStarted = false;
    private boolean feederMotorIsStarted = false;
    private final DefaultBallIndexerCommand ballIndexer; 

    /**
     * Constructs a new SK21BallIndexer.
     */
    public SK21BallIndexer()
    {
        indexerMotor =
                new CANSparkMax(Ports.indexerMotor, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Ports.feederMotor, MotorType.kBrushless);
        indexerRoller =
                new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        feederRoller = new BaseRoller(feederMotor, TuningParams.INDEXER_SPEED);
        ballIndexer = new DefaultBallIndexerCommand(this);
        resetDefaultCommand();
    }

    public void resetDefaultCommand()
    {
        setDefaultCommand(ballIndexer);
    }

    /**
     * This method starts the motor that spins the indexer
     */
    public void startIndexerRotation()
    {
        indexerRoller.setForwards();
        indexerMotorIsStarted = true;
    }

    /**
     * This method stops the motor that spins the indexer
     */
    public void stopIndexerRotation()
    {
        indexerRoller.setStop();
        indexerMotorIsStarted = false;
    }

    /**
     * Returns the current state of the Indexer Motor
     * 
     * @return the current state of the Indexer Motor
     */
    public boolean isIndexerMotorStarted()
    {
        return indexerMotorIsStarted;
    }

    /**
     * Activates the motor for the rollers to move/pop balls up to the launcher.
     */
    public void startLauncherFeederMotor()
    {
        feederRoller.setForwards();
        feederMotorIsStarted = true;
    }

    /**
     * Deactivates the motor for the rollers that move/pop balls up to the launcher.
     */
    public void stopLauncherFeederMotor()
    {
        feederRoller.setStop();
        feederMotorIsStarted = false;
    }

    /**
     * Returns current state of the Feeder Motor
     * 
     * @return current state of the feeder motor (true=yes/false=no)
     */
    public boolean isLauncherFeederMotorStarted()
    {
        return feederMotorIsStarted;
    }


}

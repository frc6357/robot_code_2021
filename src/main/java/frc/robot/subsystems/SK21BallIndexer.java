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
    /**
     * The motor controller to be used in the BaseRoller of the Indexer.
     */
    public final CANSparkMax indexerMotor;

    /**
     * The BaseRoller for the Indexer.
     */
    public final BaseRoller indexerRoller;

    /**
     * TODO These being private indicates that they are not currently access by the Test
     * Commands.
     */
    private CANSparkMax feederMotor;
    private BaseRoller feederRoller;

    private final DefaultBallIndexerCommand ballIndexer;

    /**
     * Constructs a new SK21BallIndexer.
     */
    public SK21BallIndexer()
    {
        indexerMotor = new CANSparkMax(Ports.indexerMotor, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Ports.feederMotor, MotorType.kBrushless);
        indexerRoller = new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        feederRoller = new BaseRoller(feederMotor, TuningParams.INDEXER_SPEED);
        /*
         * TODO "this" escaping from a constructor should be avoided if possible - it
         * indicates a circular reference, and in certain conditions can cause programs to
         * crash. A better methodology here is similar to what is used in SK21Drive, where
         * the default command is external to the subsystem.
         */
        ballIndexer = new DefaultBallIndexerCommand(this);
        resetDefaultCommand();
    }

    /**
     * Resets the default command for this subsystem to the command used during
     * auto/teleop.
     */
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
    }

    /**
     * This method stops the motor that spins the indexer
     */
    public void stopIndexerRotation()
    {
        indexerRoller.setStop();
    }

    /**
     * Activates the motor for the rollers to move/pop balls up to the launcher.
     */
    public void startLauncherFeederMotor()
    {
        feederRoller.setForwards();
    }

    /**
     * Deactivates the motor for the rollers that move/pop balls up to the launcher.
     */
    public void stopLauncherFeederMotor()
    {
        feederRoller.setStop();
    }
}

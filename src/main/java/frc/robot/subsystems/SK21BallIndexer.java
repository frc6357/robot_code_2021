package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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

    private CANSparkMax feederMotor;
    private BaseRoller feederRoller;
    
    public final DoubleSolenoid feederArmSolenoid;

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
        feederArmSolenoid = new DoubleSolenoid(Ports.pcm, Ports.launcherFeederExtend, Ports.launcherFeederRetract);

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
     * Activates the launcher feeder Arm to extend. The launcher feeder will then pop
     * balls into the launcher. The rollers that are run by the motor do this
     * action not the arm itself. 
     */
    public void extendLauncherFeederArm()
    {
        feederArmSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Deactivates (retracts) the launcher feeder Arm to extend. When the arm is
     * retracted the launcher feeder should no longer pop balls into the 
     * launcher. 
     */
    public void retractLauncherFeederArm()
    {
        feederArmSolenoid.set(DoubleSolenoid.Value.kReverse);
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

    /**
     * Returns current state of launcher feeder by using an if statement.
     * 
     * @return current state of launcher feeder (true=yes/false=no)
     */
    public boolean isLauncherFeederArmExtended()
    {
        DoubleSolenoid.Value state;
        state = this.feederArmSolenoid.get();
        
        if (state == DoubleSolenoid.Value.kForward)
        {
            return true;
        }  
        else
        {
            return false;
        }
    }

}

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultBallIndexerCommand;
import frc.robot.subsystems.base.BaseRoller;

/**
 * Sets the methods that are used to hold and control the balls inside of the
 * robot.
 */
public class SK21BallIndexer extends SubsystemBase {
    private CANSparkMax indexerMotor;
    private BaseRoller indexerRoller; 
    private CANSparkMax feederMotor;
    private BaseRoller feederRoller; 
    private boolean indexerMotorIsStarted = false;
    private boolean feederMotorIsStarted = false;
    private boolean feederArmIsExtended = false;
    private DoubleSolenoid feederArmSolenoid;
    private final DefaultBallIndexerCommand ballIndexer; 
    


    /**
     * Activates the motor that rotates the ball indexer
     */
    public SK21BallIndexer(Joystick joystickOperator)
    {
        indexerMotor = new CANSparkMax(Ports.indexerMotor, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Ports.feederMotor, MotorType.kBrushless); 
        indexerRoller = new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        feederRoller = new BaseRoller(feederMotor, TuningParams.INDEXER_SPEED);
        feederArmSolenoid = new DoubleSolenoid(Ports.pcm, Ports.launcherFeederRetract, Ports.launcherFeederExtend);
        ballIndexer = new DefaultBallIndexerCommand(this, joystickOperator, false);
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
     * @return the current state of the Indexer Motor
     */
    public boolean isIndexerMotorStarted() 
    {
        return indexerMotorIsStarted;
    }

    /**
     * Activates or deactivates the launcher feeder Arm
     * extend - the launcher feeder can pop out the ball
     * retract - the launcher feeder does nothing
     */
    public void extendLauncherFeederArm() 
    {
        feederArmSolenoid.set(DoubleSolenoid.Value.kForward);
        feederArmIsExtended = true;
    } 

    public void retractLauncherFeederArm()
    {
       feederArmSolenoid.set(DoubleSolenoid.Value.kReverse);
       feederArmIsExtended = false;
    }
    /**
     * startLauncherFeederMotor activates the motor for the rollers to push out the balls
     * stopLauncherFeederMotor deactivates the motor and the rollers - this should be 
     * used when the Arm lowers 
     */
    public void startLauncherFeederMotor()
    {
        feederRoller.setForwards();
        feederMotorIsStarted = true;
    }

    public void stopLauncherFeederMotor()
    {
        feederRoller.setStop();
        feederMotorIsStarted = false;
    }

    /**
     * Returns current state of the Feeder Motor
     * @return current state of the feeder motor (true=yes/false=no)
     */
    public boolean isLauncherFeederMotorStarted() 
    {
        return feederMotorIsStarted;
    }

    /**
     * Returns current state of launcher feeder
     * @return current state of launcher feeder (true=yes/false=no)
     */
    public boolean isLauncherFeederArmExtended() 
    {
        return feederArmIsExtended;
    }

}
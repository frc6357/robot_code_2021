package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
    private BaseRoller indexerRoller; 
    private CANSparkMax feederMotor;
    private BaseRoller feederRoller; 
    private boolean indexerMotorIsStarted = false;
    private boolean feederMotorIsStarted = false;
    private boolean feederArmIsDropped = false;
    private DoubleSolenoid feederArmSolenoid;
    private final DefaultBallHandlingCommand ballHandling; //change name
    


    /**
     * Activates the motor that rotates the ball indexer
     */
    public SK21BallIndexer(Joystick joystickOperator)
    {
        indexerMotor = new CANSparkMax(Ports.indexerMotor, MotorType.kBrushless);
        feederMotor = new CANSparkMax(Ports.feederMotor, MotorType.kBrushless); 
        indexerRoller = new BaseRoller(indexerMotor, TuningParams.INDEXER_SPEED);
        feederRoller = new BaseRoller(feederMotor, TuningParams.INDEXER_SPEED);
        feederArmSolenoid = new DoubleSolenoid(Ports.pcm, Ports.launcherFeederDrop, Ports.launcherFeederRaise);
        ballHandling = new DefaultBallHandlingCommand(this, joystickOperator, false);
        setDefaultCommand(ballHandling);
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


    public boolean isIndexerMotorStarted() 
    {
        return indexerMotorIsStarted;
    }

    /**
     * Activates or deactivates the launcher feeder
     * extend - the launcher feeder can pop out the ball
     * retract - the launcher feeder does nothing
     */
    public void extendLauncherFeederArm() 
    {
        //TODO: write this
    } 

    public void retractLauncherFeederArm()
    {
       //TODO: write this
    }

    public void startLauncherFeederMotor()
    {
       //TODO: write this
    }

    public void stopLauncherFeederMotor()
    {
       //TODO: write this
    }

    public boolean isLauncherFeederMotorStarted() 
    {
        return feederMotorIsStarted;
    }

    /**
     * Returns current state of launcher feeder
     * @return current state of launcher feeder (true=yes/false=no)
     */
    public boolean isLauncherFeederArmDropped() 
    {
        return feederArmIsDropped;
    }

}
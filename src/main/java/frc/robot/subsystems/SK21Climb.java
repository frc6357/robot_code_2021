package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultClimbCommand;

/**
 * The SK20Climb class is a subsystem that interacts with the climbing mechanism in order to deploy the arm and winch the robot up
 */
public class SK21Climb extends SubsystemBase 
{
    //instantiates climb mechanisms
    private WPI_TalonFX winchClimbLeft;
    private WPI_TalonFX winchClimbRight;
    public SpeedControllerGroup winchMotorGroup;

    private final DefaultClimbCommand climb;
    
    //assigns values to instantiated objects
    public SK21Climb()
    { 
        winchClimbLeft = new WPI_TalonFX(Ports.winchClimbLeft);
        winchClimbRight = new WPI_TalonFX(Ports.winchClimbRight);
        winchClimbLeft.setInverted(TuningParams.WINCH_LEFT_MOTOR_INVERT);
        winchClimbRight.setInverted(!TuningParams.WINCH_LEFT_MOTOR_INVERT);
        winchClimbLeft.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, TuningParams.WINCH_MOTOR_CURRENT_LIMIT, TuningParams.WINCH_MOTOR_CURRENT_TRIGGER, TuningParams.WINCH_MOTOR_TRIGGER_TIME));
        winchClimbRight.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, TuningParams.WINCH_MOTOR_CURRENT_LIMIT, TuningParams.WINCH_MOTOR_CURRENT_TRIGGER, TuningParams.WINCH_MOTOR_TRIGGER_TIME));
        winchMotorGroup = new SpeedControllerGroup(winchClimbLeft, winchClimbRight);

        /*
         * TODO "this" escaping from a constructor should be avoided if possible - it
         * indicates a circular reference, and in certain conditions can cause programs to
         * crash. A better methodology here is similar to what is used in SK21Drive, where
         * the default command is external to the subsystem.
         */
        climb = new DefaultClimbCommand(this);
        resetDefaultCommand();
    }
    
    /**
     * Resets the default command for this subsystem to the command used during
     * auto/teleop.
     */
    public void resetDefaultCommand()
    {
        setDefaultCommand(climb);
    }


    /*
     * When the startWinchRobot method is called a motor will start to winch the
     * entirity of the robot upwards
     */
    public void startWinchRobot() 
    {
        winchMotorGroup.set(TuningParams.WINCH_MOTOR_SPEED);
    }

    /*
     * When the stopWinchRobot method is called the motor winching the robot will be
     * stopped.
     */
    public void stopWinchRobot() 
    {
        winchMotorGroup.stopMotor();
    }

}
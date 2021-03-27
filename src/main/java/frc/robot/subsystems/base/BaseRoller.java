package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subsystems.base.SuperClasses.RollerDirection;

/**
 * Base class to be extended by any class requiring rollers.
 */
public class BaseRoller
{
    /**
     * The underlying SpeedController for this BaseRoller.
     */
    private final SpeedController motorController;

    /**
     * The speed to be used by setForwards (or interted for setBackwards).
     */
    private double speed = 1.0;

    /**
     * The current speed (meaning the speed last sent to the motors by setForwards or
     * setBackwards).
     */
    private double currentSpeed = 0.0;

    /**
     * The current direction of the roller.
     */
    private RollerDirection directionReader;

    /**
     * Consructs a new BaseRoller that uses the given SpeedController to control the
     * motors. Initial speed is set to zero.
     * 
     * @param motorController
     *            The motor controller used to control the speed of the motors in the
     *            BaseRoller
     * 
     */
    public BaseRoller(SpeedController motorController)
    {
        this.motorController = motorController;
        setSpeed(0);
    }

    /**
     * Constructs a new BaseRoller that uses the given SpeedController to control the
     * motors and the given speed as the initial speed to be used for the roller.
     * 
     * Note: Even if a non-zero value is provided for the speed, this constructor does not
     * start the motors. Either setForwards() or setBackwards() must be called to start
     * the motors.
     * 
     * @param motorController
     *            The SpeedController for the BaseRoller
     * @param speed
     *            Set the initial speed of the roller
     */
    public BaseRoller(SpeedController motorController, double speed)
    {
        this.motorController = motorController;
        this.speed = speed;
    }

    /**
     * Directs the roller to be spinning forwards.
     */
    public void setForwards()
    {
        if (currentSpeed != speed)
        {
            currentSpeed = speed;
            motorController.set(currentSpeed);
        }
        directionReader = RollerDirection.FORWARD;
    }

    /**
     * Directs the roller to be spinning backwards at a speed set by the speed.
     */
    public void setBackwards()
    {
        if (currentSpeed != -speed)
        {
            currentSpeed = -speed;
            motorController.set(currentSpeed);
        }
        directionReader = RollerDirection.BACKWARD;
    }

    /**
     * Directs the roller to stop.
     */
    public void setStop()
    {
        if (currentSpeed != 0.0)
        {
            currentSpeed = 0.0;
            motorController.set(0.0);
        }
        directionReader = RollerDirection.STOPPED;
    }

    /**
     * Returns the direction that the roller is set to.
     * 
     * @return The RollerDirection indicating the direction of the roller.
     */
    public RollerDirection getDirection()
    {
        return directionReader;
    }

    /**
     * Sets the speed that is used by the SpeedController for this BaseRoller.
     * 
     * Note: This speed will be used when setForwards() or setBackwards() is next called;
     * it will not immediately impact the speed.
     * 
     * @param newSpeed
     *            The new speed of the rollers
     */
    public void setSpeed(double newSpeed)
    {
        this.speed = newSpeed;
    }

    /**
     * Returns the speed that is to be used for the underlying SpeedController.
     * 
     * @return The speed that the SpeedController is utilizing
     */
    public double returnSpeed()
    {
        return this.speed;
    }

    /**
     * Warning: Function for test use only. 
     * 
     * This sets the roller to run at the commanded speed,
     * regardless of the speed set during initialization.
     * 
     * @param speed
     *            The speed to set the roller at
     */
    public void testSetSpeed(double speed)
    {
        currentSpeed = speed;
        motorController.set(speed);
        directionReader = (speed > 0.0) ? RollerDirection.FORWARD
            : ((speed == 0.0) ? RollerDirection.STOPPED : RollerDirection.BACKWARD);
    }

    /**
     * Retrieve the current speed of the roller. This is set each time setForwards() or
     * setBackwards() is called. Note this is the theoretical speed, it is NOT the speed
     * returned by an Encoder.
     * 
     * @return The current speed being asserted by the BaseRoller
     */
    public double testGetSpeed()
    {
        return currentSpeed;
    }
}

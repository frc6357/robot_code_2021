package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subsystems.base.SuperClasses.RollerDirection;

/**
 * Base class to be extended by any class requiring rollers
 */
public class BaseRoller
{
    // Speed Controller
    private final SpeedController motorController;
    private double speed = 1.0;
    private double currentSpeed = 0.0;

    private RollerDirection directionReader;

    /**
     * Consructs a new BaseRoller that uses the given SpeedController to control the
     * motors.
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
     * Consructs a new BaseRoller that uses the given SpeedController to control the
     * motors and the given speed as the initial speed of the motors.
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
     * Sets the roller motor/s to be spinning forwards.
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
     * Sets the roller motor/s to be spinning backwards at a speed set by the speed
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
     * Sets the roller motors to stop.
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
     * Returns the direction that the motor is set to
     * 
     * @return - Type int - Values of -1, 0, 1 - Used to check whether motor is stopped,
     *         moving forwards or backwards
     */
    public RollerDirection getDirection()
    {
        return directionReader;
    }

    /**
     * Sets or changes the speed that is used by the motor controller
     * 
     * @param newSpeed
     *            - Type double - Changes the speed of the rollers
     */
    public void setSpeed(double newSpeed)
    {
        this.speed = newSpeed;
    }

    /**
     * Returns the speed that is set to the default motherboard
     * 
     * @return - Type double - Current speed that the motor controller is utilizing
     */
    public double returnSpeed()
    {
        return this.speed;
    }

    /**
     * Function for test use only. This sets the motor to run at the commanded speed,
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
     * Retrieve the current speed of the roller.
     * 
     * @return The current speed that the encoder reads
     */
    public double testGetSpeed()
    {
        return currentSpeed;
    }
}

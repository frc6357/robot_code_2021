package frc.robot.subsystems;

import frc.robot.TuningParams;
import frc.robot.subsystems.base.SuperClasses.BaseDrive;

/**
 * The SmoothDrive class provides an interface suitable for implementing the 2020 tank
 * drive. It allows the motors to accelerate evenly and proportionately to prevent
 * skidding and jerking on the robot. It sets the motors by using the BaseDrive class.
 */
public class SmoothDrive
{
    private final BaseDrive drive;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private double[] speedTarget = {0.0, 0.0};
    private double[] speedCurrentTarget = {0.0, 0.0};

    /**
     * This constructor accepts the BaseDrive object that allows it to interface the robot
     * hardware.
     * 
     * @param drive
     *            A basicDrive type used to set up the baseDrive in SmoothDrive
     */
    public SmoothDrive(BaseDrive drive)
    {
        this.drive = drive;
    }

    /**
     * This method sets the target speeds we want to reach for each side of the
     * drivetrain.
     * 
     * @param speedLeft
     *            Speed target we want to reach on the left side of the drivetrain
     * @param speedRight
     *            Speed target we want to reach on the right side of the drivetrain
     */
    public void setSpeeds(double speedLeft, double speedRight)
    {
        speedTarget[LEFT] = speedLeft;
        speedTarget[RIGHT] = speedRight;
    }

    /**
     * This method must be called from the relevant top level "periodic" call. First, it
     * calculates the speed that the left and right motors need to achieve, then makes
     * sure both sides are accelerated proportionatly. It is responsible for updating the
     * current drivetrain motor speeds to decellerate/accelerate towards the given value
     * without exceeding the acceleration limits.
     */
    public void smoothDrivePeriodic()
    {
        double[] delta = {speedTarget[LEFT] - speedCurrentTarget[LEFT],
            speedTarget[RIGHT] - speedCurrentTarget[RIGHT]};
        boolean leftIsLarger = Math.abs(delta[LEFT]) > Math.abs(delta[RIGHT]);
        double[] speedNew = leftIsLarger ? calculateNewSpeeds(delta, LEFT, RIGHT)
            : calculateNewSpeeds(delta, RIGHT, LEFT);
        drive.setSpeed(speedNew[LEFT], speedNew[RIGHT]);
        speedCurrentTarget = speedNew;
    }

    /**
     * This method takes the delta values, the accel side that needs to be scaled, and the
     * one that doesn't need to be scaled so that it can calculate how to scale the accel
     * values proportionately. It then uses those scaled accel values and returns the new
     * speeds to put on the motors.
     * 
     * @param delta
     *            The change between the target speed and the current speed.
     * @param unscaled
     *            The drivetrain side that will be unscaled for acceleration amount
     * @param scaled
     *            The drivetrain side that will be scaled to accelrate proportionately
     *            with the other side
     * @return The speeds we need to set the new speeds of motors.
     */
    private double[] calculateNewSpeeds(double[] delta, int unscaled, int scaled)
    {
        if (delta[unscaled] == 0.0)
        {
            return speedCurrentTarget;
        }
        double[] speedNew = new double[2];
        speedNew[scaled] = calculateSendSpeed(scaled, Math.abs(delta[scaled] / delta[unscaled]));
        speedNew[unscaled] = calculateSendSpeed(unscaled, 1.0);
        return speedNew;
    }

    /**
     * This method takes in the side of which the motors are being calculated and uses it
     * to make sure that the motor speed does not exceed the targeted speed if accelerated
     * regularly. If the acceleration would exceed the targeted speed, then the speed will
     * just be set to the targeted value.
     * 
     * @param side
     *            The side of the drivetrain
     * @return The exact unscaled speed that the motors should be set to
     */
    private double calculateSendSpeed(int side, double scalingFactor)
    {
        double acceleration = getAccel(speedTarget[side], speedCurrentTarget[side]);
        double driveSpeed = speedCurrentTarget[side] + acceleration * scalingFactor;
        // If the speed to be set has just crossed the set speed in the correct
        // direction
        // then set the speed to the setpoint.
        if (Math.signum(acceleration) == Math.signum(driveSpeed - speedTarget[side]))
        {
            driveSpeed = speedTarget[side];
        }
        return driveSpeed;
    }

    /**
     * This method takes the targeted speed and the current speed we are trying to target
     * and find whether we are accelerating or decelerating and to see which direction we
     * are trying to target (forward or backward).
     * 
     * @param target
     *            The target speed that we are trying to reach.
     * @param currentTarget
     *            The acceleration incremented speed that we last sent to the motors.
     * @return The limit for the acceleration depending on the current target we
     *         last sent to the motors and the final target we are attempting to reach.
     */
    public double getAccel(double target, double currentTarget)
    {
        if (target == currentTarget)
        {
            return 0.0;
        }
        else if (target > currentTarget)
        {
            if (target >= 0)
            {
                return TuningParams.ACCEL_MAX_TOWARDS_FORWARD;
            }
            else
            {
                return TuningParams.DECEL_MAX_TOWARDS_FORWARD;
            }
        }
        else
        {
            if (target <= 0)
            {
                return TuningParams.ACCEL_MAX_TOWARDS_BACKWARD;
            }
            else
            {
                return TuningParams.DECEL_MAX_TOWARDS_BACKWARD;
            }
        }
    }
}

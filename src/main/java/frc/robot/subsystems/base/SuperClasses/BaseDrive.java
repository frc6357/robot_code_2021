package frc.robot.subsystems.base.SuperClasses;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The base class for any 2 or 3 motor sided drive train that has multiple subclasses.
 */
public class BaseDrive
{
    private final SpeedControllerGroup motorGroupLeft, motorGroupRight;
    private final DifferentialDrive driveDiff;

    /**
     * Constructor of the BaseDrive and Sets up Differential Drive
     * 
     * @param motorGroupLeft
     *            A group containing all the motors on the left side of the drivetrain
     * @param motorGroupRight
     *            A group containing all the motors on the right side of the drivetrain
     * @param shiftPolarity
     *            Tells us what the default gear setting is for the robot
     */
    public BaseDrive(SpeedControllerGroup motorGroupLeft,
        SpeedControllerGroup motorGroupRight)
    {
        this.motorGroupLeft = motorGroupLeft;
        this.motorGroupRight = motorGroupRight;
        driveDiff = new DifferentialDrive(motorGroupLeft, motorGroupRight);
    }

    /**
     * Sets the speed for the left and right side of the drivetrain
     * 
     * @param speedLeft
     *            A number between -1.0 and 1.0 to set speed of the left side of the
     *            drivetrain
     * @param speedRight
     *            A number between -1.0 and 1.0 to set speed of the right side of the
     *            drivetrain - The speed that the motor controller is going to be set to,
     *            1 for full forwards and -1 for full back
     */
    public void SetSpeed(double speedLeft, double speedRight)
    {
        driveDiff.tankDrive(speedLeft, speedRight);
    }

    /**
     * Returns the current set speed for the left side of the drivetrain
     * 
     * @return double - Current set speed of motor controller, 1 for full forwards, -1 for
     *         full back
     */
    public double getLeftSpeed()
    {
        return motorGroupLeft.get();
    }

    /**
     * Returns the current set speed for the right side of the drivetrain
     * 
     * @return double - Current set speed of motor controller, 1 for full forwards, -1 for
     *         full back
     */
    public double getRightSpeed()
    {
        return motorGroupRight.get();
    }

}
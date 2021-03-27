package frc.robot.subsystems.base.SuperClasses;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The base class for any 2 or 3 motor sided drive train that has multiple subclasses.
 * This leverates DifferentialDrive in order to leverage all of the capabilities delivered
 * in that WPILib class.
 */
public class BaseDrive
{
    private final SpeedControllerGroup motorGroupLeft, motorGroupRight;
    private final DifferentialDrive driveDiff;

    /**
     * Constructs a BaseDrive from the given SpeedControllerGroups.
     * 
     * @param motorGroupLeft
     *            A group containing all the motors on the left side of the drivetrain
     * @param motorGroupRight
     *            A group containing all the motors on the right side of the drivetrain
     */
    public BaseDrive(SpeedControllerGroup motorGroupLeft, SpeedControllerGroup motorGroupRight)
    {
        this.motorGroupLeft = motorGroupLeft;
        this.motorGroupRight = motorGroupRight;
        driveDiff = new DifferentialDrive(motorGroupLeft, motorGroupRight);
    }

    /**
     * Sets the speed for the left and right side of the drivetrain.
     * 
     * @param speedLeft
     *            A number between -1.0 and 1.0 to set speed of the left side of the
     *            drivetrain
     * @param speedRight
     *            A number between -1.0 and 1.0 to set speed of the right side of the
     *            drivetrain
     */
    public void setSpeed(double speedLeft, double speedRight)
    {
        driveDiff.tankDrive(speedLeft, speedRight);
    }

    /**
     * Returns the current set speed for the left side of the drivetrain.
     * 
     * @return The current set speed of motor controller, 1 for full forwards, -1 for full
     *         back
     */
    public double getLeftSpeed()
    {
        return motorGroupLeft.get();
    }

    /**
     * Returns the current set speed for the right side of the drivetrain.
     * 
     * @return The current set speed of motor controller, 1 for full forwards, -1 for full
     *         back
     */
    public double getRightSpeed()
    {
        return motorGroupRight.get();
    }

}

package frc.robot.subsystems.base.SuperClasses;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.utils.ScaledEncoder;

/**
 * The base class for any 2 or 3 motor sided drive train that has multiple
 * subclasses.
 */
public class BaseDrive {
    private final SpeedControllerGroup motorGroupLeft, motorGroupRight;
    private final DifferentialDrive driveDiff;
    private final ScaledEncoder encoderLeft, encoderRight;
    private final DoubleSolenoid gearShiftSolenoid;
    private double lastLeftSetSpeed = 0;
    private double lastRightSetSpeed = 0;

    /**
     * Constructor of the BaseDrive and Sets up Differential Drive
     * 
     * @param motorGroupLeft    A group containing all the motors on the left side
     *                          of the drivetrain
     * @param motorGroupRight   A group containing all the motors on the right side
     *                          of the drivetrain
     * @param encoderLeft       An encoder on the left side of the drivetrain on the
     *                          robot
     * @param encoderRight      An encoder on the right side of the drivetrain on
     *                          the robot
     * @param gearShiftSolenoid Contains the solenoid that allows for the gear
     *                          shifts on the robot
     * @param shiftPolarity     Tells us what the default gear setting is for the
     *                          robot
     */
    public BaseDrive(SpeedControllerGroup motorGroupLeft, SpeedControllerGroup motorGroupRight,
            ScaledEncoder encoderLeft, ScaledEncoder encoderRight, DoubleSolenoid gearShiftSolenoid) {
        this.motorGroupLeft = motorGroupLeft;
        this.motorGroupRight = motorGroupRight;
        this.encoderLeft = encoderLeft;
        this.encoderRight = encoderRight;
        this.gearShiftSolenoid = gearShiftSolenoid;
        driveDiff = new DifferentialDrive(motorGroupLeft, motorGroupRight);
    }

    /**
     * Sets the speed for the left and right side of the drivetrain
     * 
     * @param speedLeft  A number between -1.0 and 1.0 to set speed of the left side
     *                   of the drivetrain
     * @param speedRight A number between -1.0 and 1.0 to set speed of the right
     *                   side of the drivetrain - The speed that the motor
     *                   controller is going to be set to, 1 for full forwards and
     *                   -1 for full back
     */
    public void SetSpeed(double speedLeft, double speedRight) 
    {
            driveDiff.tankDrive(speedLeft, speedRight);
            speedLeft = lastLeftSetSpeed;
            speedRight = lastRightSetSpeed;        
    }

    /**
     * Grabs the raw values from the left encoder
     * 
     * @return The raw int value
     */
    public int getLeftEncoderRaw() {
        return encoderLeft.getRaw();
    }

    /**
     * Grabs the raw values from the right encoder
     * 
     * @return the raw int value
     */
    public int getRightEncoderRaw() {
        return encoderRight.getRaw();
    }

    /**
     * This method is used to query the number of rotations the left encoder has
     * recorded since the last time it was reset.
     *
     * @return Returns the number of full rotations the left encoder has recorded.
     */
    public double getLeftEncoderRotations() {
        return encoderLeft.getRotations();
    }

    /**
     * This method is used to query the number of rotations the right encoder has
     * recorded since the last time it was reset.
     *
     * @return Returns the number of full rotations the right encoder has recorded.
     */
    public double getRightEncoderRotations() {
        return encoderRight.getRotations();
    }

    /**
     * This method is used to query the distance the left encoder has recorded since
     * the last time it was reset.
     *
     * @return Returns the number of centimeters the left encoder has measured.
     */
    public double getLeftEncoderDistance() {
        return encoderLeft.getDistance();
    }

    /**
     * This method is used to query the distance the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of centimeters the right encoder has measured.
     */
    public double getRightEncoderDistance() {
        return encoderRight.getDistance();
    }

    /**
     * Sets the gear for both sides of the drivetrain.
     * 
     * @param newGear The gear value that we want the robot to achieve.
     */
    public void setGear(Gear newGear) {
            DoubleSolenoid.Value check = (newGear == Gear.HIGH) ? DoubleSolenoid.Value.kForward: DoubleSolenoid.Value.kReverse;
            gearShiftSolenoid.set(check);
    }

    /**
     * Returns if the gear is HIGH or LOW
     * 
     * @return Gear - One of the enum value (HIGH, LOW) to show what gear the robot
     *         is on.
     */
    public Gear getGear() {
        Gear toReturn = Gear.HIGH;
        toReturn = (gearShiftSolenoid.get() == DoubleSolenoid.Value.kForward) ? Gear.HIGH: Gear.LOW;
        
        return toReturn;
    }

    /**
     * Returns the current set speed for the left side of the drivetrain
     * 
     * @return double - Current set speed of motor controller, 1 for full forwards,
     *         -1 for full back
     */
    public double getLeftSpeed() {
        return motorGroupLeft.get();
    }

    /**
     * Returns the current set speed for the right side of the drivetrain
     * 
     * @return double - Current set speed of motor controller, 1 for full forwards,
     *         -1 for full back
     */
    public double getRightSpeed() {
        return motorGroupRight.get();
    }

}
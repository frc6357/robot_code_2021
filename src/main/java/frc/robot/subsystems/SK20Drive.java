package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.base.SuperClasses.BaseDrive;
import frc.robot.subsystems.base.SuperClasses.Gear;
import frc.robot.utils.ScaledEncoder;

public class SK20Drive extends SubsystemBase {

    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Ports.frontLeftDrive);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(Ports.backLeftDrive);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Ports.frontRightDrive);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(Ports.backRightDrive);

    private final SpeedControllerGroup motorGroupLeft = new SpeedControllerGroup(frontLeft, backLeft);
    private final SpeedControllerGroup motorGroupRight = new SpeedControllerGroup(frontRight, backRight);

    private final ScaledEncoder encoderLeft = new ScaledEncoder(Ports.leftEncoderA, Ports.leftEncoderB,
            TuningParams.ENCODER_LEFT_REVERSED, TuningParams.ENCODER_PULSES, TuningParams.WHEEL_DIAMETER);
    private final ScaledEncoder encoderRight = new ScaledEncoder(Ports.rightEncoderA, Ports.rightEncoderB,
            TuningParams.ENCODER_RIGHT_REVERSED, TuningParams.ENCODER_PULSES, TuningParams.WHEEL_DIAMETER);

    private final DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(Ports.pcm, Ports.gearShifterA, Ports.gearShifterB);

    private final BaseDrive drive = new BaseDrive(motorGroupLeft, motorGroupRight, encoderLeft, encoderRight, gearShiftSolenoid);
    private final SmoothDrive smoothDrive = new SmoothDrive(drive);
    private final DefaultDriveCommand driveCommand;

    private final ADIS16448_IMU imu = new ADIS16448_IMU();

    private double speedMultiplier = 1.0;

    /**
     * This constructor of the SK20Drive sets up the BaseDrive object and passes it
     * into the SmoothDrive object to set it up.
     */
    public SK20Drive() {
        driveCommand = new DefaultDriveCommand(this);
        setDefaultCommand(driveCommand);
        SmartDashboard.putNumber("left Encoder", encoderLeft.getRotations());
        SmartDashboard.putNumber("right Encoder", encoderRight.getRotations());
        setGear(Gear.LOW);
    }

    /**
     * This method sets both speeds for the robot
     * 
     * @param speedLeft  The desired for the left side of drivetrain
     * @param speedRight The desired for the right side of drivetrain
     */
    public void setSpeeds(double speedLeft, double speedRight) {
        smoothDrive.setSpeeds(speedLeft * speedMultiplier, speedRight * speedMultiplier);
    }

    /**
     * This method sets the motors directly to 0 without any deceleration value.
     */
    public void emergencyStop() {
        smoothDrive.setSpeeds(0, 0);
        drive.SetSpeed(0, 0);
    }

    public void setSlowmode(boolean enabled) {
        speedMultiplier = enabled ? TuningParams.SLOWMODE_MULTIPLIER : 1.0;
    }

    /**
     * This method is used to set the gear on the drivetrain.
     * 
     * @param gearTarget The target gear we want to reach.
     */
    public void setGear(Gear gearTarget) {
        drive.setGear(gearTarget);
    }

    /**
     * This method is used to find which gear the robot is currently on.
     * 
     * @return Gear - The gear we are on (HIGH or LOW).
     */
    public Gear getGear() {
        return drive.getGear();
    }

    /**
     * This method is used to query the distance the left encoder has recorded since
     * the last time it was reset.
     *
     * @return Returns the number of centimeters the left encoder has measured.
     */
    public double getLeftEncoderDistance() {
        return drive.getLeftEncoderDistance();
    }

    /**
     * This method is used to query the distance the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of centimeters the right encoder has measured.
     */
    public double getRightEncoderDistance() {
        return drive.getRightEncoderDistance();
    }

    /**
     * This method is used to query the revolutions the left encoder has recorded
     * since the last time it was reset.
     * 
     * @return Returns the amount of revolutions the left wheel has turned.
     */
    public double getLeftEncoderRevolutions() {
        return drive.getLeftEncoderRotations();
    }

    /**
     * This method is used to query the revolutions the right encoder has recorded
     * since the last time it was reset.
     * 
     * @return Returns the amount of revolutions the right wheel has turned.
     */
    public double getRightEncoderRevolutions() {
        return drive.getRightEncoderRotations();
    }

    /**
     * This method is used to query the raw pulses the left encoder has recorded
     * since the last time it was reset.
     * 
     * @return Returns the raw value that the left encoder has read.
     */
    public int getLeftEncoderRaw() {
        return drive.getLeftEncoderRaw();
    }

    /**
     * This method is used to query the raw pulses the right encoder has recorded
     * since the last time it was reset.
     * 
     * @return Returns the raw value that the right encoder has read.
     */
    public int getRightEncoderRaw() {
        return drive.getRightEncoderRaw();
    }

    /**
     * Resets the gyro value
     */
    public void resetGyro() {
        imu.reset();
    }

    /**
     * Finds the angle of the robot(in the plane parallel to the field) relative to
     * the last reset
     * 
     * @return double - The value the gyro says we are turned
     */
    public double getAngle() {
        return imu.getGyroAngleZ();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Left Wheel Revolution", getLeftEncoderRevolutions());
        SmartDashboard.putNumber("Right Wheel Revolution", getRightEncoderRevolutions());
        SmartDashboard.putNumber("Left Wheel Distance", getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Wheel Distance", getRightEncoderDistance());
        SmartDashboard.putNumber("Left Wheel Raw", getLeftEncoderRaw());
        SmartDashboard.putNumber("Right Wheel Raw", getRightEncoderRaw());
        smoothDrive.SmoothDrivePeriodic();
    }

}
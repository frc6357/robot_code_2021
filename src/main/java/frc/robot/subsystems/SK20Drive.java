package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.base.SuperClasses.BaseDrive;
import frc.robot.utils.FilteredJoystick;

public class SK20Drive extends SubsystemBase {

    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Ports.frontLeftDrive);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(Ports.backLeftDrive);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Ports.frontRightDrive);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(Ports.backRightDrive);

    private final SpeedControllerGroup motorGroupLeft = new SpeedControllerGroup(frontLeft, backLeft);
    private final SpeedControllerGroup motorGroupRight = new SpeedControllerGroup(frontRight, backRight);

    private final BaseDrive drive = new BaseDrive(motorGroupLeft, motorGroupRight);
    private final SmoothDrive smoothDrive = new SmoothDrive(drive);
    private final DefaultDriveCommand driveCommand;

    private final ADIS16448_IMU imu = new ADIS16448_IMU();

    private double speedMultiplier = 1.0;
 
    /**
     * This constructor of the SK20Drive sets up the BaseDrive object and passes it
     * into the SmoothDrive object to set it up.
     */
    public SK20Drive(FilteredJoystick joystickDriver) {
        driveCommand = new DefaultDriveCommand(this, joystickDriver);
        setDefaultCommand(driveCommand);
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
        smoothDrive.SmoothDrivePeriodic();
    }

}
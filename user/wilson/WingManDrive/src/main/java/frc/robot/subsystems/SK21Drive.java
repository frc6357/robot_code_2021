// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.utils.MotorEncoder;

/**
 * The SK21Drive class is the subsystem that controls the drive train of the Robot.
 */
public class SK21Drive extends SubsystemBase
{
    /**
     * Motor controller for the leading left motor.
     */
    public final WPI_TalonFX leftLeader = new WPI_TalonFX(Ports.frontLeftDrive);

    /**
     * Motor controller for the following left motor.
     */
    public final WPI_TalonFX leftFollower = new WPI_TalonFX(Ports.backLeftDrive);

    /**
     * Encoder attached to the leading left motor.
     */
    public final MotorEncoder leftMotorEncoder = new MotorEncoder(leftLeader, 
                                                        Constants.DriveConstants.kEncoderDistancePerPulse,
                                                        Constants.DriveConstants.kLeftEncoderReversed);

    /**
     * Motor controller for the leading right motor.
     */
    public final WPI_TalonFX rightLeader = new WPI_TalonFX(Ports.frontRightDrive);

    /**
     * Motor controller for the following right motor.
     */
    public final WPI_TalonFX rightFollower = new WPI_TalonFX(Ports.backRightDrive);

    /**
     * Encoder attached to the leading right motor.
     */
    public final MotorEncoder rightMotorEncoder = new MotorEncoder(rightLeader, 
                                                        Constants.DriveConstants.kEncoderDistancePerPulse,
                                                        Constants.DriveConstants.kRightEncoderReversed);

    /**
     * SpeedControllerGroup for the left motors.
     */
    public final SpeedControllerGroup leftGroup =
        new SpeedControllerGroup(leftLeader, leftFollower);

    /**
     * SpeedControllerGroup for the right motors.
     */
    public final SpeedControllerGroup rightGroup =
        new SpeedControllerGroup(rightLeader, rightFollower);

    // The robot's drive
    private final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
  
    /** Creates a new SK21Drive. */
    public SK21Drive()
    {
        resetEncoders();
    }

    @Override
    public void periodic()
    {
        double leftEncoderDistanceMeters = leftMotorEncoder.getPositionMeters();
        double rightEncoderDistanceMeters = rightMotorEncoder.getPositionMeters();
        double leftEncoderSpeedMeters = leftMotorEncoder.getVelocityMeters();
        double rightEncoderSpeedMeters = rightMotorEncoder.getVelocityMeters();

        SmartDashboard.putNumber("Left Wheel Distance", leftEncoderDistanceMeters);
        SmartDashboard.putNumber("Right Wheel Distance", rightEncoderDistanceMeters);
        SmartDashboard.putNumber("Left Wheel Speed", leftEncoderSpeedMeters);
        SmartDashboard.putNumber("Right Wheel Speed", rightEncoderSpeedMeters);
     }

      /**
     * Returns the current wheel speeds of the robot.
     *
     * @return The current wheel speeds.
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds()
    {
        return new DifferentialDriveWheelSpeeds(
            leftMotorEncoder.getVelocityMeters(), 
            rightMotorEncoder.getVelocityMeters());
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose)
    {
        resetEncoders();
    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation
     */
    public void arcadeDrive(double fwd, double rot)
    {
        drive.arcadeDrive(fwd, rot);
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     * @param leftVolts the commanded left output
     * @param rightVolts the commanded right output
     */
    public void tankDriveVolts(double leftVolts, double rightVolts)
    {
        leftGroup.setVoltage(leftVolts);
        rightGroup.setVoltage(-rightVolts);
        drive.feed();
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders()
    {
        leftMotorEncoder.resetEncoder();
        rightMotorEncoder.resetEncoder();
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance()
    {
      // return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
      return (leftMotorEncoder.getPositionMeters() + rightMotorEncoder.getPositionMeters()) / 2.0;
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput)
    {
        drive.setMaxOutput(maxOutput);
    }
}

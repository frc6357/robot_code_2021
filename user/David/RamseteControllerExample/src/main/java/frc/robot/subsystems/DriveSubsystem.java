// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Ports;
import frc.robot.utils.MotorEncoder;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class DriveSubsystem extends SubsystemBase {
  private final WPI_TalonFX m_leftLeader = new WPI_TalonFX(Ports.frontLeftDrive);
  private final WPI_TalonFX m_leftFollower = new WPI_TalonFX(Ports.backLeftDrive);
  private final MotorEncoder m_leftMotorEncoder = new MotorEncoder(m_leftLeader, 
                                                        Constants.DriveConstants.kEncoderDistancePerPulse,
                                                        Constants.DriveConstants.kLeftEncoderReversed);

  private final WPI_TalonFX m_rightLeader = new WPI_TalonFX(Ports.frontRightDrive);
  private final WPI_TalonFX m_rightFollower = new WPI_TalonFX(Ports.backRightDrive);
  private final MotorEncoder m_rightMotorEncoder = new MotorEncoder(m_rightLeader, 
                                                        Constants.DriveConstants.kEncoderDistancePerPulse,
                                                        Constants.DriveConstants.kRightEncoderReversed);

  private final SpeedControllerGroup m_leftGroup =
      new SpeedControllerGroup(m_leftLeader, m_leftFollower);
  private final SpeedControllerGroup m_rightGroup =
      new SpeedControllerGroup(m_rightLeader, m_rightFollower);

  private final ADIS16448_IMU m_gyro = new ADIS16448_IMU();

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftGroup, m_rightGroup);

  // The left-side drive encoder
  // private final Encoder m_leftEncoder =
      // new Encoder(
          // DriveConstants.kLeftEncoderPorts[0],
  //         DriveConstants.kLeftEncoderPorts[1],
  //         DriveConstants.kLeftEncoderReversed);

  // // The right-side drive encoder
  // private final Encoder m_rightEncoder =
  //     new Encoder(
  //         DriveConstants.kRightEncoderPorts[0],
  //         DriveConstants.kRightEncoderPorts[1],
  //         DriveConstants.kRightEncoderReversed);

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // Sets the distance per pulse for the encoders
    // m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
    // m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(), 
        // TODO: TEST TO ENSURE IT DOESN'T BREAK ROBOT
        m_leftMotorEncoder.getPositionMeters(),
        m_rightMotorEncoder.getPositionMeters());

    System.out.println("Left Distance Meters:"+m_leftMotorEncoder.getPositionMeters()+" Right Distance Meters %f"+m_rightMotorEncoder.getPositionMeters());
    System.out.println("Left Speed Meters:"+m_leftMotorEncoder.getVelocityMeters()+" Right Speed Meters %f"+m_rightMotorEncoder.getVelocityMeters());

  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    // TODO: TEST TO ENSURE IT DOESN'T BREAK ROBOT
    return new DifferentialDriveWheelSpeeds(
      m_leftMotorEncoder.getVelocityMeters(), 
      m_rightMotorEncoder.getVelocityMeters());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
    // TODO: Remove this line after testing
    // TODO: TEST TO ENSURE IT DOESN'T BREAK ROBOT
    System.out.println("Turn: " + rot + " Throttle: " + fwd);
    System.out.println("Left Encoder Distance: " + m_leftMotorEncoder.getPositionMeters() + " Right Encoder Distance: " + m_rightMotorEncoder.getPositionMeters());
    System.out.println("Left Encoder Speed: " + m_leftMotorEncoder.getVelocityMeters() + " Right Encoder Speed: " + m_rightMotorEncoder.getVelocityMeters());
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftGroup.setVoltage(leftVolts);
    m_rightGroup.setVoltage(-rightVolts);
    m_drive.feed();
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftMotorEncoder.resetEncoder();
    m_rightMotorEncoder.resetEncoder();
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  //TODO: Check if this is looking for meters!
  public double getAverageEncoderDistance() {
    // return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
    return (m_leftMotorEncoder.getPositionMeters() + m_rightMotorEncoder.getPositionMeters()) / 2.0;

  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }
}

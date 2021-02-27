// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
// import edu.wpi.first.wpilibj.AnalogGyro;
// import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
// import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import com.analog.adis16448.frc.ADIS16448_IMU;

/** Represents a differential drive style drivetrain. */
public class Drivetrain {
  public static final double  kMaxSpeed          = 2.0;         // meters per second
  // TODO: This needs to get tuned to make sure that it we don't have it turning too fast
  public static final double  kMaxAngularSpeed   = 0.75 * Math.PI; // one rotation per second


  private static final double kTrackWidth        = 0.381 * 2;   // meters
  private static final double kWheelRadius       = 0.0508;      // meters
  private static final int    kEncoderResolution = 2048;

  public static final double kDistancePerEncoderPulse = 2 * Math.PI * kWheelRadius / kEncoderResolution;


  private final WPI_TalonFX m_leftLeader = new WPI_TalonFX(Ports.frontLeftDrive);
  private final WPI_TalonFX m_leftFollower = new WPI_TalonFX(Ports.backLeftDrive);
  private final WPI_TalonFX m_rightLeader = new WPI_TalonFX(Ports.frontRightDrive);
  private final WPI_TalonFX m_rightFollower = new WPI_TalonFX(Ports.backRightDrive);

  // private final Encoder m_leftEncoder = new Encoder(Ports.leftEncoderA, Ports.leftEncoderB);
  // private final Encoder m_rightEncoder = new Encoder(Ports.rightEncoderA, Ports.rightEncoderB);

  private final SpeedControllerGroup m_leftGroup =
      new SpeedControllerGroup(m_leftLeader, m_leftFollower);
  private final SpeedControllerGroup m_rightGroup =
      new SpeedControllerGroup(m_rightLeader, m_rightFollower);

  // private final AnalogGyro m_gyro = new AnalogGyro(0);
  private final ADIS16448_IMU m_gyro = new ADIS16448_IMU();

  // TODO: Tune this PID using the values gathered in the frc-characterization tool - Also tune I Values
  private final PIDController m_leftPIDController = new PIDController(0.00000485, 0, 0);
  private final PIDController m_rightPIDController = new PIDController(0.00000485, 0, 0);

  private final DifferentialDriveKinematics m_kinematics =
      new DifferentialDriveKinematics(kTrackWidth);

  private final DifferentialDriveOdometry m_odometry;

  // Gains are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 3);

  /**
   * Constructs a differential drive object. Sets the encoder distance per pulse and resets the
   * gyro.
   */
  public Drivetrain() {
    m_gyro.reset();

    // Set the distance per pulse for the drive encoders. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
    // m_leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
    // m_rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);

    // m_leftEncoder.reset();
    // m_rightEncoder.reset();

    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
    m_rightGroup.setInverted(true);
  }

  /**
   * Sets the desired wheel speeds.
   *
   * @param speeds The desired wheel speeds.
   */
  public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
    final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
    final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

    final double leftOutput =
        m_leftPIDController.calculate(m_leftLeader.getSelectedSensorVelocity(), speeds.leftMetersPerSecond);
    final double rightOutput =
        m_rightPIDController.calculate(m_rightLeader.getSelectedSensorVelocity(), speeds.rightMetersPerSecond);
    m_leftGroup.setVoltage(leftOutput + leftFeedforward);
    m_rightGroup.setVoltage(rightOutput + rightFeedforward);
  }

  /**
   * Drives the robot with the given linear velocity and angular velocity.
   *
   * @param xSpeed Linear velocity in m/s.
   * @param rot Angular velocity in rad/s.
   */
  @SuppressWarnings("ParameterName")
  public void drive(double xSpeed, double rot) {
    var wheelSpeeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, 0.0, rot));
    setSpeeds(wheelSpeeds);
  }

  /** Updates the field-relative position. */
  public void updateOdometry() {
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftLeader.getSelectedSensorPosition() * kDistancePerEncoderPulse, m_rightLeader.getSelectedSensorPosition() * kDistancePerEncoderPulse);
  }

  /**
   * Resets the field-relative position to a specific location.
   *
   * @param pose The position to reset to.
   */
  public void resetOdometry(Pose2d pose) {
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Returns the pose of the robot.
   *
   * @return The pose of the robot.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }
}

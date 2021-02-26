// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.filters.FilterDeadband;
import java.util.List;
import java.io.IOException;
import java.nio.file.*;

public class Robot extends TimedRobot {
  private final FilteredJoystick m_controller = new FilteredJoystick(0);
  private final Drivetrain m_drive = new Drivetrain();
  private final FilterDeadband m_deadbandThrottle = new FilterDeadband(0.05, -1.0);
  private final FilterDeadband m_deadbandTurn     = new FilterDeadband(0.05, -1.0);

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);
  private final String trajectoryJSON = "paths/StartupPath1.wpilib.json";

  // An example trajectory to follow during the autonomous period.
  private Trajectory m_trajectory;

  // The Ramsete Controller to follow the trajectory.
  private RamseteController m_ramseteController;

  // The timer to use during the autonomous period.
  private Timer m_timer;

  @Override
  public void robotInit() {
    // Create the trajectory to follow in autonomous. It is best to initialize
    // trajectories here to avoid wasting time in autonomous.
    m_controller.setFilter(Ports.OIDriverLeftDrive, m_deadbandTurn);
    m_controller.setFilter(Ports.OIDriverRightDrive, m_deadbandThrottle);
    try 
    { 
      Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
      m_trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    }
    catch(IOException ex)
    {
    }
  }

  @Override
  public void autonomousInit() {
    // Initialize the timer.
    m_timer = new Timer();
    m_timer.start();

    // Reset the drivetrain's odometry to the starting pose of the trajectory.
    m_drive.resetOdometry(m_trajectory.getInitialPose());
  }

  @Override
  public void autonomousPeriodic() {
    // Update odometry.
    m_drive.updateOdometry();

    if (m_timer.get() < m_trajectory.getTotalTimeSeconds()) {
      // Get the desired pose from the trajectory.
      var desiredPose = m_trajectory.sample(m_timer.get());

      // Get the reference chassis speeds from the Ramsete controller.
      var refChassisSpeeds = m_ramseteController.calculate(m_drive.getPose(), desiredPose);

      // Set the linear and angular speeds.
      m_drive.drive(refChassisSpeeds.vxMetersPerSecond, refChassisSpeeds.omegaRadiansPerSecond);
    } else {
      m_drive.drive(0, 0);
    }
  }

  @Override
  public void teleopPeriodic() {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed =
        m_speedLimiter.calculate(m_controller.getFilteredAxis(Ports.OIDriverRightDrive)) * Drivetrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot =
        m_rotLimiter.calculate(m_controller.getFilteredAxis(Ports.OIDriverLeftDrive))
            * Drivetrain.kMaxAngularSpeed;
    System.out.println("Left Drive: " + m_controller.getFilteredAxis(Ports.OIDriverLeftDrive) + " Right Drive:" + m_controller.getFilteredAxis(Ports.OIDriverRightDrive));

    m_drive.drive(xSpeed, rot);
  }
}

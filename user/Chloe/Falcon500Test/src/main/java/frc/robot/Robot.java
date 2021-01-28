// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically it contains the code
 * necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private SpeedController m_leftMotorController, m_rightMotorController;

  @Override
  public void robotInit() {
    m_leftMotorController = new SpeedControllerGroup(new WPI_TalonFX(10), new WPI_TalonFX(12));
    m_rightMotorController = new SpeedControllerGroup(new WPI_TalonFX(11), new WPI_TalonFX(13));
    m_myRobot = new DifferentialDrive(m_leftMotorController, m_rightMotorController);
    m_leftStick = new Joystick(0);
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(m_leftStick.getRawAxis(1), m_leftStick.getRawAxis(5));
  }
}

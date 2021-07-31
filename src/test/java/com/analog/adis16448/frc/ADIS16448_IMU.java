/*----------------------------------------------------------------------------*/
/* Copyright (c) 2016-2020 Analog Devices Inc. All Rights Reserved.           */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*                                                                            */
/* Modified by Juan Chong - frcsupport@analog.com                             */
/*----------------------------------------------------------------------------*/

// This is a stubbed-out version of the ASI16448 class used during unit testing 
// of classes which make use of this hardware. DO NOT USE IT except when running
// unit tests!

package com.analog.adis16448.frc;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.SPI;

public class ADIS16448_IMU extends GyroBase implements Gyro {
  public enum IMUAxis { kX, kY, kZ }

  /* Instant raw output variables */
  private double m_gyro_x = 0.0;
  private double m_gyro_y = 0.0;
  private double m_gyro_z = 0.0;
  private double m_accel_x = 0.0;
  private double m_accel_y = 0.0;
  private double m_accel_z = 0.0;
  private double m_mag_x = 0.0;
  private double m_mag_y = 0.0;
  private double m_mag_z = 0.0;
  private double m_baro = 0.0;
  private double m_temp = 0.0;

  /* IMU gyro offset variables */
  private double m_gyro_offset_x = 0.0;
  private double m_gyro_offset_y = 0.0;
  private double m_gyro_offset_z = 0.0;
  private int m_avg_size = 0;
  private int m_accum_count = 0;

  /* Integrated gyro angle variables */
  private double m_integ_gyro_x = 0.0;
  private double m_integ_gyro_y = 0.0;
  private double m_integ_gyro_z = 0.0;

  /* Complementary filter variables */
  private double m_dt = 0.0;
  private double m_alpha = 0.0;
  private double m_tau = 1.0;
  private double m_compAngleX = 0.0;
  private double m_compAngleY = 0.0;
  private double m_accelAngleX = 0.0;
  private double m_accelAngleY = 0.0;
  
   /* User-specified yaw axis */
   private IMUAxis m_yaw_axis;

  public ADIS16448_IMU() {
    System.out.println("Stub ADIS16448_IMU in use.");
  }

  /**
   *
   */
  public ADIS16448_IMU(final IMUAxis yaw_axis, SPI.Port port, int cal_time) {
    System.out.println("Stub ADIS16448_IMU in use.");
  }

  
  /**
   * {@inheritDoc}
   */
  @Override
  public void calibrate() {
  }

  public int setYawAxis(IMUAxis yaw_axis) {
    if(m_yaw_axis == yaw_axis) {
      return 1;
    }
    m_yaw_axis = yaw_axis;
    reset();
    return 0;
  }

  
  /**
   * {@inheritDoc}
   */
  public void reset() {
  }

  /**
   * Delete (free) the spi port used for the IMU.
   */
  @Override
  public void close() {
  }

/**
 * 
 */
public double getAngle() {
    return 0.0;
}

/**
 * 
 */
public double getRate() {
    return 0.0;
}

/**
   * 
   * @return
   */
  public IMUAxis getYawAxis() {
    return m_yaw_axis;
  }

  /**
   * 
   * @return
   */
  public double getGyroAngleX() {
    return m_integ_gyro_x;
  }

  /**
   * 
   * @return
   */
  public double getGyroAngleY() {
    return m_integ_gyro_y;
  }

  /**
   * 
   * @return
   */
  public double getGyroAngleZ() {
    return m_integ_gyro_z;
  }

/**
   * 
   * @return
   */
  public double getGyroInstantX() {
    return m_gyro_x;
  }

  /**
   * 
   * @return
   */
  public double getGyroInstantY() {
    return m_gyro_y;
  }

  /**
   * 
   * @return
   */
  public double getGyroInstantZ() {
    return m_gyro_z;
  }

  /**
   * 
   * @return
   */
  public double getAccelInstantX() {
    return m_accel_x;
  }

  /**
   * 
   * @return
   */
  public double getAccelInstantY() {
    return m_accel_y;
  }

  /**
   * 
   * @return
   */
  public double getAccelInstantZ() {
    return m_accel_z;
  }

  /**
   * 
   * @return
   */
  public double getMagInstantX() {
    return m_mag_x;
  }

  /**
   * 
   * @return
   */
  public double getMagInstantY() {
    return m_mag_y;
  }

  /**
   * 
   * @return
   */
  public double getMagInstantZ() {
    return m_mag_z;
  }

  /**
   * 
   * @return
   */
  public double getXComplementaryAngle() {
    return m_compAngleX;
  }

  /**
   * 
   * @return
   */
  public double getYComplementaryAngle() {
    return m_compAngleY;
  }

  /**
   * 
   * @return
   */
  public double getXFilteredAccelAngle() {
    return m_accelAngleX;
  }

  /**
   * 
   * @return
   */
  public double getYFilteredAccelAngle() {
    return m_accelAngleY;
  }

  /**
   * 
   * @return
   */
  public double getBarometricPressure() {
    return m_baro;
  }

  /**
   * 
   * @return
   */
  public double getTemperature() {
    return m_temp;
  }
}

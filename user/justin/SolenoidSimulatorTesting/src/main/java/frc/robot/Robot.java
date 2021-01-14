/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase;



public class Robot extends TimedRobot {
  //private final Joystick m_stick = new Joystick(0);

  // Solenoid corresponds to a single solenoid.
  //private final Solenoid m_solenoid = new Solenoid(0);

  // DoubleSolenoid corresponds to a double solenoid.
  private final DoubleSolenoid m_doubleSolenoid = new DoubleSolenoid(1, 2);

  boolean toggleOn = false;
  boolean togglePressed = false;

  //private static final int kSolenoidButton = 1;
  private static final int kDoubleSolenoidForward = 2;
  private static final int kDoubleSolenoidReverse = 3;
  Joystick joystick;

  private final Encoder m_encoder = new Encoder(1, 2, false, CounterBase.EncodingType.k4X);


  
  @Override
  public void robotInit() {

    //m_encoder.setSamplesToAverage(5);
    //m_encoder.setDistancePerPulse(1.0 / 360.0 * 2.0 * Math.PI * 1.5);
    //m_encoder.setMinRate(1.0);
    joystick = new Joystick(0);


  }

  

  @Override
  public void teleopPeriodic() {
    
    //m_solenoid.set(m_stick.getRawButton(kSolenoidButton));

    

    /*
    if (m_stick.getRawButton(kDoubleSolenoidForward)) {
      m_doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    } else if (m_stick.getRawButton(kDoubleSolenoidReverse)) {
      m_doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    */


    //updateToggle();

    if(toggleOn){
      m_doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }else{
      m_doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
  }


  public void updateToggle()
    {
        if(joystick.getRawButton(3)){
            if(!togglePressed){
                toggleOn = !toggleOn;
                togglePressed = true;
            }
        }else{
            togglePressed = false;
        }
    }
}

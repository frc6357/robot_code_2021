package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

public class DpadUpButton extends Button {
  private final Dpad m_Dpad;
 
  public DpadUpButton(Dpad dpad) {
    m_Dpad = dpad; 
  }
  @Override
  public boolean get() {
    return m_Dpad.isUpPressed();
  }


    

}

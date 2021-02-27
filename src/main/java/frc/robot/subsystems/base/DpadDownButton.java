package frc.robot.subsystems.base;
import edu.wpi.first.wpilibj2.command.button.Button;

public class DpadDownButton extends Button {
  private final Dpad m_Dpad;
  public DpadDownButton(Dpad dpad) {
    m_Dpad = dpad; 
  }

  @Override
  public boolean get() {
    return m_Dpad.isDownPressed();
  }

}

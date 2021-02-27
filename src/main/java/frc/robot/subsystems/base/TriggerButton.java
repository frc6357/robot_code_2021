package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.Ports;
import frc.robot.TuningParams;

public class TriggerButton extends Button {

  private final Joystick joystick;
  private final int axis;

  public TriggerButton(Joystick joystick, int axis) {
    this.joystick = joystick;
    this.axis = axis;

  }

  @Override
  public boolean get() {
    double triggerValue = joystick.getRawAxis(axis);
    return triggerValue >= TuningParams.TRIGGER_THRESHOLD;
  }

  
}

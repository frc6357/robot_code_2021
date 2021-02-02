package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Ports;

public class Dpad {
    private final Joystick operatorJoystick;

    public Dpad(Joystick operatorJoystick) {
        this.operatorJoystick = operatorJoystick;
    }

    public boolean isUpPressed() {
        int direction = operatorJoystick.getPOV(Ports.OIOperatorDpad);
        return (direction == 0) || (direction == 315) || (direction == 45);
    }

    public boolean isDownPressed() {
        int direction = operatorJoystick.getPOV(Ports.OIOperatorDpad);
        return (direction == 180) || (direction == 225) || (direction == 135);
    }

}

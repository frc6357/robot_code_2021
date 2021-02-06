package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Joystick;

public class Dpad {
    private final Joystick operatorJoystick;

    public Dpad(Joystick operatorJoystick) {
        this.operatorJoystick = operatorJoystick;
    }

    public boolean isUpPressed() {
        int direction = operatorJoystick.getPOV();
        return (direction == 0) || (direction == 315) || (direction == 45);
    }

    public boolean isDownPressed() {
        int direction = operatorJoystick.getPOV();
        return (direction == 180) || (direction == 225) || (direction == 135);
    }

}

package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Creates a class that converts Dpad behaviors to binary up/down behaviors. Note we
 * consciously choose a "sloppy" definition of up/down to account for stress while
 * driving.
 */
public class Dpad
{
    /**
     * The Joystick on which this Dpad is present.
     */
    private final Joystick joystick;

    /**
     * The DPad Number on the Joystick.
     */
    private final int dpadNumber;

    /**
     * Constructs a new Dpad for the given operatorJoystick.
     * 
     * @param joystick
     *            The Joystick the Dpad is on
     * @param dpadNumber
     *            The dpad number on the given Joystick to be used
     */
    public Dpad(Joystick joystick, int dpadNumber)
    {
        this.joystick = joystick;
        this.dpadNumber = dpadNumber;
    }

    /**
     * Returns true if we want to convert the current state of the Dpad to an "Up"
     * behavior.
     * 
     * @return true if we want to convert the current state of the Dpad to an "Up"
     *         behavior; false otherwise
     */
    public boolean isUpPressed()
    {
        int direction = joystick.getPOV(dpadNumber);
        return (direction == 0) || (direction == 315) || (direction == 45);
    }

    /**
     * Returns true if we want to convert the current state of the Dpad to a "Down"
     * behavior.
     * 
     * @return true if we want to convert the current state of the Dpad to a "Down"
     *         behavior; false otherwise
     */
    public boolean isDownPressed()
    {
        int direction = joystick.getPOV(dpadNumber);
        return (direction == 180) || (direction == 225) || (direction == 135);
    }
}

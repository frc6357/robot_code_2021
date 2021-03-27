package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Wraps the Dpad to behave like a button (for the "Down" behavior).
 */
public class DpadDownButton extends Button
{
    /**
     * The underlying Dpad for this DpanDownButton.
     */
    private final Dpad dpad;

    /**
     * Constructs a new DpadUpButton to have the Dpad emulate a button when pressed
     * downward.
     * 
     * @param dpad
     *            The Dpad on which we are emulating a down button
     */
    public DpadDownButton(Dpad dpad)
    {
        this.dpad = dpad;
    }

    @Override
    public boolean get()
    {
        return dpad.isDownPressed();
    }
}

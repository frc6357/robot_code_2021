package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Wraps the Dpad to behave like a button (for the "Up" behavior).
 */
public class DpadUpButton extends Button
{
    /**
     * The underlying Dpad for this DpadUpButton.
     */
    private final Dpad dpad;

    /**
     * Constructs a new DpadUpButton to have the Dpad emulate a button when pressed
     * upward.
     * 
     * @param dpad
     *            The Dpad on which we are emulating an up button
     */
    public DpadUpButton(Dpad dpad)
    {
        this.dpad = dpad;
    }

    @Override
    public boolean get()
    {
        return dpad.isUpPressed();
    }
}

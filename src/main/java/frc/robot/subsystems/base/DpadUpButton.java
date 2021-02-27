package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Wraps the Dpad to behave like a button (for the "Up" behavior)
 */
public class DpadUpButton extends Button
{

    /**
     * The underlying Dpad for this DpadUpButton.
     */
    private final Dpad m_Dpad;

    public DpadUpButton(Dpad dpad)
    {
        m_Dpad = dpad;
    }

    @Override
    public boolean get()
    {
        return m_Dpad.isUpPressed();
    }
}

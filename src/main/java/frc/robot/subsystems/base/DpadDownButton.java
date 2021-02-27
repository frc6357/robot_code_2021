package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Wraps the Dpad to behave like a button (for the "Down" behavior)
 */
public class DpadDownButton extends Button
{

    /**
     * The underlying Dpad for this DpanDownButton.
     */
    private final Dpad m_Dpad;

    public DpadDownButton(Dpad dpad)
    {
        m_Dpad = dpad;
    }

    @Override
    public boolean get()
    {
        return m_Dpad.isDownPressed();
    }

}

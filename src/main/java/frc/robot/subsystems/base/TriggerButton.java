package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.TuningParams;

/**
 * Creates a Button for a Trigger (which normally doesn't behave like a button). This will
 * trigger as a Button when the trigger is pulled beyond a specific threshold.
 */
public class TriggerButton extends Button
{

    /**
     * The Joystick on which the Trigger exists.
     */
    private final Joystick joystick;

    /**
     * The axis for the Trigger to be converted to a Button.
     */
    private final int axis;

    /**
     * Constructs a new TriggerButton for the given Joystick and axis.
     * 
     * @param joystick
     *            The Joystick on which the Trigger exists
     * @param axis
     *            The axis on the given Joystick to be converted to a Button
     */
    public TriggerButton(Joystick joystick, int axis)
    {
        this.joystick = joystick;
        this.axis = axis;
    }

    @Override
    public boolean get()
    {
        double axisValue = joystick.getRawAxis(axis);
        return axisValue >= TuningParams.TRIGGER_THRESHOLD;
    }
}

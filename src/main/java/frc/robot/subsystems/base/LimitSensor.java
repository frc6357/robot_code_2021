package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Class for any sensor that has a limit or only a true or false (i.e. 0 or 1), state.
 */
public class LimitSensor extends DigitalInput
{
    /**
     * Indicates if the value of the LimitSensor should be inverted before it is returned.
     */
    private final boolean inverted;

    /**
     * This creates a limit sensor that is based off of DigitalInput and sets the channel
     * that requires, and then checks if the sensor needs to be inverted.
     * 
     * @param channel
     *            The DIO channel that the Limit Sensor is using
     * @param inverted
     *            If the limit sensor is returning false when the value should be true,
     *            set inverted to be true
     */
    public LimitSensor(int channel, boolean inverted)
    {
        super(channel);
        this.inverted = inverted;
    }

    @Override
    /**
     * This retuns the value of the limit sensor taking into count the inverted boolean.
     * 
     * @return The value of the limit sensor
     */
    public boolean get()
    {
        boolean returnVal = super.get();
        return inverted ? !returnVal : returnVal;
    }
}

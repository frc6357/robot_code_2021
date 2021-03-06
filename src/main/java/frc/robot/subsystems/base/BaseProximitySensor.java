package frc.robot.subsystems.base;

/**
 * Base class for any proximity sensor.
 */
public class BaseProximitySensor extends LimitSensor
{
    /**
     * Constructs a new BaseProximitySensor using the given DIO port.
     *
     * @param port
     *            DIO port for proximity sensor
     */
    public BaseProximitySensor(int port)
    {
        super(port, false);
    }

    /**
     * Constructs a new BaseProximitySensor using the given DIO port and triggeredState.
     *
     * @param port
     *            DIO port for proximity sensor
     * @param inverted
     *            Indicates the value of the sensor should be inverted when returned
     */
    public BaseProximitySensor(int port, boolean inverted)
    {
        super(port, inverted);
    }
}
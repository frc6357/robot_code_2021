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
     *
     * @param triggeredState
     *            Set when the sensor is triggered
     */
    public BaseProximitySensor(int port, boolean triggeredState)
    {
        super(port, triggeredState);
    }
}
package frc.robot.subsystems.base;

/**
 * Base class for any proximity sensor
 */
public class BaseProximitySensor extends LimitSensor
{
    /**
     * Constructor
     *
     * @param port
     *      - Type: int
     *      - DIO port for proximity sensor
     */
    public BaseProximitySensor(int port)
    {
        super(port, false);
    }

    /**
     * Constructor
     *
     * @param port
     *      - Type: int
     *      - DIO port for proximity sensor
     *
     * @param triggeredState
     *      - Type: boolean
     *      - Set when the sensor is triggered
     */
    public BaseProximitySensor(int port, boolean triggeredState)
    {
        super(port, triggeredState);
    }

    /**
     * Gets if sensor is triggered
     */
    @Override
    public boolean get()
    {
        return super.get();
    }
}
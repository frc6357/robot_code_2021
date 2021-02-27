package frc.robot.subsystems.base;

/**
 * Base class for the Grove Infrared Reflective Sensor v1.2
 */
public class BaseGroveIRProximitySensor extends BaseProximitySensor
{
    /**
     * Constructs a new BaseGroveIRProximitySensor using the given port.
     *
     * @param port
     *            Port of the Grove IR sensor
     */
    public BaseGroveIRProximitySensor(int port)
    {
        super(port, true);
    }
}
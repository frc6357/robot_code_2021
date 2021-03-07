package frc.robot.subsystems.base;

/**
 * This class is used to create a BaseLimitSensor object and controll the state to always
 * be the boolean that is passed in.
 */
public class DummyLimitSensor extends LimitSensor
{

    /**
     * The value the DummyLimitSensor will return from the get() method.
     */
    private final boolean constState;

    /**
     * Creates a new DummyLimitSensor that will always return a specific value.
     *
     * @param constState
     *            The value the DummyLimitSensor will return from the get() method
     */
    public DummyLimitSensor(boolean constState)
    {
        super(30, false);
        this.constState = constState;
    }

    /**
     * @return Returns the state set in the constructor of the DummyLimitSensor.
     */
    @Override
    public boolean get()
    {
        return constState;
    }
}

package frc.robot.subsystems.base;

/**
 * This class is used to create a BaseLimitSensor object and controll the
 * state to always be the boolean that is passed in.
 */
public class DummyLimitSensor extends LimitSensor
{

    private final boolean constState;

    /**
     * Constructor:
     *
     * @param constState
     *      - Type: boolean
     *      - State for getIsTriggered to always return
     */
    public DummyLimitSensor(boolean constState)
    {
        super (30, false);
        this.constState = constState;
    }

    /**
     * @return
     *      - Type: boolean
     *      - Returns the state set in the constructor
     */
    @Override
    public boolean get()
    {
        return constState;
    }
}
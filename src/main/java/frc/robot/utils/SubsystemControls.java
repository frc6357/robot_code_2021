package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the subsystem control values as imported from the subsystem control
 * JSON file.
 */
public class SubsystemControls
{

    private final boolean intake;
    private final boolean launcher;
    private final boolean indexer;
    private final boolean colorwheel;
    private final boolean climb;

    /**
     * Constructs a new SubsystemControls object with the given subsystem presence.
     * 
     * @param intake
     *            indicates if the intake system is present and should be enabled
     * @param launcher
     *            indicates if the launcher system is present and should be enabled
     * @param indexer
     *            indictes if the indexer system is present and should be enabled
     * @param colorwheel
     *            indicates if the color wheel system is present and should be enabled
     * @param climb
     *            indicates if the climb system is present and should be enabled
     */
    public SubsystemControls(@JsonProperty(required = true, value = "intake")
    boolean intake, @JsonProperty(required = true, value = "launcher")
    boolean launcher, @JsonProperty(required = true, value = "indexer")
    boolean indexer, @JsonProperty(required = true, value = "colorwheel")
    boolean colorwheel,  @JsonProperty(required = true, value = "climb")
    boolean climb)
    {
        this.intake = intake;
        this.launcher = launcher;
        this.indexer = indexer;
        this.colorwheel = colorwheel;
        this.climb = climb;
    }

    /**
     * Returns true if the intake system is indicated as present and should be enabled.
     * 
     * @return true if the intake system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isIntakePresent()
    {
        return intake;
    }

    /**
     * Returns true if the launcher system is indicated as present and should be enabled.
     * 
     * @return true if the launcher system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isLauncherPresent()
    {
        return launcher;
    }

    /**
     * Returns true if the indexer system is indicated as present and should be enabled.
     * 
     * @return true if the indexer system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isIndexerPresent()
    {
        return indexer;
    }

    /**
     * Returns true if the colorwheel system is indicated as present and should be enabled.
     * 
     * @return true if the colorwheel system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isColorwheelPresent()
    {
        return colorwheel;
    }

    /**
     * Returns true if the climb system is indicated as present and should be enabled.
     * 
     * @return true if the climb system is indicated as present and should be enabled;
     *         false otherwise
     */
        public boolean isClimbPresent()
    {
        return climb;
    }
}

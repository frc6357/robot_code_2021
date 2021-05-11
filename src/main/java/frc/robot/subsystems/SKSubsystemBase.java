package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * SKSubsystemBase is an extension of SubsystemBase used by The Spring Konstant. This is a
 * template that ensures we define the appropriate test mode hooks for our Subsystems.
 */
public abstract class SKSubsystemBase extends SubsystemBase
{
    /**
     * Constructs a new SKSubsystemBase (and registers it with the TestModeManager).
     */
    public SKSubsystemBase()
    {
        TestModeManager.registerSubsystem(this);
    }

    /**
     * Method used to initialize Test Mode behaviors for a Subsystem. This method will be
     * called once per robot instantiation, on the first entry into test mode. This will
     * be called before enterTestMode().
     * 
     * Initial setup of the Shuffleboard objects should be put here.
     */
    public abstract void initializeTestMode();

    /**
     * Method called periodically (effectively from testModePeriodic) for a Subsystem.
     * 
     * Connections from Shuffleboard to the motors and other devices should be put here.
     */
    public abstract void testModePeriodic();

    /**
     * Method used to set up any items for Test Mode for a Subsystem. This method will be
     * called every time upon entry into test mode.
     * 
     * Items to set physical object defaults (e.g. set all motors to zero speed) should be
     * put here.
     */
    public abstract void enterTestMode();
}

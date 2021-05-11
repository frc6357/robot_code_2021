package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for The Spring Konstant's Test Mode Control System.
 * 
 * Manages registration of Subsystems and delegates initialize and periodic methods to all
 * registered subsystems.
 */
public final class TestModeManager
{

    private TestModeManager()
    {
        //Do not instantiate Utility Class
    }

    private static List<SKSubsystemBase> subsystemList = new ArrayList<>();
    private static boolean testModeIntialized = false;

    /**
     * Register an SKSubsystemBase with the TestModeManager.
     * 
     * @param skSubsystemBase
     *            The SKSubsystemBase to be registered
     */
    public static void registerSubsystem(SKSubsystemBase skSubsystemBase)
    {
        subsystemList.add(skSubsystemBase);
    }

    /**
     * Initialize the registered SKSubsystemBase objects to run in Test Mode.
     * 
     * In general, this should be called from Robot.java testInit().
     */
    public static void initializeTestMode()
    {
        if (!testModeIntialized)
        {
            for (SKSubsystemBase skSubsystemBase : subsystemList)
            {
                skSubsystemBase.initializeTestMode();
            }
            testModeIntialized = true;
        }
        for (SKSubsystemBase skSubsystemBase : subsystemList)
        {
            skSubsystemBase.enterTestMode();
        }
    }

    /**
     * Call the periodic method on all registered SKSubsystemBase objects.
     * 
     * In general, this should be called from Robot.java testPeriodic().
     */
    public static void testModePeriodic()
    {
        for (SKSubsystemBase skSubsystemBase : subsystemList)
        {
            skSubsystemBase.testModePeriodic();
        }
    }
}

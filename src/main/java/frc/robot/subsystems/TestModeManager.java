package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

public final class TestModeManager
{

  private static List<SKSubsystemBase> subsystemList = new ArrayList<>();
  private static boolean testModeIntialized = false;

  public static void registerSubsystem(SKSubsystemBase skSubsystemBase)
  {
    subsystemList.add(skSubsystemBase);
  }

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

  public static void testModePeriodic()
  {
    for (SKSubsystemBase skSubsystemBase : subsystemList)
    {
      skSubsystemBase.testModePeriodic();
    }
  }
}

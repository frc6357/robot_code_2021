package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

public final class TestModeManager {

  private static List<SKSubsystemBase> subsystemList = new ArrayList<>();
  static boolean testModeIntialized = false;
  

  public static void registerSubsystem(SKSubsystemBase skSubsystemBase)
  {
    subsystemList.add(skSubsystemBase);
  }

  public static void initializeTestMode(){
    if (testModeIntialized == false)
    {
      for (SKSubsystemBase skSubsystemBase : subsystemList) 
      {
        skSubsystemBase.initializeTestMode();
        skSubsystemBase.enterTestMode();
        testModeIntialized = true;
      }
    }
    else
    {
      for (SKSubsystemBase skSubsystemBase : subsystemList) 
      {
        skSubsystemBase.initializeTestMode();
        skSubsystemBase.enterTestMode();
        testModeIntialized = true;
      }
    }
  }

  public static void testModePeriodic(){
    for (SKSubsystemBase skSubsystemBase : subsystemList) {
      skSubsystemBase.testModePeriodic();
    }
  }
}

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

public final class TestModeManager {

  private static List<SKSubsystemBase> subsystemList = new ArrayList<>();
  

  public static void registerSubsystem(SKSubsystemBase skSubsystemBase)
  {
    subsystemList.add(skSubsystemBase);
  }

  public static void initializeTestMode(){
    for (SKSubsystemBase skSubsystemBase : subsystemList) {
      skSubsystemBase.initializeTestMode();
    }
  }

  public static void testModePeriodic(){
    for (SKSubsystemBase skSubsystemBase : subsystemList) {
      skSubsystemBase.testModePeriodic();
    }
  }
}

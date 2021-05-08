package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SKSubsystemBase extends SubsystemBase
{
  public SKSubsystemBase()
  {
    TestModeManager.registerSubsystem(this);
  }

  public abstract void initializeTestMode();

  public abstract void testModePeriodic();

  public abstract void enterTestMode();
}

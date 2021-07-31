// 
// A stub class implementing the SpeedController interface which provides us with a very
// simple motor controller model for use in unit testing.
//
package stubs;
import edu.wpi.first.wpilibj.SpeedController;

/** Interface for speed controlling devices. */
public class TestSpeedController implements SpeedController {
  private double currentSpeed          = 0.0;
  private static double batteryVoltage = 12.0;
  private boolean inverted              = false;

  public TestSpeedController(int deviceNumber) {
  }

  public TestSpeedController() {
  }

  /**
   * Common interface for setting the speed of a speed controller.
   *
   * @param speed The speed to set. Value should be between -1.0 and 1.0.
   */
  public void set(double speed)
  {
    currentSpeed = speed;
  }

  /**
   * Sets the voltage output of the SpeedController. Compensates for the current bus voltage to
   * ensure that the desired voltage is output even if the battery voltage is below 12V - highly
   * useful when the voltage outputs are "meaningful" (e.g. they come from a feedforward
   * calculation).
   *
   * <p>NOTE: This function *must* be called regularly in order for voltage compensation to work
   * properly - unlike the ordinary set function, it is not "set it and forget it."
   *
   * @param outputVolts The voltage to output.
   */
  public void setVoltage(double outputVolts) {
    set(outputVolts / batteryVoltage);
  }

  /**
   * Common interface for getting the current set speed of a speed controller.
   *
   * @return The current set speed. Value is between -1.0 and 1.0.
   */
  public double get() {
    return currentSpeed;
  }

  /**
   * Common interface for inverting direction of a speed controller.
   *
   * @param isInverted The state of inversion true is inverted.
   */
  public void setInverted(boolean isInverted) {
    inverted = isInverted;
  }

  /**
   * Common interface for returning if a speed controller is in the inverted state or not.
   *
   * @return isInverted The state of the inversion true is inverted.
   */
  public boolean getInverted()
  {
    return inverted;
  }

  /** Disable the speed controller. */
  public void disable()
  {
    currentSpeed = 0.0;
  }

  /**
   * Stops motor movement. Motor can be moved again by calling set without having to re-enable the
   * motor.
   */
  public void stopMotor()
  {
    currentSpeed = 0.0;
  }

  public void pidWrite(double output)
  {
    
  }
}

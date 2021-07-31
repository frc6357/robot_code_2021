import static org.junit.Assert.*;

import edu.wpi.first.hal.HAL;
import frc.robot.subsystems.base.BaseRoller;
import stubs.TestSpeedController;
import org.junit.*;

public class BaseRollerTest {
  public static final double SPEED = 0.5;
  private TestSpeedController controller = new TestSpeedController();
  private BaseRoller drive;

  @Before // this method will run before each test
  public void setup() {
    assert HAL.initialize(500, 0); // initialize the HAL, crash if failed
    drive = new BaseRoller(controller, SPEED); // create our roller object
  }

  @After // this method will run after each test
  public void shutdown() throws Exception {
    
  }

  @Test
  public void testStop() {
    drive.setStop();
    assertEquals(0.0, drive.getSpeed(), 0.0); // Make sure the motor has been stopped.
  }

  @Test
  public void testSetForwards() {
    drive.setForwards();
    assertEquals(SPEED, drive.getSpeed(), 0.0); // Make sure the motor is running forwards.
  }

  @Test
  public void testSetBackwards() {
    drive.setBackwards();
    assertEquals(-SPEED, drive.getSpeed(), 0.0); // Make sure the motor is running backwards.
  }
}

import static org.junit.Assert.*;

import edu.wpi.first.hal.HAL;
import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.simulation.SPISim;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.subsystems.SK21Drive;
import org.junit.*;

public class SK21DriveTest {
  public static final double HEADING_DELTA = 1e-2; // acceptable deviation range
  SK21Drive drive;

  @Before // this method will run before each test
  public void setup() {
    assert HAL.initialize(500, 0); // initialize the HAL, crash if failed
    drive = new SK21Drive(); // create our intake
  }

  @After // this method will run after each test
  public void shutdown() throws Exception {
    drive.close(); // destroy our drive object
  }

  @Test // marks this method as a test
  public void testResetGyroWorks() {
    drive.resetGyro(); 
    double heading = drive.getHeading();
    assertEquals(0.0, heading, HEADING_DELTA); // make sure that the value set to the motor is 0
  }
}

//
// Skeleton unit test for the SK21Drive class used in the 2021 robot from FRC Team 6357.
//
// NB: Currently, running this test (at least on a Mac) results in a runtime error in
// libc++ when the test exits. As yet, I have no idea what's causing this but I suspect
// it's related to the ADIS16448_IMU or WPI_TalonFX classes which are needed by the 
// subsystem. I've tried to stub out the IMU class but the problem persists so perhaps
// a WPI_TalonFX stub is also needed? Regardless, I've renamed the file to prevent it from
// being included in the current batch of unit tests run during each build.
//
import static org.junit.Assert.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.simulation.SPISim;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.subsystems.SK21Drive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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

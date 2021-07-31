// 
// A stub class implementing the SpeedController interface which provides us with a very
// simple motor controller model for use in unit testing.
//
package com.ctre.phoenix.motorcontrol.can;

import edu.wpi.first.wpilibj.SpeedController;
import stubs.TestSpeedController;

/** Interface for speed controlling devices. */
public class WPI_TalonFX extends TestSpeedController {
    public WPI_TalonFX(int deviceNumber) {
        super(deviceNumber);
        System.out.println("Using stub WPI_TalonFX class.");
    }

    public double getSelectedSensorPosition() {
        return 0.0;
    }

    public double getSelectedSensorVelocity() {
        return 0.0;
    }
    
    public void close() {
    }
}

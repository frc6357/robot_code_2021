// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical
 * or boolean constants. This class should not be used for any other purpose. All
 * constants should be declared globally (i.e. public static). Do not put anything
 * functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever
 * the constants are needed, to reduce verbosity.
 */
public final class Constants
{
    /**
     * Constants related to Driving the Robot.
     */
    public static final class DriveConstants
    {
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;

        // TODO: The value here is what we measured from the robot. Check to make sure this works
        // correctly because the drive characterization tool determines the track width automatically
        // and comes up with a rather different value closer to 1.0m.
        public static final double kTrackwidthMeters = 0.69;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kDriveGearReduction = 12.412;
        public static final int    kEncoderCPR = 2048;
        public static final double kWheelDiameterMeters = 0.18375;
        public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
            (kWheelDiameterMeters * Math.PI) / ((double) kEncoderCPR * kDriveGearReduction);

        // Data taken from characterization analysis on 03/02/2021
        public static final double ksVolts = 0.516;
        public static final double kvVoltSecondsPerMeter = 2.38;
        public static final double kaVoltSecondsSquaredPerMeter = 0.133;
        public static final double kPDriveVel = 1.88;
    }

    /**
     * Constants related to the Autonomous operation mode for the Robot.
     */
    public static final class AutoConstants
    {
        public static final double kMaxSpeedMetersPerSecond = 2;
        public static final double kMaxAccelerationMetersPerSecondSquared = .50;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }

    public static final double kInchesPerMilimetre = 0.0393701;
  
    public static final String kSplineDirectory = "/home/lvuser/deploy/paths";
    public static final String kSplineDirectoryWindows = "C:/Users/Owner/Documents/WeaverOutput/output";

    public static final String kSubsystem = "/home/lvuser/deploy/Subsystems.json";
    public static final String kSubsystemWindows = "C:/Users/Owner/Documents/Subsystems.json";
}

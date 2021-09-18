package frc.robot;

import edu.wpi.first.wpilibj.util.Color;

/**
 * TuningParams represents Tuning Parameters for the Robot.
 * 
 * These are generally items that are dependent upon a specific robot design, not
 * generally usable for another robot.
 */
public final class TuningParams 
{
    //////////////////////////////
    // Drive Params
    //////////////////////////////
    public static final double  ACCEL_MAX_TOWARDS_FORWARD       =  0.125;
    public static final double  ACCEL_MAX_TOWARDS_BACKWARD      = -0.125;
    public static final double  DECEL_MAX_TOWARDS_FORWARD       =  0.125;
    public static final double  DECEL_MAX_TOWARDS_BACKWARD      = -0.125;

    public static final double  CONTROLLED_TURN_SPEED           = 0.5;
    public static final double  SLOW_CONTROLLED_TURN_SPEED      = 0.2;
    public static final double  ANGLE_TURN_TOLERANCE            = 1;
    public static final double  AUTONOMOUS_SLOW_START_ANGLE     = 30;       // In Degrees

    public static final boolean ENCODER_LEFT_REVERSED           = false;
    public static final boolean ENCODER_RIGHT_REVERSED          = true;
    public static final int     ENCODER_PULSES                  = 480;
    public static final double  DRIVETRAIN_GEAR_REDUCTION       = 12.412;
    public static final double  WHEEL_DIAMETER                  = 18.375;   // 7.5" In Centimeters
    public static final double  WHEEL_CIRCUMFERENCE             = 57.72;

    public static final double  STRAIGHT_DRIVE_OFFSET_TOLERANCE = 1.0;      // In Centimeters
    public static final double  OFFSET_SPEED_INCREMENT          = 0.01;
    public static final double  AUTONOMOUS_DRIVE_SPEED          = 0.5;
    public static final double  AUTONOMOUS_LOW_SPEED_LEVEL      = 0.2;
    public static final double  AUTONOMOUS_SLOW_DISTANCE_AREA   = 40;       // In Centimeters

    public static final double  DRIVETRAIN_KP                   = 0.00000485;
    public static final double  DRIVETRAIN_KD                   = 0;

    public static final double  SLOWMODE_MULTIPLIER             = 0.25;
    public static final double  SLOWMODE_TRIGGER_THRESHOLD      = 0.75;     // Joystick Trigger Required Value

    public static final double  DEADBAND_TURN                   = 0.01;

    //////////////////////////////
    // Color Wheel Params
    //////////////////////////////
    public static final Color RGB_CYAN                         = new Color(0.1799, 0.4451, 0.3799);
    public static final Color RGB_GREEN                        = new Color(0.2199, 0.5200, 0.2300);
    public static final Color RGB_RED                          = new Color(0.4199, 0.3899, 0.1699);
    public static final Color RGB_YELLOW                       = new Color(0.3100, 0.5300, 0.1499);

    public static final int COLOR_WHEEL_PROXIMITY_THRESHOLD    = 30;

    public static final double COLOR_WHEEL_SPEED               = 0.5; 

    public static final int COLOR_WHEEL_TRANSITIONS            = 26; // Slightly more than three revs

    public static final int COLOR_WHEEL_ARRAY_SIZE             = 3;

    //////////////////////////////
    // Launcher Params
    //////////////////////////////
    public static final double LOADER_MAX_SPEED                = 1.0;
    public static final double RELEASE_MOTOR_SPEED             = 1.0;
    // TODO: Fix these later
    public static final double LAUNCHER_SET_PERCENTAGE_SLOW     = -0.53; // TODO: IMPORTANT! Tune this
    public static final double LAUNCHER_SET_PERCENTAGE_MEDIUM   = -0.63; // TODO: IMPORTANT! Tune this
    public static final double LAUNCHER_SET_PERCENTAGE_HIGH     = -0.70; // TODO: IMPORTANT! Tune this
    public static final double LAUNCHER_SET_PERCENTAGE_CRITICAL = -0.80; // TODO: IMPORTANT! Tune this
    public static final int RELEASE_MOTOR_RUNTIME               = 300; // In Milliseconds
    //TODO: IMPORTANT! Tune these PID values later
    // After reading information from Chief Delphi and RevRobotics people,
    // these values are supposed to be insanely small as their
    // software is different than most other PID controllers
    public static final double LAUNCHER_P_VALUE                = 0.001;
    public static final double LAUNCHER_I_VALUE                = 0.0000005;
    public static final double LAUNCHER_D_VALUE                = 0.00001;

    public static final double LAUNCHER_MAX_RPM                = 5700.0;
    public static final double LAUNCHER_IZONE_VALUE            = 2500;

    //////////////////////////////
    // Intake Params
    //////////////////////////////
    public static final double INTAKE_MAX_SPEED                = 1.0;
    public static final int INTAKE_ENCODER_PULSES              = 2048;
    public static final double INTAKE_WHEEL_DIAMETER           = 5.08; // 2.0 Inches in centimetres
    public static final boolean INTAKE_BALL_CHECK_INVERT       = false;

    //////////////////////////////
    // Ball Indexer Params
    //////////////////////////////
    public static final double INDEXER_SPEED                = 1.0;
    public static final double LAUNCHER_FEEDER_SPEED        = 1.0;
    public static final double TRIGGER_THRESHOLD            = 0.9;

    ///////////////////////////////
    // Climb Params
    ///////////////////////////////
    public static final double WINCH_MOTOR_SPEED            = 0.05;
    public static final int WINCH_MOTOR_CURRENT_TRIGGER     = 7;    // Triggers current limit when the motor pulls 7A
    public static final int WINCH_MOTOR_CURRENT_LIMIT       = 5;    // Sets the current limit to 5A
    public static final double WINCH_MOTOR_TRIGGER_TIME     = 0.5;  // Time to trigger the motor current threshold
    public static final boolean WINCH_LEFT_MOTOR_INVERT     = true; // TODO: Ensure motor direction is correct!

    ///////////////////////////////
    // Auto Params
    ///////////////////////////////
    public static final double AUTO_STRAIGHTSTMOVE_DRIVE_DISTANCE       = 50.0; //cm
    public static final double AUTO_OFFSETSTMOVE_DRIVE_DISTANCE         = -1000.0;
    public static final double LAUNCHER_START_UP_TIME                   = 3.0;

    public static final int AUTO_STRAIGHTSTTRENCH_TURN_ANGLE            = 90;
    public static final double AUTO_STRAIGHTSTTRENCH_DRIVE_DISTANCE_1   = 192.024;

    public static final double AUTO_TRENCH_DRIVE_DISTANCE               = 670.56;

    public static final int AUTO_OFFSETSTTRENCH_TURN_ANGLE_1            = -148;

    // Distance to drive forward or backward to get off the line in "Drive forwards" and "Drive backwards"
    public static final double AUTO_DRIVE_DISTANCE                      = 100.0;
    public static final double AUTO_DRIVE_DISTANCE_TO_WALL              = 294.0;   //10ft start line to wall
    public static final double AUTO_FIRE_ALL_BALLS_TIME                 = 4000.0;  // Milliseconds

    ///////////////////////////////
    // Test Params
    ///////////////////////////////
    public static final double TEST_DRIVE_STRAIGHT_1        = 100;    //Amount in centimeters
    public static final double TEST_DRIVE_STRAIGHT_2        = 200;    //Amount in centimeters
    public static final double TEST_DRIVE_STRAIGHT_3        = 500;    //Amount in centimeters

    public static final double TEST_TURN_1                  = 10;    //Amount of degrees
    public static final double TEST_TURN_2                  = 30;    //Amount of degrees
    public static final double TEST_TURN_3                  = 50;    //Amount of degrees
    public static final double TEST_TURN_4                  = 90;    //Amount of degrees
    public static final double TEST_TURN_5                  = 180;    //Amount of degrees

    public static final String SPLINE_DIRECTORY = "C:/Users/Owner/Documents/WeaverOutput/output";
}

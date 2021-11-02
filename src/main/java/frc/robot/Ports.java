package frc.robot;

// Resource Allocations:
//
// PCM 0 - colorSpinnerExtend
//     1 - launcherHoodExtend
//     2 - intakeMoverRaise  
//     3 - launcherFeederRetract
//     4 - launcherFeederExtend 
//     5 - intakeMoverDrop 
//     6 - launcherHoodRetract      
//     7 - colorSpinnerRetract

/** 
 * Definitions of all hardware connections and hardware-related
 * IDs on the robot. This class should be included in any other
 * class which needs to interact with the robot hardware. The
 * values here form part of the robot's control system configuration
 * specification.
 */
public class Ports 
{

    //////////////////////////////
    // Robot Infrastructure
    //////////////////////////////
    public static final int pcm                         = 1;  // CAN ID

    //////////////////////////////
    // I2C device addresses
    //////////////////////////////
    public static final int i2cColorSensor              = 0x52;

    //////////////////////////////
    // Drivetrain Addresses
    //////////////////////////////
    public static final int frontLeftDrive              = 10; // CAN ID
    public static final int frontRightDrive             = 11; // CAN ID
    public static final int backLeftDrive               = 12; // CAN ID
    public static final int backRightDrive              = 13; // CAN ID

    //////////////////////////////
    // Intake Addresses
    //////////////////////////////
    public static final int intakeMotor                 = 20; // CAN ID

    public static final int intakeMoverDrop             = 5;  // PCM 1, Output
    public static final int intakeMoverRaise            = 2;  // PCM 1, Output

    public static final int intakeOpenCheck             = 4;  // DIO
    public static final int intakeSpeedCheckA           = 5;  // DIO
    public static final int intakeSpeedCheckB           = 6;  // DIO
    public static final int intakeBallCheck             = 7;  // DIO

    ///////////////////////////////
    // Ball Indexer Addresses
    ///////////////////////////////
    public static final int indexerMotor                = 21; // CAN ID
    public static final int feederMotor                 = 22; // CAN ID

    public static final int launcherFeederRetract          = 3;  // PCM 1, Output
    public static final int launcherFeederExtend         = 4;  // PCM 1, Output

    ///////////////////////////////
    // Ball Launcher Addresses
    ///////////////////////////////
    public static final int ballLauncherMotor           = 23; // CAN ID
    public static final int ballLoaderMotor             = 24; // CAN ID
    public static final int ballReleaseMotor            = 25; // CAN ID

    public static final int launcherHoodExtend          = 1;  // PCM 1, Output
    public static final int launcherHoodRetract         = 6;  // PCM 1, Output

    ///////////////////////////////
    // Climb Addresses
    ///////////////////////////////
    public static final int winchClimbLeft              = 30; // CAN ID
    public static final int winchClimbRight             = 31; // CAN ID


    ///////////////////////////////
    // Control Wheel Addresses
    ///////////////////////////////
    public static final int colorWheelSpinner           = 40; // CAN ID

    public static final int colorSpinnerExtend          = 0;  // PCM 1, Output 
    public static final int colorSpinnerRetract         = 7;  // PCM 1, Output


    ///////////////////////////////
    // Additional hardware
    ///////////////////////////////
    public static final int MindSensorsCANLight         = 3;  // CAN ID of LED strip controller

    ///////////////////////////////
    // Operator Interface
    ///////////////////////////////
    public static final int OIDriverJoystick            = 0;

    // Controls set for Arcade Drive - left stick turn, right stick throttle.
    public static final int OIDriverTurn                = 0;  // Left Joystick X
    // public static final int OIDriverMove                = 1;  // WingmanWheel Move Axis
    public static final int OIDriverMove                = 5;  // Right Joystick Y
    
    public static final int OIDriverSlowmode            = 3;  // Right Trigger Axis

    public static final int OIOperatorJoystick          = 1;
    public static final int OIOperatorDpad = 0; 

    // public static final int OIOperatorStopColorWheel    = 11;    // Nothing at the moment
    // public static final int OIOperatorStartThreeRotate  = 9;    // Left Joystick press button
    public static final int OIOperatorStartSetColor     = 12;   // Nothing at the moment
    public static final int OIOperatorColorWheelLift    = 10;    // Right Stick
    
    
    public static final int OIOperatorReverseIntake     = 9;    // Left Joystick Button
    
    public static final int OIOperatorActivateIndexer   = 2;    // Left Trigger Axis
    public static final int OIOperatorDeactivateIndexer = 3;    // Right Trigger Axis
    
    public static final int OIOperatorHighHoodAngle     = 5;    // Left Bumper
    public static final int OIOperatorLowHoodAngle      = 6;    // Right Bumper
    public static final int OIOperatorShootBall         = 2;    // Button B

    public static final int OIOperatorClimbReverse      = 4;    // Button Y
    public static final int OIOperatorClimb             = 7;    // Button Back

    public static final int OIOperatorSetLauncherSpeed  = 1;    // Button A



    
}

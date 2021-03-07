package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.commands.DefaultColorWheelCommand;
import frc.robot.subsystems.base.BaseRoller;
import frc.robot.subsystems.base.Color2021;
import frc.robot.subsystems.base.ColorSensor2021;

/**
 * The SK20ColorWheel class is the subsystem that interacts with roller and color sensor
 * mechanism used to spin the 2020-2021 game control panel. This class must be used in
 * conjunction with related command classes which will be responsible for activating the
 * spinner motor, detecting color sensor reading changes, and updating the color
 * transition counter stored here.
 */
public class SK21ColorWheel extends SubsystemBase
{
    private BaseRoller spinnerRoller;
    private DoubleSolenoid spinnerLifter;
    private CANSparkMax spinnerRollerMotor;
    private int spinnerTransitionCount = 0;
    private ColorSensor2021 colorSensor;
    private CANEncoder spinnerRollerEncoder;
    private static Color2021[] fieldColors =
            {Color2021.RED, Color2021.GREEN, Color2021.CYAN, Color2021.YELLOW};
    private Color2021[] colorArray =
            new Color2021[TuningParams.COLOR_WHEEL_ARRAY_SIZE];
    private int indexOfColorArray = 0;
    private boolean colorArrayIsFull = false;
    private Color2021 colorDebounced = Color2021.NONE;
    private final DefaultColorWheelCommand colorWheelDefaultCommand;

    /**
     * Creates the SK21ColorWheel object and all hardware resources it uses.
     */
    public SK21ColorWheel()
    {
        colorSensor = new ColorSensor2021(I2C.Port.kOnboard);
        spinnerRollerMotor =
                new CANSparkMax(Ports.colorWheelSpinner, MotorType.kBrushless);
        spinnerRoller = new BaseRoller(spinnerRollerMotor,
            TuningParams.COLOR_WHEEL_SPEED);
        spinnerLifter = new DoubleSolenoid(Ports.pcm, Ports.colorSpinnerExtend,
            Ports.colorSpinnerRetract);
        spinnerRollerEncoder = spinnerRollerMotor.getEncoder();

        colorWheelDefaultCommand = new DefaultColorWheelCommand(this);
        setDefaultCommand(colorWheelDefaultCommand);
    }

    /**
     * This function energizes the solenoid to lift the color wheel mechanism.
     */
    public void extendLifter()
    {
        spinnerLifter.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * This function de-energies the solenoid to retract the color wheel mechanism.
     */
    public void retractLifter()
    {
        spinnerLifter.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Turns on the motor which rotates the spinner which, when in contact with the
     * control panel, spins the colored disk.
     */
    public void activateSpinnerRoller()
    {
        spinnerRoller.setForwards();
    }

    /**
     * Turns off the motor which rotates the spinner which, when in contact with the
     * control panel, spins the colored disk.
     */
    public void deactivateSpinnerRoller()
    {
        spinnerRoller.setStop();
    }

    /**
     * Checks whether the lifter is in the extended or retracted position.
     * 
     * @return True for extended, false for retracted.
     */
    public boolean getIsLifterExtended()
    {
        return spinnerLifter.get() == DoubleSolenoid.Value.kForward;
    }

    /**
     * Returns the current speed of the spinner roller.
     * 
     * @return The speed of the spinner.
     */
    public double getSpinnerRollerSpeed()
    {
        return spinnerRollerEncoder.getVelocity();
    }

    /**
     * Resets the color transition counter. This method is intended to allow a zero
     * position to be set prior to starting rotating the color wheel.
     */
    public void resetSpinnerTransitionCount()
    {
        spinnerTransitionCount = 0;
    }

    /**
     * As the control panel color disk turns, the command managing it will monitor the
     * detected color and detect changes. Each time a change is detected, this method must
     * be called to keep track of the distance the wheel has moved since the count was
     * last reset. Since several commands are likely to be needed, this keeps the
     * accumulated number of transitions (and, hence, the accumulated total rotation) in a
     * single place that each command can access.
     */
    public void incrementSpinnerTransitionCount()
    {
        spinnerTransitionCount++;
    }

    /**
     * Returns the color transition counter. The transition count is incremented when the
     * spinner roller is active and the color sensor detects a change in the color above
     * it.
     * 
     * @return The number of detected color transitions since the last call to
     *         resetSpinnerTransitionCount().
     */
    public int getSpinnerTransitionCount()
    {
        return spinnerTransitionCount;
    }

    /**
     * Returns the color currently detected by the color sensor or NONE if the proximity
     * sensor indicates that it is not currently under the control panel. This method
     * merely reads the current value from the sensor. No debouncing is performed.
     * 
     * @return The game color closest to the color currently being detected.
     */
    public Color2021 getDetectedColor()
    {
        if (colorSensor
            .getProximity() >= TuningParams.COLOR_WHEEL_PROXIMITY_THRESHOLD)
        {
            return colorSensor.getGameColor();
        }
        return Color2021.NONE;
    }

    /**
     * Returns the color currently detected by the color sensor or NONE if the proximity
     * sensor indicates that it is not currently under the control panel.
     * 
     * This function debounces the read color so other commands and subsystems do not need
     * to do it.
     * 
     * @return The game color closest to the color currently being detected.
     */
    public Color2021 getDebouncedColor()
    {
        // Updates the color array.
        colorArray[indexOfColorArray] = getDetectedColor();

        // This line is used to make sure that the index values don't go out of bounds.
        indexOfColorArray =
                (indexOfColorArray + 1) % TuningParams.COLOR_WHEEL_ARRAY_SIZE;

        // This checks that the color array has been filled
        if (indexOfColorArray == 0)
        {
            colorArrayIsFull = true;
        }

        // This code is to run once the color array has been filled in at least once.
        if (colorArrayIsFull)
        {
            // This part checks that all of values in the color array are the same to
            // unbounce the reading.
            for (int i = 1; i < TuningParams.COLOR_WHEEL_ARRAY_SIZE; i++)
            {
                // If we detect a difference, return the last color because we've not finished
                // debouncing yet.
                if (colorArray[i] != colorArray[0])
                {
                    return colorDebounced;
                }
            }

            // If we get here, we read the same value the appropriate number of times
            // back-to-back so we assume that the reading is stable.
            colorDebounced = colorArray[0];
        }

        return colorDebounced;
    }

    /**
     * Returns the color which should be detected by the field's control panel color
     * sensor based on the color the robot is currently detecting. If the proximity sensor
     * indicates that it is not currently under the control panel, returns UNKNOWN.
     * 
     * @return The game color we expect the field is currently detecting or UNKNOWN if the
     *         robot is not currently reading a color.
     */
    public Color2021 getFieldDetectedColor()
    {
        /**
         * @return the color the the fieldsensor is detecting and if the detected color is
         *         NONE which is determined by getDetectedColor() it will return just
         *         NONE. And if finally the code never matches any of these conditions if
         *         will just return UNKNOWN
         */
        Color2021 col = getDetectedColor();
        if (col == Color2021.NONE)
        {
            return col;
        }
        else
        {
            for (int i = 0; i < fieldColors.length; i++)
            {
                if (col == fieldColors[i])
                {
                    return fieldColors[(i + 2) % 4];
                }
            }

        }

        return Color2021.UNKNOWN;
    }

    /**
     * Returns the color which should be detected by the field's control panel color
     * sensor based on the color the robot is currently detecting. If the proximity sensor
     * indicates that it is not currently under the control panel, returns UNKNOWN.
     * 
     * This function debounces the color read to guard against transitional weirdness.
     * 
     * @return The game color we expect the field is currently detecting or UNKNOWN if the
     *         robot is not currently reading a color.
     */
    public Color2021 getDebouncedFieldDetectedColor()
    {
        /**
         * @return the color the the fieldsensor is detecting and if the detected color is
         *         NONE which is determined by getDetectedColor() it will return just
         *         NONE. And if finally the code never matches any of these conditions if
         *         will just return UNKNOWN
         */
        Color2021 col = getDebouncedColor();
        if (col == Color2021.NONE)
        {
            return col;
        }
        else
        {
            for (int i = 0; i < fieldColors.length; i++)
            {
                if (col == fieldColors[i])
                {
                    return fieldColors[(i + 2) % 4];
                }
            }
        }

        return Color2021.UNKNOWN;
    }
}

package frc.robot.subsystems.base;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.TuningParams;

/**
 * The class ColorSensor2021 will have three methods that will return Color rgb values,
 * returns the game color, and finally will return a normalized distance to determine if
 * the color sensor can accurately determine the color or if it is too far.
 */

public class ColorSensor2021
{
    /**
     * This is the ColorSensorV3 member variable that is used by all three methods.
     */
    private final ColorSensorV3 colSensor;

    /**
     * The ColorMatch object used to match colors.
     */
    private final ColorMatch colorMatch = new ColorMatch();

    /**
     * This is the constructor that needs a I2C port to be passed in when creating an
     * instance of ColorSensor2021.
     * 
     * @param port
     *            The I2C Port to which the ColorSensor is attached
     */
    public ColorSensor2021(I2C.Port port)
    {
        colSensor = new ColorSensorV3(port);

        colorMatch.addColorMatch(TuningParams.RGB_CYAN);
        colorMatch.addColorMatch(TuningParams.RGB_GREEN);
        colorMatch.addColorMatch(TuningParams.RGB_RED);
        colorMatch.addColorMatch(TuningParams.RGB_YELLOW);
    }

    /**
     * This method will return the color that the ColorSensorV3 is reading.
     * 
     * @return The color that the ColorSensorV3 is reading
     */
    public Color2021 getGameColor()
    {
        Color detectedColor = colSensor.getColor();
        ColorMatchResult matcher = colorMatch.matchClosestColor(detectedColor);

        if (matcher.color == TuningParams.RGB_CYAN)
        {
            return Color2021.CYAN;
        }
        else if (matcher.color == TuningParams.RGB_GREEN)
        {
            return Color2021.GREEN;
        }
        else if (matcher.color == TuningParams.RGB_RED)
        {
            return Color2021.RED;
        }
        else if (matcher.color == TuningParams.RGB_YELLOW)
        {
            return Color2021.YELLOW;
        }
        else
        {
            return Color2021.UNKNOWN;
        }
    }

    /**
     * This method returns a normalized integer value that tells how far the ColorSensorV3
     * needs to go before it can accuratly read the colors.
     * 
     * @return The normalized integer value that tells how far the ColorSensorV3 needs to
     *         go before it can accuratly read the colors
     */
    public int getProximity()
    {
        return colSensor.getProximity();
    }
}

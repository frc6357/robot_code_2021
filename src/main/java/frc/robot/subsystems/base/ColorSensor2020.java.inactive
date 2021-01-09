package frc.robot.subsystems.base;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.TuningParams;

/**
 * The class ColorSensor2020 will have three methods that will return Color rgb
 * values, returns the game color, and finally will return a normalized distance
 * to determine if the color sensor can acurately determine the color or if it
 * is too far.
 */

public class ColorSensor2020 {
    /**
     * This is the ColorSensorV3 member variable that is used by all three methods.
     */
    private ColorSensorV3 colSensor;
    private ColorMatch colorMatch = new ColorMatch();

    /**
     * This is the constructor that needs a I2C port to be passed in when creating
     * an instance of ColorSensor2020.
     */
    public ColorSensor2020(I2C.Port port) {
        colSensor = new ColorSensorV3(port);

        colorMatch.addColorMatch(TuningParams.RGB_CYAN);
        colorMatch.addColorMatch(TuningParams.RGB_GREEN);
        colorMatch.addColorMatch(TuningParams.RGB_RED);
        colorMatch.addColorMatch(TuningParams.RGB_YELLOW);
    }

    /**
     * This method will return Color rgb values that are normalized.
     */
    public Color getColor() {
        return colSensor.getColor();
    }

    /**
     * This method will return the color that the ColorSensorV3 is reading.
     */
    public Color2020 getGameColor() {

        Color detectedColor = colSensor.getColor();
        ColorMatchResult matcher = colorMatch.matchClosestColor(detectedColor);

        if (matcher.color == TuningParams.RGB_CYAN) {
            return Color2020.CYAN;
        } else if (matcher.color == TuningParams.RGB_GREEN) {
            return Color2020.GREEN;
        } else if (matcher.color == TuningParams.RGB_RED) {
            return Color2020.RED;
        } else if (matcher.color == TuningParams.RGB_YELLOW) {
            return Color2020.YELLOW;
        } else {
            return Color2020.UNKNOWN;
        }
    }

    /**
     * This method returns a normalized integer value that tells how far the
     * ColorSensorV3 needs to go before it can accuratly read the colors.
     */
    public int getProximity() {
        return colSensor.getProximity();
    }

}
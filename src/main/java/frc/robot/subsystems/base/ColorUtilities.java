package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Static methods related to Color.
 */
public class ColorUtilities
{
    /**
     * It takes in a color and reports back to Smart Dashboard to see what color the
     * sensor is detecting.
     * 
     * @param color
     */
    public static void reportColor(Color2021 color)
    {
        boolean red = (color == Color2021.RED);
        boolean green = (color == Color2021.GREEN);
        boolean cyan = (color == Color2021.CYAN);
        boolean yellow = (color == Color2021.YELLOW);

        SmartDashboard.putBoolean("isRed", red);
        SmartDashboard.putBoolean("isGreen", green);
        SmartDashboard.putBoolean("isCyan", cyan);
        SmartDashboard.putBoolean("isYellow", yellow);
    }
}
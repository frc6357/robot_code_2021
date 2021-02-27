package frc.robot.utils;

import edu.wpi.first.wpilibj.Encoder;

public class ScaledEncoder extends Encoder
{
    private final int pulsesPerRotation;

    /**
     * Constructor for the Scaled Encoder
     * @param channelA The digital input ID for channel A
     * @param channelB The digital input ID for channel B
     * @param reverseDirection Sets if the encoder direction is reversed
     * @param pulses The number of pulses per revolution for the encoder
     * @param diameter Diameter of the drivetrain wheels
     */
    public ScaledEncoder(int channelA, int channelB, boolean reverseDirection, int pulses, double diameter)
    {
        super(channelA, channelB, reverseDirection);
        pulsesPerRotation = pulses;
        setDistancePerPulse((diameter * Math.PI) / pulses);
    }

    /**
     * Constructor for the Scaled Encoder
     * @param channelA The digital input ID for channel A
     * @param channelB The digital input ID for channel B
     * @param pulses The number of pulses per revolution for the encoder
     * @param diameter Diameter of the drivetrain wheels
     */
    public ScaledEncoder(int channelA, int channelB, int pulses, double diameter)
    {
        super(channelA, channelB);
        pulsesPerRotation = pulses;
        setDistancePerPulse((diameter * Math.PI) / pulses);
    }

    /**
     * Gets the amount of degrees the wheel has turned
     * @return double - The amount of degrees the wheel has turned
     */
    public double getAngleDegrees()
    {
        return (get() * 360) / pulsesPerRotation;
    }

    /**
     * Gets the amount of radians the wheel has turned
     * @return double - The amount of radians the wheel has turned
     */
    public double getAngleRadians()
    {
        return (get() * 2 * Math.PI) / pulsesPerRotation;
    }

    /**
     * Gets the amount of rotations the wheel has turned
     * @return double - The amount of rotations the wheel has turned
     */
    public double getRotations()
    {
        return get() / pulsesPerRotation;
    }
}
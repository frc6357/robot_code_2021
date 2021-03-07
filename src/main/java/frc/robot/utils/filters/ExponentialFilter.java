package frc.robot.utils.filters;

/**
 * Creates a cubic curve on the filter
 * 0.0 correlates to 0.0, and 1.0 correlates to "coeff" and
 * -1.0 correlates to "-coeff". The transfer function is
 * 
 * output = coefficient * (input^3)
 */
public class ExponentialFilter extends Filter
{
    /**
     * Coefficient of gain to multiply by.
     */
    private double coefficient;

    /**
     * Constructs a new ExponentialFilter with the given gain coefficient.
     * 
     * @param coef
     *            The coefficient of the cubic function for this ExponentialFilter. This
     *            can be positive or negative, allowing the joystick value to be inverted
     *            at the same time as the filter is applied.
     */
    public ExponentialFilter(double coef)
    {
        coefficient = coef;
    }

    /**
     * Filters the input using a scaled cubic transfer function.
     * 
     * @param rawAxis
     *            The data to be filtered, from -1 to 1
     * @return The cubic relation of that data
     */
    @Override
    public double filter(double rawAxis)
    {
        return coefficient * Math.pow(rawAxis, 3);
    }

    /**
     * Sets the coefficient of the cubic function of this ExponentialFilter.
     * 
     * @param c
     *            The new coefficient for this ExponentialFilter, which must be greater
     *            than zero
     */
    public void setCoef(double c)
    {
        coefficient = Math.abs(c);
    }
}

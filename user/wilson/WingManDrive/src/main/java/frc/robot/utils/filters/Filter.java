package frc.robot.utils.filters;

/**
 * This class is an abstract template for individual Filters. It lays out the groundwork
 * for any filtering to be done by subclasses.
 */
public abstract class Filter
{
    /**
     * Filters the given input value per the rules of this Filter, and returns the
     * filtered value.
     * 
     * @param rawAxis
     *            The actual value being returned by the raw data
     * @return The filtered data to be passed to the motor
     */
    public abstract double filter(double rawAxis);
}

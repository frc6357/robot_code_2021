package frc.robot.utils.filters;

import java.lang.Math;

/*
 * Creates a cubic curve on the filter
 * 0.0 correlates to 0.0, and 1.0 correlates to "coeff" and
 * -1.0 correlates to "-coeff". The transfer function is
 * 
 * output = coefficient * (input^3)
 */
public class CubicDeadbandFilter extends Filter
{
    private double coefficient; // Coefficient or gain to multiply by
    private double deadband; // Deadband value for the controller to avoid drift

    private double filteredInput;
    private double reverseFilter;

    private double c; // Used for creating the cubic function

    /**
     * Default constructor, gives a coefficient of coef
     * 
     * @param coefficient
     *            The coefficient of the cubic function. This can be positive or negative, allowing
     *            the joystick value to be inverted at the same time as the filter is applied. This
     *            number should be less than 1.
     * @param deadband
     *            Used as an area around the zero of the controller inputs. Should be a positive
     *            value. This is used to stop natural controller drift and small accidental inputs.
     */
    public CubicDeadbandFilter(double coefficient, double deadband, boolean reverseInput)
    {
        this.coefficient = coefficient;
        this.deadband = deadband;
        reverseFilter = (reverseInput ? -1.0 : 1.0);
    }

    /**
     * Filters the input using a scaled cubic transfer function.
     * 
     * @param rawAxis
     *            the data to be read in, from -1 to 1
     * @return the cubic relation of that data
     */
    @Override
    public double filter(double rawAxis)
    {
        filteredInput = (Math.abs(rawAxis) - deadband) * Math.signum(rawAxis);
        c = (1 - ( coefficient * Math.pow( (1 - deadband), 3) ) / (1 - deadband));

        if (Math.abs(rawAxis) < deadband)
        {
            return 0.0;
        }
        else
        {
            return coefficient * Math.pow(filteredInput, 3) * c * filteredInput * reverseFilter;
        }
        
    }

    /**
     * Sets the coefficient of the cubic function
     * 
     * @param c
     *            the coefficient, which must be between zero and one
     */
    public void setCoef(double newC)
    {
        coefficient = Math.abs(newC);
    }

    /**
     * Sets the coefficient of the cubic function
     * 
     * @param newD
     *            the deadband
     */
    public void setDeadband(double newD)
    {
        deadband = Math.abs(newD);
    }
}

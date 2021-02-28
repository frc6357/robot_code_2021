package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class MotorEncoder {
    
    private WPI_TalonFX m_SelectedMotor;
    private double metersPerPulse;
    private double lastPositionResetValue = 0.0;
    private double inversionFactor;

    /**
     * Sets up the encoder wrapper with the required motor and other informaiton to calculate
     * distance and velocities
     * @param selectedMotorEncoder The motor used to find the base information from
     * @param metersPerPulse The distance that the wheel moves with each pulse from 
     *                          the motor
     * @param inverted Looks whether or not the motor is inverted
     */
    public MotorEncoder(WPI_TalonFX selectedMotorEncoder, double metersPerPulse, boolean isInverted) {
        m_SelectedMotor = selectedMotorEncoder;
        this.metersPerPulse = metersPerPulse;
        inversionFactor = isInverted ? -1 : 1;
    }

    /**
     * Calculates the position of the motor in pulses using the current
     * position, inversion, and the last motor position reset
     * @return The current position of the motor in pulses
     */
    public double getPositionPulses() {
        return (m_SelectedMotor.getSelectedSensorPosition() - lastPositionResetValue) * inversionFactor;
    }

    /**
     * Calculates the velocity of the motor in pulses using the
     * inversion, and the last motor position reset
     * @return The current velocity of the motor in pulse units
     */
    public double getVelocityPulses() {
        return m_SelectedMotor.getSelectedSensorVelocity() * inversionFactor;
    }

    /**
     * Calculates the position of the motor in meters using the given
     * meters per pulse and the current position
     * @return The current position of the motor in meters
     */
    public double getPositionMeters() {
        return getPositionPulses() * metersPerPulse;
    }

    /**
     * Calculates the velocity of the motor in meters using the given
     * meters per pulse and the current velocity
     * @return The current velocity of the motor in meters
     */
    public double getVelocityMeters() {
        return getVelocityPulses() * metersPerPulse;
    }

    /**
     * Resets the position of the motor encoder to zero
     */
    public void resetEncoder() {
        lastPositionResetValue = m_SelectedMotor.getSelectedSensorPosition();
    }

    /**
     * Sets the distance in meters that is given for each pulse to calculate
     * position and velocity speed in meters
     * @param metersPerPulse The given meters that should be used per meter
     */
    public void setMetersPerPulse(double metersPerPulse) {
        this.metersPerPulse = metersPerPulse;
    }
}

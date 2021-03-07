package frc.robot.utils;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonSRXSpeedController implements SpeedController
{
    private final WPI_TalonSRX speedController;

    /**
     * Constructs a new TalonSRXSpeedController with the given CAN address.
     * 
     * @param CANAddress
     *            The CAN address for the speed controller underlying this
     *            TalonSRXSpeedController
     */
    public TalonSRXSpeedController(int CANAddress)
    {
        speedController = new WPI_TalonSRX(CANAddress);
    }

    @Override
    public void pidWrite(double output)
    {
        speedController.pidWrite(output);
    }

    @Override
    public void set(double speed)
    {
        speedController.set(speed);
    }

    @Override
    public double get()
    {
        return speedController.get();
    }

    @Override
    public void setInverted(boolean isInverted)
    {
        speedController.setInverted(isInverted);
    }

    @Override
    public boolean getInverted()
    {
        return speedController.getInverted();
    }

    @Override
    public void disable()
    {
        speedController.disable();
    }

    @Override
    public void stopMotor()
    {
        speedController.stopMotor();
    }

}

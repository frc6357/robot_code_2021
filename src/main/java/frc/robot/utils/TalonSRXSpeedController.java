package frc.robot.utils;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;;

public class TalonSRXSpeedController implements SpeedController
{
    WPI_TalonSRX speedController;

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

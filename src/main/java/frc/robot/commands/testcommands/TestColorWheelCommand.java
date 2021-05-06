package frc.robot.commands.testcommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21ColorWheel;

/**
 * A Command for testing the Color Wheel subsystem.
 */
public class TestColorWheelCommand extends CommandBase
{
    /**
     * The Color Wheel subsystem under test.
     */
    private final SK21ColorWheel colorwheelSubsystem;

    /**
     * NetworkTableEntry for the Deployment Solenoid used to extend the mechanism.
     */
    private ComplexWidget colorwheelDeployment;

    /**
     * Sendable chooser used to set the options for the Solenoid
     */
    SendableChooser<DoubleSolenoid.Value> solenoidChooser = new SendableChooser<DoubleSolenoid.Value>();

    /**
     * NetworkTableEntry for the Motor used to spin the color wheel.
     */
    private NetworkTableEntry colorwheelMotor;

    /**
     * Constructor of the test command for the color wheel that sets up the
     * the dependencies
     * 
     * @param colorwheelSubsystem The color wheel subsystem that is being used
     */
    public TestColorWheelCommand(SK21ColorWheel colorwheelSubsystem)
    {
        this.colorwheelSubsystem = colorwheelSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(colorwheelSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        
        solenoidChooser.setDefaultOption("Neutral", DoubleSolenoid.Value.kOff);
        solenoidChooser.addOption("Forwards", DoubleSolenoid.Value.kForward);
        solenoidChooser.addOption("Backwards", DoubleSolenoid.Value.kReverse);

        // Toggle widget that controls the extension state of the color wheel mechanism
        colorwheelDeployment = Shuffleboard.getTab("Color Wheel").add("Extension", solenoidChooser)
        .withWidget(BuiltInWidgets.kComboBoxChooser).withSize(2, 1).withPosition(0, 0);

        // Slider widget going from -1 to 1 that controls the motor that is used to spin the
        // the color wheel
        colorwheelMotor = Shuffleboard.getTab("Color Wheel").add("Spin", 0)
            .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 1).withPosition(0, 2).getEntry();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        // Grabs the boolean value of the togglable widget and uses the value
        // to set the state of the solenoid that extends the mechanism
        DoubleSolenoid.Value value = solenoidChooser.getSelected();
        colorwheelSubsystem.spinnerLifter.set(value);

        // Grabs the value of the slider and sets that speed to the motor
        double speed = colorwheelMotor.getValue().getDouble();
        colorwheelSubsystem.spinnerRollerMotor.set(speed);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21ColorWheel;
import frc.robot.subsystems.base.Color2021;
import frc.robot.subsystems.base.ColorUtilities;

/**
 * A command that reports the field detected color to the driver's station.
 */
public class DefaultColorWheelCommand extends CommandBase
{
    /**
     * The Color Wheel subsystem for this DefaultColorWheelCommand.
     */
    private final SK21ColorWheel colorWheelSubsystem;

    /**
     * The previous color seen by this DefaultColorWheelCommand as it runs through
     * execute().
     */
    private Color2021 prevColor;

    /**
     * Creates a new DefaultColorWheelCommand that sets up the member subsystem.
     *
     * @param colorWheelSubsystem
     *            The subsystem used by the command to read the colors
     */
    public DefaultColorWheelCommand(SK21ColorWheel colorWheelSubsystem)
    {
        this.colorWheelSubsystem = colorWheelSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(colorWheelSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        prevColor = Color2021.NONE;
    }

    /**
     * This method, which is usually run every 20ms, reads and reports the field detected
     * color to the driver's station
     */
    @Override
    public void execute()
    {
        Color2021 readColor = colorWheelSubsystem.getDebouncedFieldDetectedColor();
        if (prevColor != readColor)
        {
            prevColor = readColor;
            ColorUtilities.reportColor(prevColor);
        }
    }

    // False as default commands are intended to not end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

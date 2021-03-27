// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK21Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An intake command that normalizes the direction of the intake rollers when it is
 * extended.
 */
public class DefaultIntakeCommand extends CommandBase
{

    /**
     * The Ball Intake Subsystem
     */
    private final SK21Intake intakeSubsystem;

    /**
     * Creates a new DefaultIntakeCommand which takes in the required subsystem
     *
     * @param intakeSubsystem
     *            The intake subsystem used by this command.
     */
    public DefaultIntakeCommand(SK21Intake intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intakeSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    /**
     * It will set the intake to go to the forward direction and will only set it if the
     * intake is extended.
     */
    @Override
    public void initialize()
    {
        /**
         * Note: This looks strange, but is useful, as the ExtendIntakeCommand has a "side
         * effect" of starting the intake roller. If this is not done here when the
         * default command re-enters, then the rollers could only be started by retracting
         * and then extending the rollers. This is a "good" detection of existing state of
         * the robot.
         */
        if (intakeSubsystem.isIntakeExtended())
        {
            intakeSubsystem.startIntakeRoller();
        }
    }

    // False as default commands are intended to not end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}

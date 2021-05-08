/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK21Launcher;

/**
 * This command toggles the hood angle to low.
 */
public class SetHoodLowShotCommand extends CommandBase
{
    /**
     * The Ball Launcher Subsystem.
     */
    private final SK21Launcher launcherSubsystem;

    /**
     * Creates a new SetHoodLowShotCommand which takes in the required subsystem.
     *
     * @param launcherSubsystem
     *            The launcher subsystem used by this command.
     */
    public SetHoodLowShotCommand(SK21Launcher launcherSubsystem)
    {
        this.launcherSubsystem = launcherSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(launcherSubsystem);
    }

    // Called once when the command is initially scheduled - we use as a "one shot"
    @Override
    public void initialize()
    {
        launcherSubsystem.setHoodForHighAngleShot(false);
    }

    // Return true as we used initialize() as a one-shot (we do not need ongoing behavior).
    @Override
    public boolean isFinished()
    {
        return true;
    }
}

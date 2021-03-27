/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK21Launcher;

/**
 * This command works by setting the launcher speed based on the hood position.
 * 
 * TODO: Update the command to be able to use multiple different speeds - Because of the
 * change in the game this year it has become more and more important to make sure that
 * the PID gets tuned properly and the code is built to be able to take multiple different
 * speeds based off of a SmartDashboard Chooser
 */
public class LauncherActivateCommand extends CommandBase
{
    /**
     * The Launcher Subsystem used by this LauncherActivateCommand.
     */
    private final SK21Launcher launcherSubsystem;

    /**
     * Creates a new LauncherActivateCommand.
     *
     * @param launcherSubsystem
     *            The subsystem used by this command.
     */
    public LauncherActivateCommand(SK21Launcher launcherSubsystem)
    {
        this.launcherSubsystem = launcherSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(launcherSubsystem);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        if (launcherSubsystem.isHoodSetToShootHigh())
        {
            launcherSubsystem.setLauncherSpeed(TuningParams.LAUNCHER_SET_PERCENTAGE_SLOW);
        }
        else
        {
            launcherSubsystem.setLauncherSpeed(TuningParams.LAUNCHER_SET_PERCENTAGE_CRITICAL);
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}

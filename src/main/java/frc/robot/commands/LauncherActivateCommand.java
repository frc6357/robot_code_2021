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
 * TODO: Update the command to be able to use multiple different speeds
 *  - Because of the change in the game this year it has become more and more important to make sure that
 *    the PID gets tuned properly and the code is built to be able to take multiple different speeds
 *    based off of a SmartDashboard Chooser
 */
public class LauncherActivateCommand extends CommandBase 
{
    private final SK21Launcher m_subsystem;
    private final boolean endable;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public LauncherActivateCommand(SK21Launcher subsystem, boolean endable) 
    {
        m_subsystem = subsystem;
        this.endable = endable;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        if (m_subsystem.isHoodSetToShootHigh())
        {
            m_subsystem.setLauncherSpeed(TuningParams.LAUNCHER_SET_PERCENTAGE_SLOW);
        }

        else
        {
            m_subsystem.setLauncherSpeed(TuningParams.LAUNCHER_SET_PERCENTAGE_CRITICAL);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return endable;
    }
}
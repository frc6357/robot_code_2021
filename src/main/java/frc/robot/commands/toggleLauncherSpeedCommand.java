// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.TuningParams;
import frc.robot.subsystems.SK21Launcher;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command will be used to set the speeds of the of the launcher motor to
 * the desired speed, out of the desired list of speeds.
 */
public class ToggleLauncherSpeedCommand extends CommandBase {
    
    private final SK21Launcher m_subsystem;
    private int speedIndex = 0;

    private final double[] possibleSpeeds = {TuningParams.LAUNCHER_SET_PERCENTAGE_SLOW,
                                             TuningParams.LAUNCHER_SET_PERCENTAGE_MEDIUM,
                                             TuningParams.LAUNCHER_SET_PERCENTAGE_HIGH,
                                             TuningParams.LAUNCHER_SET_PERCENTAGE_CRITICAL};


    /**
     * Creates a new toggleLauncherSpeedCommand and sets up the variables.
     *
     * @param subsystem The launcher subsystem needed by this command to control the launcher.
     */
    public ToggleLauncherSpeedCommand(SK21Launcher subsystem) {
        // Sets up the required variables from the constructer for our use
        m_subsystem = subsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // Sets the speed for the launcher by going through the 
        // possibleSpeed arrays and sets up the index for the
        // next time it is called.
        m_subsystem.setLauncherSpeed(possibleSpeeds[speedIndex]);
        speedIndex = (speedIndex + 1) % 4;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The command only sets the speeds and does nothing else, so the command ends immediately
        return true;
    }
}

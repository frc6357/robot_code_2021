package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK20Drive;
import frc.robot.subsystems.base.SuperClasses.Gear;

/**
 * This command sets the gear on the drivetrain to either high or low.
 */
public class SetGear extends CommandBase {
    private final SK20Drive m_subsystem;
    private Gear gearTarget;

    /**
     * Creates a new SetGear that takes in the subsystem needed to change the gear
     * on the drivetrain and takes in the desired gear value that we need to set it
     * to.
     *
     * @param subsystem  The subsystem used the command to switch the gear.
     * @param gearTarget The gear we want to set the ssolenoid to.
     */
    public SetGear(SK20Drive subsystem, Gear gearTarget) {
        m_subsystem = subsystem;
        this.gearTarget = gearTarget;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_subsystem.setGear(gearTarget);
    }

    // This should never be executed
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Always returns true because all the work is done in the command
    // initialization.
    @Override
    public boolean isFinished() {
        return true;
    }
}
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.TuningParams;
import frc.robot.subsystems.SK20Drive;

/**
 * A command that drives the robot n distance forward.
 */
public class DriveStraightCommand extends CommandBase {
    private final SK20Drive m_subsystem;
    private double initialLeftEncoderValue;
    private double initialRightEncoderValue;
    private final double distanceTarget;
    private boolean isDone = false;

    /**
     * Constructor that creates a new DriveStraightCommand, sets up the member
     * subsystem, and sets the desired target distance we are trying to reach.
     *
     * @param subsystem      The subsystem used by the command to set drivetrain
     *                       motor speeds.
     * @param distanceTarget The amount of centimeters we want to the robot to move.
     */
    public DriveStraightCommand(SK20Drive subsystem, double distanceTarget) {
        m_subsystem = subsystem;
        this.distanceTarget = distanceTarget;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        initialLeftEncoderValue = m_subsystem.getLeftEncoderDistance();
        initialRightEncoderValue = m_subsystem.getRightEncoderDistance();
        isDone = false;
    }

    /**
     * This method sets the target speeds that the motors need to achieve to drive
     * straight usually every 20ms. This method also looks with the encoders to make
     * sure that the robot isn't 'drifting' into a certain direction. If the robot
     * happens to be drifting into a certain direction however, the code will
     * correct the speeds such that the robot doesn't drift as much if at all.
     */
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftEncoderDistance = m_subsystem.getLeftEncoderDistance();
        double rightEncoderDistance = m_subsystem.getRightEncoderDistance();
        double deltaLeftEncoderDistance = leftEncoderDistance - initialLeftEncoderValue;
        double deltaRightEncoderDistance = rightEncoderDistance - initialRightEncoderValue;
        double absDelta = Math.abs(deltaLeftEncoderDistance - deltaRightEncoderDistance);
        double driveSpeed = TuningParams.AUTONOMOUS_DRIVE_SPEED * Math.signum(distanceTarget);

        if (deltaLeftEncoderDistance >= (distanceTarget - TuningParams.AUTONOMOUS_SLOW_DISTANCE_AREA)
                || deltaRightEncoderDistance >= (distanceTarget - TuningParams.AUTONOMOUS_SLOW_DISTANCE_AREA)) {
            driveSpeed = TuningParams.AUTONOMOUS_LOW_SPEED_LEVEL * Math.signum(distanceTarget);
        }

        if (absDelta <= TuningParams.STRAIGHT_DRIVE_OFFSET_TOLERANCE) {
            m_subsystem.setSpeeds(driveSpeed, driveSpeed);

        } else {
            double increment = (absDelta * TuningParams.OFFSET_SPEED_INCREMENT) * Math.signum(distanceTarget);
            if (deltaLeftEncoderDistance > deltaRightEncoderDistance) {
                m_subsystem.setSpeeds(driveSpeed - increment, driveSpeed);
            } else {
                m_subsystem.setSpeeds(driveSpeed, driveSpeed - increment);
            }
        }

        if (deltaLeftEncoderDistance >= Math.abs(distanceTarget) || deltaRightEncoderDistance >= Math.abs(distanceTarget)) {
            m_subsystem.setSpeeds(0.0, 0.0);
            isDone = true;
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // If we were interrupted, for safety, stop both motors.
        if (interrupted) {
            m_subsystem.setSpeeds(0.0, 0.0);
            isDone = true;
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isDone;
    }
}

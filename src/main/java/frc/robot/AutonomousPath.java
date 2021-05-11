package frc.robot;
import java.io.File;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public interface AutonomousPath {
    public File getFile();
    private Trajectory makeTrajectoryFromJSON();
    public Trajectory getTrajectory();
    
}

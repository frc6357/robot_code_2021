package frc.robot;
import java.io.File;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class Drive1mForwardBackward implements AutonomousPath {

    private File jsonFile;
    private Trajectory jsonFileTrajectory;
    File splineDirectory = new File(Constants.kSplineDirectory);
    if (!splineDirectory.exists())
    {
        splineDirectory = new File(Constants.kSplineDirectoryWindows);
    }


    public Drive1mForwardBackward(){
        jsonFile = 
    }

    public File getFile(){
        return jsonFile;
    }

    public Trajectory getTrajectory(){
        return jsonFileTrajectory;
    }

    
}

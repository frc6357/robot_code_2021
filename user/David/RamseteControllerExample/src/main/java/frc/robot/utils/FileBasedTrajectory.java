// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

/** Add your docs here. */
public class FileBasedTrajectory implements Function<TrajectoryConfig, Trajectory> {

  private final JsonTrajectory trajectory;

  public FileBasedTrajectory(File trajectoryFile) throws JsonParseException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(trajectoryFile);
    trajectory = mapper.readValue(parser, JsonTrajectory.class);

  }

  @Override
  public Trajectory apply(TrajectoryConfig config) {

    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(trajectory.getStart(),
        trajectory.getInteriorWaypoints(), trajectory.getEnd(), config);
    return exampleTrajectory;

  }

}

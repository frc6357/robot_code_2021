// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

/** Add your docs here. */
public class JsonTrajectory {

  private final Pose2d start;
  private final List<Translation2d> interiorWaypoints;
  private final Pose2d end;

  public JsonTrajectory(@JsonProperty(required = true, value = "start") Pose2d start,
      @JsonProperty(required = true, value = "interiorWaypoints") List<Translation2d> interiorWaypoints,
      @JsonProperty(required = true, value = "end") Pose2d end) {

    this.start = start;
    this.interiorWaypoints = interiorWaypoints;
    this.end = end;
  }

  public Pose2d getStart() {
    return start;
  }

  public List<Translation2d> getInteriorWaypoints() {
    return interiorWaypoints;
  }

  public Pose2d getEnd() {
    return end;
  }

}

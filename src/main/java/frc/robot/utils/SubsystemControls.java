package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubsystemControls {

  private final boolean intake;
  private final boolean launcher;
  private final boolean indexer;

  public SubsystemControls(@JsonProperty(required = true, value = "intake") boolean intake,
      @JsonProperty(required = true, value = "launcher") boolean launcher,
      @JsonProperty(required = true, value = "indexer") boolean indexer) {

    this.intake = intake;
    this.launcher = launcher;
    this.indexer = indexer;
  }

  public boolean isIntakePresent() {
    return intake;
  }

  public boolean isLauncherPresent() {
    return launcher;
  }

  public boolean isIndexerPresent() {
    return indexer;
  }
  
}

package com.github.asamaraw.wiz;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class WizcliPluginExtension {
  private final Property<String> projectId;
  private final Property<String> repositoryName;

  public WizcliPluginExtension(Project project) {
    this.projectId = project.getObjects().property(String.class);
    this.repositoryName = project.getObjects().property(String.class);
  }

  public Property<String> getProjectId() {
    return projectId;
  }

  public Property<String> getRepositoryName() {
    return repositoryName;
  }
}

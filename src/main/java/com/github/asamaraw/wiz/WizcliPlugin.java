package com.github.asamaraw.wiz;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;

public class WizcliPlugin implements Plugin<Project> {
  public void apply(Project project) {
    project.getExtensions().create("wiz", WizcliPluginExtension.class);

    // Create directory scan task
    project
        .getTasks()
        .register(
            "wizScanDir",
            Exec.class,
            task -> {
              task.setGroup("Wiz");
              task.setDescription("Runs directory scan using the Wiz CLI");
              task.commandLine("wizcli", "dir", "scan");

              // Configure task with project details
              WizcliPluginExtension wizExt =
                  project.getExtensions().findByType(WizcliPluginExtension.class);
              if (wizExt == null) {
                throw new IllegalStateException("Wiz extension not configured");
              }
              String projectId = wizExt.getProjectId().getOrNull();
              if (projectId == null) {
                throw new IllegalStateException("Project ID not configured in Wiz extension");
              }
              String repositoryName = wizExt.getRepositoryName().getOrNull();
              if (repositoryName == null) {
                throw new IllegalStateException("Repository name not configured in Wiz extension");
              }
              task.args("--project", projectId);
              task.args("--name", repositoryName);
            });
  }
}

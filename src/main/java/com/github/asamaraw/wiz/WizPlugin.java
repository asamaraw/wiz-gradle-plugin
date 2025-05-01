package com.github.asamaraw.wiz;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Exec;

public class WizPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Create extension for Wiz configuration
        WizExtension extension = project.getExtensions().create("wiz", WizExtension.class);

        // Create wizRun task
        project.getTasks().register("wizRun", Exec.class, task -> {
            task.setGroup("Wiz");
            task.setDescription("Runs the Wiz CLI with configured options");
            task.commandLine("wiz", "run");
            
            // Configure task based on extension properties
            String options = extension.getOptions().getOrNull();
            if (options != null) {
                String[] optionsArray = options.split(" ");
                task.args((Object[]) optionsArray);
            }
        });

        // Create wizBuild task
        project.getTasks().register("wizBuild", Exec.class, task -> {
            task.setGroup("Wiz");
            task.setDescription("Builds using the Wiz CLI with configured options");
            task.commandLine("wiz", "build");
            
            // Configure task based on extension properties
            String options = extension.getOptions().getOrNull();
            if (options != null) {
                String[] optionsArray = options.split(" ");
                task.args((Object[]) optionsArray);
            }
        });
    }
}

class WizExtension {
    private final Property<String> options;

    public WizExtension(Project project) {
        this.options = project.getObjects().property(String.class);
    }

    public Property<String> getOptions() {
        return options;
    }
}

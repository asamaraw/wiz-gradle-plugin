# Wiz Gradle Plugin

This Gradle plugin provides integration with the Wiz CLI, allowing you to run Wiz commands directly from your Gradle build.

## Usage

Add the plugin to your `build.gradle` file:

```groovy
plugins {
    id 'com.github.asamaraw.wiz-gradle-plugin' version '0.1.0'
}
```

### Available Tasks

- `wizScanDir`: Runs a directory scan using the Wiz CLI. This task requires the project ID (projectId) and repository name (repositoryName) to be configured in the Wiz extension. It executes the command `wizcli dir scan` with the project and repository details.

## Example

```groovy
plugins {
    id 'com.github.asamaraw.wiz-gradle-plugin' version '0.1.0'
}

// You can also override options per task
wizScanDir {
    args '--projectId de97b048-4f7e-4bda-907c-7303bdb36afc --repositoryName example-repo'
}
```

## Requirements

- Java 17 or higher
- Wiz CLI installed and available in PATH

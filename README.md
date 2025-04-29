# Wiz Gradle Plugin

This Gradle plugin provides integration with the Wiz CLI, allowing you to run Wiz commands directly from your Gradle build.

## Usage

Add the plugin to your `build.gradle` file:

```groovy
plugins {
    id 'com.example.wiz' version '1.0.0'
}

// Configure Wiz options
wiz {
    options = '--your-options-here'
}
```

### Available Tasks

- `wizRun`: Runs the Wiz CLI with the configured options
- `wizBuild`: Builds using the Wiz CLI with the configured options

## Example

```groovy
plugins {
    id 'com.example.wiz' version '1.0.0'
}

wiz {
    options = '--config wiz-config.yaml --verbose'
}

// You can also override options per task
wizRun {
    args '--config other-config.yaml'
}
```

## Example Configuration

An example Wiz configuration file is provided at [example-wiz-config.yaml](example-wiz-config.yaml). This file includes common settings for:
- Project information
- Build settings
- Environment configuration
- Logging settings
- Custom properties

You can use this as a template for your own Wiz configuration.

## Requirements

- Java 17 or higher
- Wiz CLI installed and available in PATH

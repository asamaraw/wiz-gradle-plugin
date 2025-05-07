package com.github.asamaraw.wiz;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WizcliPluginTest {

  private Project project;
  private WizcliPlugin plugin;

  @Mock private ExtensionContainer extensions;

  @Mock private WizcliPluginExtension extension;

  @Mock private TaskProvider<Exec> taskProvider;

  @Mock private Exec task;

  private Action<Exec> taskConfig;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    project = ProjectBuilder.builder().build();
    plugin = new WizcliPlugin();

    // Mock the extension creation
    when(project.getExtensions()).thenReturn(extensions);
    when(extensions.create(eq("wiz"), eq(WizcliPluginExtension.class))).thenReturn(extension);

    // Mock the task creation
    when(project.getTasks()).thenReturn(project.getTasks());
    @SuppressWarnings("unchecked")
    Action<Exec> mockAction = mock(Action.class);
    taskConfig = mockAction;
    when(project.getTasks().register(eq("wizScanDir"), eq(Exec.class), eq(taskConfig)))
        .thenReturn(taskProvider);
    when(taskProvider.get()).thenReturn(task);
    doAnswer(
            invocation -> {
              Exec task = mock(Exec.class);
              return task;
            })
        .when(taskProvider)
        .get();
  }

  @Test
  public void testApplyCreatesExtension() {
    plugin.apply(project);

    verify(extensions).create(eq("wiz"), eq(WizcliPluginExtension.class));
  }

  @Test
  public void testApplyRegistersTask() {
    plugin.apply(project);

    verify(project.getTasks()).register(eq("wizScanDir"), eq(Exec.class), eq(taskConfig));
    verify(taskProvider).get();
  }

  @Test
  public void testApplyConfiguresTask() {
    plugin.apply(project);

    verify(task).setGroup("Wiz");
    verify(task).setDescription("Runs directory scan using the Wiz CLI");
    verify(task).commandLine("wizcli", "dir", "scan");
  }

  @Test
  public void testApplyHandlesMissingExtension() {
    when(project.getExtensions().findByType(WizcliPluginExtension.class)).thenReturn(null);

    assertThrows(IllegalStateException.class, () -> plugin.apply(project));
  }

  @Test
  public void testApplyHandlesMissingProjectId() {
    when(extension.getProjectId().getOrNull()).thenReturn(null);

    assertThrows(IllegalStateException.class, () -> plugin.apply(project));
  }

  @Test
  public void testApplyHandlesMissingRepositoryName() {
    when(extension.getProjectId().getOrNull()).thenReturn("project-123");
    when(extension.getRepositoryName().getOrNull()).thenReturn(null);

    assertThrows(IllegalStateException.class, () -> plugin.apply(project));
  }

  @Test
  public void testApplyConfiguresTaskWithExtensionValues() {
    String projectId = "project-123";
    String repositoryName = "my-repo";

    when(extension.getProjectId().getOrNull()).thenReturn(projectId);
    when(extension.getRepositoryName().getOrNull()).thenReturn(repositoryName);

    plugin.apply(project);

    verify(task).args("--project", projectId);
    verify(task).args("--name", repositoryName);
  }
}

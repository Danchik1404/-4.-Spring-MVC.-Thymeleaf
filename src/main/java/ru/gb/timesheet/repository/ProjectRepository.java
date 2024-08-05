package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProjectRepository {
  private static long sequence = 1L;
  private final List<Project> projects = new ArrayList<>();

  public boolean exists(Long projectId) {
    return projects.stream()
            .anyMatch(t -> Objects.equals(t.getProjectId(), projectId));
  }

  public Optional<Project> getById(Long id) {
    return projects.stream()
            .filter(t -> Objects.equals(t.getProjectId(), id))
            .findFirst();
  }

  public List<Project> getAll() {
    return List.copyOf(projects);
  }

  public Project create(Project project) {
    project.setProjectId(sequence++);
    projects.add(project);
    return project;
  }

  public void delete(Long id) {
    projects.removeIf(t -> Objects.equals(t.getProjectId(), id));
  }
}
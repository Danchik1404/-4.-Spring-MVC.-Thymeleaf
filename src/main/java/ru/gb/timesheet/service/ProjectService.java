package ru.gb.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectService(ProjectRepository repository) {
    this.projectRepository = repository;
  }

  public boolean ifExists(Long projectId) {
    return projectRepository.exists(projectId);
  }

  public Optional<Project> getById(Long id) {
    if (!ifExists(id)) {
      throw new IllegalArgumentException("Project with ID " + id + " does not exist.");
    }
    return projectRepository.getById(id);
  }

  public List<Project> getAll() {
    return projectRepository.getAll();
  }

  public Project create(Project project) {
    return projectRepository.create(project);
  }

  public void delete(Long id) {
    if (!ifExists(id)) {
      throw new IllegalArgumentException("Project with ID " + id + " does not exist.");
    }
    projectRepository.delete(id);
  }
}
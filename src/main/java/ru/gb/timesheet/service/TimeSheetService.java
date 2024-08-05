package ru.gb.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.TimeSheetRepository;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSheetService {

  private final TimeSheetRepository repository;
  private final ProjectService projectService;

  @Autowired
  public TimeSheetService(TimeSheetRepository repository, ProjectService projectService) {
    this.repository = repository;
    this.projectService = projectService;
  }

  public boolean ifExists(Long projectId) {
    return repository.exists(projectId);
  }

  public Optional<Timesheet> getById(Long id, Long projectId) {
    if (!ifExists(projectId)) {
      throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    }
    return repository.getById(id);
  }

  public Optional<Timesheet> getById(Long id) {
    return repository.getById(id);
  }

  public List<Timesheet> getAll() {
    return repository.getAll();
  }

  public List<Timesheet> getAllByProjectId(Long projectId) {
    if (!ifExists(projectId)) {
      throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    }
    return repository.getAllByProjectId(projectId);
  }

  public List<Timesheet> getAllByProjectIdAndDate(Long projectId, LocalDate createdAtAfter, LocalDate createdAtBefore) {
    List<Timesheet> timesheets = repository.getAllByProjectId(projectId);
    return timesheets.stream()
            .filter(t -> (createdAtAfter == null || t.getCreatedAt().isAfter(createdAtAfter))
                    && (createdAtBefore == null || t.getCreatedAt().isBefore(createdAtBefore)))
            .collect(Collectors.toList());
  }

  public Timesheet create(Timesheet timesheet, Long projectId) {
    if (!projectService.getById(projectId).isPresent()) {
      throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    }
    return repository.create(timesheet, projectId);
  }

  public void delete(Long id, Long projectId) {
    if (!ifExists(projectId)) {
      throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    }
    repository.delete(id);
  }
}
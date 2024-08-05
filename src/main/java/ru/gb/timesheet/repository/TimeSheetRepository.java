package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Timesheet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TimeSheetRepository {

  private final ConcurrentHashMap<Long, Long> projectSequenceMap = new ConcurrentHashMap<>();
  private final List<Timesheet> timesheets = new ArrayList<>();

  private long getNextIdForProject(Long projectId) {
    return projectSequenceMap.merge(projectId, 1L, (oldValue, newValue) -> oldValue + 1);
  }

  public boolean exists(Long projectId) {
    return projectSequenceMap.containsKey(projectId);
  }

  public Optional<Timesheet> getById(Long id) {
    return timesheets.stream()
            .filter(t -> Objects.equals(t.getId(), id))
            .findFirst();
  }

  public List<Timesheet> getAll() {
    return List.copyOf(timesheets);
  }

  public List<Timesheet> getAllByProjectId(Long projectId) {
    return timesheets.stream()
            .filter(t -> Objects.equals(t.getProjectId(), projectId.toString()))
            .collect(Collectors.toList());
  }

  public Timesheet create(Timesheet timesheet, Long projectId) {
    timesheet.setId(getNextIdForProject(projectId));
    timesheet.setCreatedAt(LocalDate.now());
    timesheet.setProjectId(projectId.toString());
    timesheets.add(timesheet);
    return timesheet;
  }

  public Timesheet create(Timesheet timesheet) {
//        timesheet.setId(getNextIdForProject(projectId));
//        timesheet.setCreatedAt(LocalDate.now());
//        timesheet.setProjectId(projectId.toString());
    timesheets.add(timesheet);
    return timesheet;
  }

  public void delete(Long id) {
    timesheets.removeIf(t -> Objects.equals(t.getId(), id));
  }


}
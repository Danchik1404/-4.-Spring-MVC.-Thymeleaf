package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.page.TimeSheetPageDto;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimeSheetService timesheetService;
    private final ProjectService projectService;

    public List<TimeSheetPageDto> findAll() {
        return timesheetService.getAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    public Optional<TimeSheetPageDto> findById(Long id) {
        return timesheetService
                .getById(id)
                .map(this::convert);
    }

    public List<TimeSheetPageDto> findAllByProjectId(Long projectId) {
        return timesheetService.getAllByProjectId(projectId)
                .stream()
                .map(this::convert)
                .toList();
    }

    public Optional<Project> findProjectById(Long projectId) {
        return projectService.getById(projectId);
    }

    private TimeSheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.getById(Long.valueOf(timesheet.getProjectId()))
                .orElseThrow();

        TimeSheetPageDto timesheetPageParameters = new TimeSheetPageDto();
        timesheetPageParameters.setProjectName(project.getName());
        timesheetPageParameters.setId(String.valueOf(timesheet.getId()));
        timesheetPageParameters.setProjectId(String.valueOf(timesheet.getProjectId()));
        timesheetPageParameters.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));

        return timesheetPageParameters;
    }
}
package ru.gb.timesheet.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimeSheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}/timesheets")
public class TimeSheetController {

    private final TimeSheetService timesheetService;

    public TimeSheetController(TimeSheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable Long projectId, @PathVariable("id") Long id) {
        Optional<Timesheet> ts = timesheetService.getById(id, projectId);
        if (ts.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll(@PathVariable Long projectId) {
        return ResponseEntity.ok(timesheetService.getAllByProjectId(projectId));

    }

    @GetMapping("/filter")
    public ResponseEntity<List<Timesheet>> getAllByDate(
            @PathVariable Long projectId,
            @RequestParam(required = false) String createdAtAfter,
            @RequestParam(required = false) String createdAtBefore) {

        LocalDate afterDate = createdAtAfter != null ? LocalDate.parse(createdAtAfter) : null;
        LocalDate beforeDate = createdAtBefore != null ? LocalDate.parse(createdAtBefore) : null;

        return ResponseEntity.ok(timesheetService.getAllByProjectIdAndDate(projectId, afterDate, beforeDate));
    }


    @PostMapping
    public ResponseEntity<Timesheet> create(@PathVariable Long projectId, @RequestBody Timesheet timesheet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheetService.create(timesheet, projectId));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId, @PathVariable Long id) {
        timesheetService.delete(id, projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
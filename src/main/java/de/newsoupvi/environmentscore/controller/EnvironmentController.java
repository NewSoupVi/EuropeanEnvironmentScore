package de.newsoupvi.environmentscore.controller;

import de.newsoupvi.environmentscore.dto.AnalysisTaskDTO;
import de.newsoupvi.environmentscore.model.AnalysisTask;
import de.newsoupvi.environmentscore.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EnvironmentController {
    private final TaskService taskService;

    @Autowired
    public EnvironmentController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/analysis")
    public ResponseEntity<AnalysisTask> createAnalysis(@RequestBody Map<String, Double> coords, HttpSession session) {
        double lat = coords.get("lat");
        double lng = coords.get("lng");
        String sessionId = session.getId();
        AnalysisTask task = taskService.createAnalysisTask(lat, lng, sessionId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/analysis/{id}")
    public ResponseEntity<AnalysisTaskDTO> getAnalysis(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean includeResults,
            HttpSession session) {
        AnalysisTask task = taskService.getTask(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        if (!session.getId().equals(task.getSessionId())) {
            return ResponseEntity.notFound().build();
        }

        // Check if results already fetched
        if (includeResults && task.isResultsFetched()) {
            return ResponseEntity.status(403).build();
        }

        AnalysisTaskDTO taskDTO = taskService.getTaskWithQueueInfo(id, includeResults);

        // Mark results as fetched if requested
        if (includeResults) {
            taskService.markResultsFetched(id);
        }

        return ResponseEntity.ok(taskDTO);
    }
}

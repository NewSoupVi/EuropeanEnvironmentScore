package de.newsoupvi.environmentscore.controller;

import de.newsoupvi.environmentscore.model.AnalysisTask;
import de.newsoupvi.environmentscore.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EnvironmentController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/analysis")
    public ResponseEntity<AnalysisTask> createAnalysis(@RequestBody Map<String, Double> coords, HttpSession session) {
        double lat = coords.get("lat");
        double lng = coords.get("lng");
        String sessionId = session.getId();
        AnalysisTask task = taskService.createAnalysisTask(lat, lng, sessionId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/analysis/{id}")
    public ResponseEntity<AnalysisTask> getAnalysis(@PathVariable Long id, HttpSession session) {
        AnalysisTask task = taskService.getTask(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Verify session ownership
        if (!session.getId().equals(task.getSessionId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(task);
    }
}

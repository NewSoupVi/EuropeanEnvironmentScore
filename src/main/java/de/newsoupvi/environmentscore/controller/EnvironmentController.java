package de.newsoupvi.environmentscore.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class EnvironmentController {

    @GetMapping("/score")
    public ResponseEntity<Map<String, Object>> getEnvironmentScore() {
        Map<String, Object> response = new HashMap<>();
        response.put("score", 85);
        response.put("status", "Good");
        response.put("lastUpdated", "2024-01-15T10:30:00Z");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations")
    public ResponseEntity<Map<String, Object>> getLocations() {
        Map<String, Object> response = new HashMap<>();
        response.put("locations", new Object[]{
            Map.of("id", 1, "name", "Central Park", "lat", 40.7829, "lng", -73.9654, "score", 92),
            Map.of("id", 2, "name", "Times Square", "lat", 40.7580, "lng", -73.9855, "score", 78),
            Map.of("id", 3, "name", "Brooklyn Bridge", "lat", 40.7061, "lng", -73.9969, "score", 88)
        });
        return ResponseEntity.ok(response);
    }
}

package de.newsoupvi.environmentscore.dto;

import de.newsoupvi.environmentscore.model.AnalysisTask;

import java.util.List;

public class AnalysisTaskDTO {
    private final Long id;
    private final double latitude;
    private final double longitude;
    private final List<SubtaskDTO> subtasks;

    public AnalysisTaskDTO(AnalysisTask task, List<SubtaskDTO> subtasks) {
        this.id = task.getId();
        this.latitude = task.getLatitude();
        this.longitude = task.getLongitude();
        this.subtasks = subtasks;
    }

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<SubtaskDTO> getSubtasks() {
        return subtasks;
    }
}


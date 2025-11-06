package de.newsoupvi.environmentscore.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AnalysisTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "analysisTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subtask> subtasks = new ArrayList<>();

    private String sessionId;

    private boolean resultsFetched = false;

    public AnalysisTask() {
        // JPA requires no-arg constructor
    }

    public AnalysisTask(double latitude, double longitude, String sessionId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sessionId = sessionId;
        this.subtasks = new ArrayList<>();
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

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        this.subtasks.add(subtask);
        subtask.setAnalysisTask(this);
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isResultsFetched() {
        return resultsFetched;
    }

    public void setResultsFetched(boolean resultsFetched) {
        this.resultsFetched = resultsFetched;
    }
}


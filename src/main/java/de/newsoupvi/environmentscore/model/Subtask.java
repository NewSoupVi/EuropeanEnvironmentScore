package de.newsoupvi.environmentscore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "A", "B", or "C"

    @Enumerated(EnumType.STRING)
    private SubtaskStatus status;

    private String result;

    private Long createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AnalysisTask analysisTask;

    public Subtask() {
        // JPA requires no-arg constructor
    }

    public Subtask(String type) {
        this.type = type;
        this.status = SubtaskStatus.QUEUED;
        this.result = null;
        this.createdAt = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public SubtaskStatus getStatus() {
        return status;
    }

    public void setStatus(SubtaskStatus status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AnalysisTask getAnalysisTask() {
        return analysisTask;
    }

    public void setAnalysisTask(AnalysisTask analysisTask) {
        this.analysisTask = analysisTask;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}


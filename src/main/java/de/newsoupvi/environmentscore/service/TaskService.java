package de.newsoupvi.environmentscore.service;

import de.newsoupvi.environmentscore.model.AnalysisTask;
import de.newsoupvi.environmentscore.model.Subtask;
import de.newsoupvi.environmentscore.model.SubtaskStatus;
import de.newsoupvi.environmentscore.repository.AnalysisTaskRepository;
import de.newsoupvi.environmentscore.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final AnalysisTaskRepository analysisTaskRepository;
    private final SubtaskRepository subtaskRepository;

    @Autowired
    public TaskService(AnalysisTaskRepository analysisTaskRepository, SubtaskRepository subtaskRepository) {
        this.analysisTaskRepository = analysisTaskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    @Transactional
    public AnalysisTask createAnalysisTask(double latitude, double longitude, String sessionId) {
        // Cancel all queued subtasks from this session's previous tasks
        cancelQueuedTasksForSession(sessionId);

        AnalysisTask task = new AnalysisTask(latitude, longitude, sessionId);

        // Create 3 subtasks: A, B, C
        String[] types = {"A", "B", "C"};
        for (String type : types) {
            Subtask subtask = new Subtask(type);
            task.addSubtask(subtask);
        }

        // Save to DB - TaskExecutorService will pick up queued subtasks
        task = analysisTaskRepository.save(task);

        return task;
    }

    @Transactional
    protected void cancelQueuedTasksForSession(String sessionId) {
        analysisTaskRepository.findBySessionId(sessionId).forEach(task -> 
            task.getSubtasks().stream()
                .filter(subtask -> subtask.getStatus() == SubtaskStatus.QUEUED)
                .forEach(subtask -> {
                    subtask.setStatus(SubtaskStatus.CANCELED);
                    subtaskRepository.save(subtask);
                })
        );
    }

    public AnalysisTask getTask(Long taskId) {
        return analysisTaskRepository.findById(taskId).orElse(null);
    }
}


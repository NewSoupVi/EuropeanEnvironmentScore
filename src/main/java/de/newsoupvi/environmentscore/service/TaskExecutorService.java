package de.newsoupvi.environmentscore.service;

import de.newsoupvi.environmentscore.model.Subtask;
import de.newsoupvi.environmentscore.model.SubtaskStatus;
import de.newsoupvi.environmentscore.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskExecutorService {
    private final SubtaskRepository subtaskRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Random random = new Random();

    @Autowired
    public TaskExecutorService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    @Scheduled(fixedDelay = 500) // Check every 500ms
    public void processQueuedTasks() {
        List<Subtask> queuedSubtasks = subtaskRepository.findByStatus(SubtaskStatus.QUEUED);

        for (Subtask subtask : queuedSubtasks) {
            subtask.setStatus(SubtaskStatus.QUEUED_SUBMITTED);
            subtaskRepository.save(subtask);
            executorService.submit(() -> executeSubtask(subtask.getId()));
        }
    }

    private void executeSubtask(Long subtaskId) {
        Subtask subtask = subtaskRepository.findById(subtaskId).orElse(null);
        if (subtask == null) {
            return;
        }

        // Check if already canceled before starting
        if (subtask.getStatus() == SubtaskStatus.CANCELED) {
            return;
        }

        subtask.setStatus(SubtaskStatus.IN_PROGRESS);
        subtaskRepository.save(subtask);

        try {
            // Sleep for random time between 3-5 seconds
            int sleepTime = 3000 + random.nextInt(2001);
            Thread.sleep(sleepTime);

            // Refetch to check if canceled during execution
            subtask = subtaskRepository.findById(subtaskId).orElse(null);
            if (subtask == null || subtask.getStatus() == SubtaskStatus.CANCELED) {
                return;
            }

            // Set result
            subtask.setResult("Result " + subtask.getType());
            subtask.setStatus(SubtaskStatus.DONE);
            subtaskRepository.save(subtask);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


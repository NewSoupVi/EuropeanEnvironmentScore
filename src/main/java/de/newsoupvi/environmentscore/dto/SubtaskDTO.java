package de.newsoupvi.environmentscore.dto;

import de.newsoupvi.environmentscore.model.Subtask;
import de.newsoupvi.environmentscore.model.SubtaskStatus;

public class SubtaskDTO {
    private final Long id;
    private final String type;
    private final SubtaskStatus status;
    private final String result;
    private final long queuePosition;

    public SubtaskDTO(Subtask subtask, long queuePosition) {
        this.id = subtask.getId();
        this.type = subtask.getType();
        this.status = mapStatusForFrontend(subtask.getStatus());
        this.result = subtask.getResult();
        this.queuePosition = queuePosition;
    }

    private static SubtaskStatus mapStatusForFrontend(SubtaskStatus status) {
        return status == SubtaskStatus.QUEUED_SUBMITTED ? SubtaskStatus.QUEUED : status;
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

    public String getResult() {
        return result;
    }

    public long getQueuePosition() {
        return queuePosition;
    }
}


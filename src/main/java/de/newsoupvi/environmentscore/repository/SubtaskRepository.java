package de.newsoupvi.environmentscore.repository;

import de.newsoupvi.environmentscore.model.Subtask;
import de.newsoupvi.environmentscore.model.SubtaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByStatus(SubtaskStatus status);

    long countByStatusInAndCreatedAtLessThan(List<SubtaskStatus> statuses, Long createdAt);
}


package de.newsoupvi.environmentscore.repository;

import de.newsoupvi.environmentscore.model.AnalysisTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisTaskRepository extends JpaRepository<AnalysisTask, Long> {
    List<AnalysisTask> findBySessionId(String sessionId);
}


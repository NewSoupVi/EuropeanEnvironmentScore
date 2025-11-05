<template>
  <div class="analysis-shelf">
    <h2>Analysis Tasks</h2>
    <div
      v-if="!analysisTask"
      class="loading"
    >
      <p>Initializing analysis...</p>
    </div>
    <div
      v-else
      class="subtasks-container"
    >
      <div
        v-for="subtask in analysisTask.subtasks"
        :key="subtask.id"
        class="subtask-card"
      >
        <div class="subtask-header">
          <h3>Task {{ subtask.type }}</h3>
          <span
            class="status-badge"
            :class="subtask.status.toLowerCase()"
          >
            {{ formatStatus(subtask.status) }}
          </span>
        </div>
        <div class="subtask-body">
          <div
            v-if="subtask.status === 'DONE' && subtask.result"
            class="result"
          >
            <strong>Result:</strong> {{ subtask.result }}
          </div>
          <div
            v-else-if="subtask.status === 'CANCELED'"
            class="canceled"
          >
            ❌ Canceled
          </div>
          <div
            v-else-if="subtask.status === 'IN_PROGRESS'"
            class="in-progress"
          >
            <span class="spinner">⏳</span> Processing...
            <span v-if="subtask.queuePosition > 0" class="queue-info">
              ({{ subtask.queuePosition }} ahead)
            </span>
          </div>
          <div
            v-else
            class="queued"
          >
            Waiting in queue...
            <span v-if="subtask.queuePosition > 0" class="queue-info">
              ({{ subtask.queuePosition }} tasks ahead)
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  analysisTask: {
    type: Object,
    default: null,
  },
});

const formatStatus = (status) => {
  switch (status) {
    case "QUEUED":
      return "Queued";
    case "IN_PROGRESS":
      return "In Progress";
    case "DONE":
      return "Done";
    case "CANCELED":
      return "Canceled";
    default:
      return status;
  }
};
</script>

<style scoped>
.analysis-shelf {
  padding: 2rem;
  color: #e2e8f0;
}

.analysis-shelf h2 {
  color: #68d391;
  margin-bottom: 1.5rem;
  font-size: 1.8rem;
}

.loading {
  text-align: center;
  padding: 2rem;
  color: #a0aec0;
}

.subtasks-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.subtask-card {
  background-color: #1a202c;
  border: 1px solid #4a5568;
  border-radius: 8px;
  padding: 1rem;
  transition: all 0.2s ease;
}

.subtask-card:hover {
  border-color: #68d391;
}

.subtask-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.subtask-header h3 {
  margin: 0;
  color: #e2e8f0;
  font-size: 1.2rem;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.875rem;
  font-weight: 600;
}

.status-badge.queued {
  background-color: #4a5568;
  color: #e2e8f0;
}

.status-badge.in_progress {
  background-color: #3182ce;
  color: #e2e8f0;
  animation: pulse 2s ease-in-out infinite;
}

.status-badge.done {
  background-color: #48bb78;
  color: #1a202c;
}

.status-badge.canceled {
  background-color: #f56565;
  color: #1a202c;
}

.subtask-body {
  color: #a0aec0;
}

.canceled {
  color: #f56565;
}

.result {
  color: #e2e8f0;
  padding: 0.5rem;
  background-color: #2d3748;
  border-radius: 4px;
}

.in-progress {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.spinner {
  animation: spin 1s linear infinite;
}

.queue-info {
  font-size: 0.875rem;
  color: #a0aec0;
  font-style: italic;
}

@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.7;
  }
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>

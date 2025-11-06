<template>
  <div class="analysis-shelf">
    <Transition
      name="fade"
      mode="out-in"
    >
      <div
        v-if="!analysisTask || !hasStarted"
        key="loading"
        class="loading"
      >
        <div
          v-if="initError"
          class="error"
        >
          ❌ Failed to initialize analysis
        </div>
        <div
          v-else
          class="spinner-container"
        >
          <span class="spinner spinner-large">⏳</span>
          <div
            v-if="minQueuePosition > 0"
            class="overall-queue-info"
          >
            <span class="queue-badge">{{ minQueuePosition }} tasks ahead in queue</span>
          </div>
        </div>
      </div>
      <div
        v-else
        key="subtasks"
        class="subtasks-container"
      >
        <div
          v-for="subtask in analysisTask.subtasks"
          :key="subtask.id"
          class="subtask-box"
        >
          <Transition
            name="fade"
            mode="out-in"
          >
            <div
              v-if="subtask.status === 'DONE' && subtask.result"
              key="result"
              class="result-content"
            >
              {{ subtask.result }}
            </div>
            <div
              v-else
              key="spinner"
              class="spinner-content"
            >
              <span class="spinner">⏳</span>
            </div>
          </Transition>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  analysisTask: {
    type: Object,
    default: null,
  },
  initError: {
    type: Boolean,
    default: false,
  },
});

const minQueuePosition = computed(() => {
  if (!props.analysisTask || !props.analysisTask.subtasks) {
    return 0;
  }

  const positions = props.analysisTask.subtasks.map((st) => st.queuePosition || 0);

  return positions.length > 0 ? Math.min(...positions) : 0;
});

const hasStarted = computed(() => {
  if (!props.analysisTask || !props.analysisTask.subtasks) {
    return false;
  }

  return props.analysisTask.subtasks.some((st) => st.status !== "QUEUED");
});
</script>

<style scoped>
.analysis-shelf {
  padding: 2rem;
  height: 100%;
  color: #e2e8f0;
  display: flex;
  flex-direction: column;
}

.overall-queue-info {
  display: flex;
  align-items: center;
}

.queue-badge {
  background-color: #3182ce;
  color: #e2e8f0;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 600;
  display: inline-block;
}

.loading {
  text-align: center;
  padding: 2rem;
  color: #a0aec0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
}

.spinner-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.spinner-large {
  font-size: 3rem;
}

.error {
  color: #f56565;
  font-size: 1.2rem;
}

.subtasks-container {
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 1rem;
}

.subtask-box {
  flex: 1;
  min-height: 80px;
  background-color: #1a202c;
  border: 1px solid #4a5568;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
}

.spinner-content {
  display: flex;
  align-items: center;
  justify-content: center;
}

.result-content {
  color: #e2e8f0;
  text-align: center;
}

.spinner {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>

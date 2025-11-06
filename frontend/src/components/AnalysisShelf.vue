<template>
  <div class="analysis-shelf">
    <Transition
      name="fade"
      mode="out-in"
    >
      <div
        v-if="!analysisTask || !allDone"
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
            v-if="!hasStarted && minQueuePosition > 0"
            class="overall-queue-info"
          >
            <span class="queue-badge">{{ minQueuePosition }} tasks ahead in queue</span>
          </div>
          <div
            v-else
            class="progress-info"
          >
            <span class="progress-badge">{{ completedCount }}/{{ totalCount }} complete</span>
          </div>
        </div>
      </div>
      <div
        v-else
        key="results"
        class="results-container"
      >
        <div
          v-for="(subtask, index) in analysisTask.subtasks"
          :key="subtask.id"
          class="result-card"
          :class="{
            highlighting: index === highlightedIndex,
            revealed: index < revealedCount,
          }"
        >
          <div class="result-title">
            {{ subtask.description }}
          </div>
          <div class="result-score">
            {{ subtask.result }}
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { computed, ref, watch } from "vue";

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

const revealedCount = ref(0);
const highlightedIndex = ref(-1);

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

const allDone = computed(() => {
  if (!props.analysisTask || !props.analysisTask.subtasks) {
    return false;
  }

  return props.analysisTask.subtasks.every((st) => st.status === "DONE");
});

const completedCount = computed(() => {
  if (!props.analysisTask || !props.analysisTask.subtasks) {
    return 0;
  }

  return props.analysisTask.subtasks.filter((st) => st.status === "DONE").length;
});

const totalCount = computed(() => {
  if (!props.analysisTask || !props.analysisTask.subtasks) {
    return 0;
  }

  return props.analysisTask.subtasks.length;
});

// Sequential reveal when all done
watch(allDone, (isDone) => {
  if (isDone) {
    revealedCount.value = 0;
    highlightedIndex.value = -1;
    const total = props.analysisTask.subtasks.length;
    const interval = setInterval(() => {
      if (revealedCount.value < total) {
        revealedCount.value++;
        highlightedIndex.value = revealedCount.value - 1;
      } else {
        clearInterval(interval);
        highlightedIndex.value = -1;
      }
    }, 1500); // 800ms fade + 700ms hold
  }
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

.queue-badge,
.progress-badge {
  background-color: #3182ce;
  color: #e2e8f0;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.9rem;
  font-weight: 600;
  display: inline-block;
}

.progress-info {
  display: flex;
  align-items: center;
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

.results-container {
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 1rem;
}

.result-card {
  flex: 1;
  min-height: 80px;
  background-color: #1a202c;
  border: 2px solid #4a5568;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  opacity: 0;
  transition:
    opacity 0.8s ease,
    border-color 0.3s ease,
    transform 0.3s ease;
}

.result-card.revealed {
  opacity: 1;
}

.result-card.highlighting {
  border-color: #68d391;
  transform: scale(1.02);
}

.result-title {
  color: #a0aec0;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.result-score {
  color: #68d391;
  font-size: 2rem;
  font-weight: bold;
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

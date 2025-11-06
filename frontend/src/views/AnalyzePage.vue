<template>
  <div id="analyze-page">
    <header>
      <h1>{{ $t("analyze.title") }}</h1>
      <div class="header-controls">
        <LanguageSwitcher />
        <button
          class="back-button"
          @click="goBack"
        >
          {{ $t("analyze.backButton") }}
        </button>
      </div>
    </header>

    <main>
      <div
        v-if="isValid"
        class="coordinates-display"
      >
        <div class="left-or-top-container">
          <LayeredMap
            :lat="parseFloat(latitude)"
            :lng="parseFloat(longitude)"
          />
        </div>
        <div class="right-or-bottom-container">
          <AnalysisShelf
            :analysis-task="analysisTask"
            :init-error="initError"
          />
        </div>
      </div>

      <div
        v-else
        class="error-message"
      >
        <h2>{{ $t("analyze.invalidCoordinates") }}</h2>
        <p>{{ $t("analyze.invalidMessage") }}</p>
        <p>{{ $t("analyze.latitudeRange") }}</p>
        <p>{{ $t("analyze.longitudeRange") }}</p>
        <button
          class="back-button"
          @click="goBack"
        >
          {{ $t("analyze.backButton") }}
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter, useRoute } from "vue-router";
import LanguageSwitcher from "../components/LanguageSwitcher.vue";
import LayeredMap from "../components/LayeredMap.vue";
import AnalysisShelf from "../components/AnalysisShelf.vue";
import { validateCoordinates } from "../utils/coordinates.js";

export default {
  name: "AnalyzePage",
  components: {
    LanguageSwitcher,
    LayeredMap,
    AnalysisShelf,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const latitude = ref(null);
    const longitude = ref(null);
    const isValid = ref(false);
    const analysisTask = ref(null);
    const initError = ref(false);
    let pollInterval = null;

    const goBack = () => {
      router.push("/");
    };

    const createAnalysis = async (lat, lng) => {
      try {
        const response = await fetch("http://localhost:8080/api/analysis", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({ lat, lng }),
        });
        if (!response.ok) {
          throw new Error(`HTTP ${response.status}`);
        }
        const task = await response.json();
        analysisTask.value = task;
        startPolling(task.id);
      } catch (error) {
        console.error("Error creating analysis:", error);
        initError.value = true;
      }
    };

    const pollAnalysisStatus = async (taskId) => {
      try {
        const response = await fetch(`http://localhost:8080/api/analysis/${taskId}?includeResults=false`, {
          credentials: "include",
        });
        const task = await response.json();
        analysisTask.value = task;

        // Check if all subtasks are done or canceled
        const allDone = task.subtasks.every((st) => st.status === "DONE" || st.status === "CANCELED");
        if (allDone && pollInterval) {
          clearInterval(pollInterval);
          pollInterval = null;
          // Fetch full results now that all are done
          fetchFullResults(taskId);
        }
      } catch (error) {
        console.error("Error polling analysis:", error);
      }
    };

    const fetchFullResults = async (taskId) => {
      try {
        const response = await fetch(`http://localhost:8080/api/analysis/${taskId}?includeResults=true`, {
          credentials: "include",
        });
        const task = await response.json();
        analysisTask.value = task;
      } catch (error) {
        console.error("Error fetching results:", error);
      }
    };

    const startPolling = (taskId) => {
      pollInterval = setInterval(() => {
        pollAnalysisStatus(taskId);
      }, 1000);
    };

    onMounted(() => {
      const lat = route.query.lat;
      const lng = route.query.lng;

      if (validateCoordinates(lat, lng)) {
        latitude.value = parseFloat(lat).toFixed(6);
        longitude.value = parseFloat(lng).toFixed(6);
        isValid.value = true;
        createAnalysis(parseFloat(lat), parseFloat(lng));
      } else {
        isValid.value = false;
      }
    });

    onBeforeUnmount(() => {
      if (pollInterval) {
        clearInterval(pollInterval);
      }
    });

    return {
      latitude,
      longitude,
      isValid,
      analysisTask,
      initError,
      goBack,
    };
  },
};
</script>

<style scoped>
#analyze-page {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #e2e8f0;
  background-color: #1a202c;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

header {
  background-color: #2d3748;
  padding: 1rem 2rem;
  border-bottom: 1px solid #4a5568;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-controls {
  display: flex;
  align-items: center;
  gap: 1rem;
}

header h1 {
  color: #68d391;
  font-size: 2rem;
  margin: 0;
}

.back-button {
  background-color: rgb(58, 75, 105);
  color: #e2e8f0;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: rgb(90, 117, 173);
}

main {
  flex: 1;
  display: flex;
}

.coordinates-display {
  text-align: center;
  background-color: #2d3748;
  flex: 1;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: row;
}

@media (orientation: portrait) {
  .coordinates-display {
    flex-direction: column;
  }
}

.left-or-top-container,
.right-or-bottom-container {
  flex: 1;
}

.coordinates-display h2 {
  color: #68d391;
  margin-bottom: 1.5rem;
}

.coordinate-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  padding: 0.5rem 0;
  border-bottom: 1px solid #4a5568;
}

.coordinate-item:last-child {
  border-bottom: none;
}

.label {
  font-weight: 600;
  color: #a0aec0;
}

.value {
  color: #e2e8f0;
  font-family: monospace;
}

.error-message {
  text-align: center;
  background-color: #2d3748;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.error-message h2 {
  color: #f56565;
  margin-bottom: 1rem;
}

.error-message p {
  color: #a0aec0;
  margin-bottom: 0.5rem;
}
</style>

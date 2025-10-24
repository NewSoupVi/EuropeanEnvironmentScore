<template>
  <div id="home-page">
    <header>
      <h1>{{ $t("app.title") }}</h1>
      <LanguageSwitcher />
    </header>

    <main>
      <div class="instruction">
        <p>{{ $t("home.instruction") }}</p>
      </div>
      <div class="map-container">
        <EnvironmentMap @coordinates-changed="updateCoordinates" />
        <button
          class="button-primary radius-bottom"
          @click="navigateToAnalyze"
          :disabled="!coordinates.lat || !coordinates.lng"
        >
          {{ $t("home.analyzeButton") }}
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import EnvironmentMap from "../components/EnvironmentMap.vue";
import LanguageSwitcher from "../components/LanguageSwitcher.vue";

export default {
  name: "HomePage",
  components: {
    EnvironmentMap,
    LanguageSwitcher,
  },
  setup() {
    const router = useRouter();
    const coordinates = ref({ lat: 52.5208339, lng: 13.4089248 }); // Default to Berlin TV Tower

    const updateCoordinates = (lat, lng) => {
      coordinates.value = { lat, lng };
    };

    const navigateToAnalyze = () => {
      if (coordinates.value.lat && coordinates.value.lng) {
        router.push({
          path: "/analyze",
          query: {
            lat: coordinates.value.lat,
            lng: coordinates.value.lng,
          },
        });
      }
    };

    return {
      coordinates,
      updateCoordinates,
      navigateToAnalyze,
    };
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  height: 100%;
  overflow: hidden;
}

#home-page {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #e2e8f0;
  background-color: #1a202c;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

header {
  background-color: #2d3748;
  padding: 1rem 2rem;
  border-bottom: 1px solid #4a5568;
  flex-shrink: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

header h1 {
  color: #68d391;
  font-size: 2rem;
  margin: 0;
}

main {
  flex: 1;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.instruction {
  text-align: center;
  margin-bottom: 1rem;
}

.instruction p {
  color: #e2e8f0;
  font-size: 1.3rem;
  margin: 0;
}

.map-container {
  width: 100vh;
  height: 100%;
  max-width: 90vw;
  display: flex;
  flex-direction: column;
}

.radius-bottom {
  border-radius: 0 0 8px 8px;
}
</style>

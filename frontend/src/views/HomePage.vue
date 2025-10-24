<template>
  <div id="home-page">
    <header>
      <h1>Environment Score</h1>
    </header>
    
    <main>
      <div class="instruction">
        <p>Choose a location to analyze!</p>
      </div>
      <div class="map-container">
        <EnvironmentMap @coordinates-changed="updateCoordinates" />
        <button 
          class="analyze-button" 
          @click="navigateToAnalyze"
          :disabled="!coordinates.lat || !coordinates.lng"
        >
          Analyze this location
        </button>
      </div>
    </main>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import EnvironmentMap from '../components/EnvironmentMap.vue'

export default {
  name: 'HomePage',
  components: {
    EnvironmentMap
  },
  setup() {
    const router = useRouter()
    const coordinates = ref({ lat: 52.5208339, lng: 13.4089248 }) // Default to Berlin TV Tower

    const updateCoordinates = (lat, lng) => {
      coordinates.value = { lat, lng }
    }

    const navigateToAnalyze = () => {
      if (coordinates.value.lat && coordinates.value.lng) {
        router.push({
          path: '/analyze',
          query: {
            lat: coordinates.value.lat,
            lng: coordinates.value.lng
          }
        })
      }
    }

    return {
      coordinates,
      updateCoordinates,
      navigateToAnalyze
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
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

.analyze-button {
  width: 100%;
  padding: 1rem 2rem;
  background-color: rgb(58, 75, 105);
  color: #e2e8f0;
  border: none;
  border-radius: 0 0 8px 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
  flex-shrink: 0;
}

.analyze-button:hover:not(:disabled) {
  background-color: rgb(90, 117, 173);
}

.analyze-button:disabled {
  background-color: #4a5568;
  cursor: not-allowed;
  opacity: 0.6;
}
</style>

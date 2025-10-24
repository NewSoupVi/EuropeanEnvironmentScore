<template>
  <div id="analyze-page">
    <header>
      <h1>{{ $t('analyze.title') }}</h1>
      <div class="header-controls">
        <LanguageSwitcher />
        <button @click="goBack" class="back-button">{{ $t('analyze.backButton') }}</button>
      </div>
    </header>
    
    <main>
      <div v-if="isValid" class="coordinates-display">
        <h2>{{ $t('analyze.selectedLocation') }}</h2>
        <div class="coordinate-item">
          <span class="label">{{ $t('analyze.latitude') }}</span>
          <span class="value">{{ latitude }}</span>
        </div>
        <div class="coordinate-item">
          <span class="label">{{ $t('analyze.longitude') }}</span>
          <span class="value">{{ longitude }}</span>
        </div>
      </div>
      
      <div v-else class="error-message">
        <h2>{{ $t('analyze.invalidCoordinates') }}</h2>
        <p>{{ $t('analyze.invalidMessage') }}</p>
        <p>{{ $t('analyze.latitudeRange') }}</p>
        <p>{{ $t('analyze.longitudeRange') }}</p>
        <button @click="goBack" class="back-button">{{ $t('analyze.backButton') }}</button>
      </div>
    </main>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import LanguageSwitcher from '../components/LanguageSwitcher.vue'

export default {
  name: 'AnalyzePage',
  components: {
    LanguageSwitcher
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const latitude = ref(null)
    const longitude = ref(null)
    const isValid = ref(false)

    const validateCoordinates = (lat, lng) => {
      const latNum = parseFloat(lat)
      const lngNum = parseFloat(lng)
      
      return !isNaN(latNum) && !isNaN(lngNum) && 
             latNum >= -90 && latNum <= 90 && 
             lngNum >= -180 && lngNum <= 180
    }

    const goBack = () => {
      router.push('/')
    }

    onMounted(() => {
      const lat = route.query.lat
      const lng = route.query.lng
      
      if (lat && lng && validateCoordinates(lat, lng)) {
        latitude.value = parseFloat(lat).toFixed(6)
        longitude.value = parseFloat(lng).toFixed(6)
        isValid.value = true
      } else {
        isValid.value = false
      }
    })

    return {
      latitude,
      longitude,
      isValid,
      goBack
    }
  }
}
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
  padding: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.coordinates-display {
  text-align: center;
  background-color: #2d3748;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
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

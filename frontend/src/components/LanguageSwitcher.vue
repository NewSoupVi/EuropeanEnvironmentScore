<template>
  <div class="language-switcher">
    <button 
      @click="switchLanguage('en')" 
      :class="{ active: currentLocale === 'en' }"
      class="lang-button"
    >
      EN
    </button>
    <button 
      @click="switchLanguage('de')" 
      :class="{ active: currentLocale === 'de' }"
      class="lang-button"
    >
      DE
    </button>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'

export default {
  name: 'LanguageSwitcher',
  setup() {
    const { locale } = useI18n()
    
    const currentLocale = computed(() => locale.value)
    
    const switchLanguage = (lang) => {
      locale.value = lang
      // Store preference in localStorage (only in browser)
      if (typeof window !== 'undefined') {
        localStorage.setItem('preferred-language', lang)
      }
    }
    
    return {
      currentLocale,
      switchLanguage
    }
  }
}
</script>

<style scoped>
.language-switcher {
  display: flex;
  gap: 0.5rem;
}

.lang-button {
  background-color: transparent;
  color: #a0aec0;
  border: 1px solid #4a5568;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.875rem;
  transition: all 0.2s ease;
}

.lang-button:hover {
  background-color: #4a5568;
  color: #e2e8f0;
}

.lang-button.active {
  background-color: #68d391;
  color: #1a202c;
  border-color: #68d391;
}

.lang-button.active:hover {
  background-color: #5bbf7d;
}
</style>

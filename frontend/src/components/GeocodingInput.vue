<template>
  <div class="geocoding-input-wrapper">
    <div class="geocoding-input-container">
      <input
        id="geocoding-input"
        ref="inputElement"
        v-model="locationInput"
        :placeholder="$t('geocode.search')"
        type="text"
        @focus="inputIsFocused = true"
        @blur="hideSuggestionsIfSuggestionWasntClicked()"
      >
      <button
        id="locationSearchSubmit"
        class="submit-button button-primary"
        :disabled="isSearching"
        @click="searchLocationDirect()"
      >
        {{ $t("geocode.search") }}
      </button>
    </div>
    <div
      v-if="suggestions.length > 0"
      :hidden="!inputIsFocused"
      class="suggestions-dropdown"
    >
      <div
        v-for="(suggestion, index) in suggestions"
        :key="index"
        class="suggestion-item"
        @click="selectSuggestion(suggestion)"
      >
        {{ suggestion.display_name }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const emit = defineEmits(["location-found"]);

const locationInput = defineModel("locationInput", { type: String, default: "" });
const isSearching = ref(false);
const suggestions = ref([]);
const queryInProgress = ref(false);
const debounceTimer = ref(null);
const userTypingReady = ref(false);
const lastQueriedText = ref("");
const inputElement = ref(null);
const inputIsFocused = ref(false);

const searchLocationDirect = async () => {
  if (!locationInput.value.trim() || isSearching.value) return;

  isSearching.value = true;
  try {
    const response = await fetch(
      `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(locationInput.value)}&format=json`,
    );
    const data = await response.json();

    if (data.length > 0) {
      const result = data[0];
      emit("location-found", parseFloat(result.lat), parseFloat(result.lon), result.display_name);
    }
  } catch (error) {
    console.error("Geocoding error:", error);
  } finally {
    isSearching.value = false;
  }
};

const hideSuggestionsIfSuggestionWasntClicked = (e) => {
  if (!e.target.closest(".suggestion-item")) {
    inputIsFocused.value = false;
  }
}; 

const fetchSuggestions = async (query) => {
  if (!query.trim() || queryInProgress.value) return;

  console.log("Fetching suggestions");

  queryInProgress.value = true;
  lastQueriedText.value = query;

  try {
    const response = await fetch(
      `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(query)}&format=json&limit=5`,
    );
    const data = await response.json();
    console.log(data);
    suggestions.value = data;
  } catch (error) {
    console.error("Suggestion fetch error:", error);
  } finally {
    queryInProgress.value = false;
    checkAndFetchSuggestions();
  }
};

const checkAndFetchSuggestions = () => {
  if (
    userTypingReady.value &&
    !queryInProgress.value &&
    locationInput.value.trim() &&
    locationInput.value !== lastQueriedText.value
  ) {
    fetchSuggestions(locationInput.value);
  }
};

const selectSuggestion = (suggestion) => {
  emit("location-found", parseFloat(suggestion.lat), parseFloat(suggestion.lon), suggestion.display_name);
  if (inputElement.value) {
    inputElement.value.blur();
    inputIsFocused.value = false;
  }
};

watch(locationInput, () => {
  userTypingReady.value = false;

  if (debounceTimer.value) {
    clearTimeout(debounceTimer.value);
  }

  debounceTimer.value = setTimeout(() => {
    userTypingReady.value = true;
    checkAndFetchSuggestions();
  }, 600);
});
</script>

<style scoped>
.geocoding-input-wrapper {
  position: relative;
}

.geocoding-input-container {
  display: flex;
}

input {
  font-size: 1rem;
  padding: 0.25rem;
  flex: 1;
  border-radius: 8px 0 0 0;
  border: 0;
  background-color: rgb(235, 235, 235);
  transition: all 0.2s ease;
}

input:hover {
  background-color: rgb(245, 245, 245);
}

input:focus {
  outline: none;
  background-color: rgb(250, 250, 250);
  transition: all 0.1s ease;
}

.submit-button {
  width: fit-content;
  border-radius: 0 8px 0 0;
}

.suggestions-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: rgb(245, 245, 245);
  border: 1px solid rgb(200, 200, 200);
  color: black;
  border-top: none;
  max-height: 200px;
  overflow-y: auto;
  z-index: 1000;
}

.suggestion-item {
  padding: 0.5rem;
  cursor: pointer;
  border-bottom: 1px solid rgb(230, 230, 230);
}

.suggestion-item:hover {
  background-color: rgb(235, 235, 235);
}

.suggestion-item:last-child {
  border-bottom: none;
}
</style>

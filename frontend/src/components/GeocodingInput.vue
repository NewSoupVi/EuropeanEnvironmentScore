<template>
  <div class="geocoding-input-container">
    <input
      id="geocoding-input"
      v-model="locationInput"
      :placeholder="$t('geocode.search')"
      type="text"
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
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["location-found"]);

const locationInput = defineModel("locationInput", { type: String, default: "" });
const isSearching = ref(false);

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
</script>

<style scoped>
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
</style>

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
      @click="searchLocation()"
    >
      {{ $t("geocode.search") }}
    </button>
  </div>
</template>

<script setup>
const locationInput = defineModel("locationInput", { type: String, default: "" });

const searchLocation = () => {
  console.log(locationInput.value);
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

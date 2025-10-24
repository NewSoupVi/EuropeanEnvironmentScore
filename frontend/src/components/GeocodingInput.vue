<template>
  <div class="geocoding-input-container">
    <input id="geocoding-input" :placeholder="$t('geocode.search')" v-model="locationInput" type="text" />
    <button class="submit-button button-primary" @click="searchLocation()" id="locationSearchSubmit">
      {{ $t("geocode.search") }}
    </button>
  </div>
</template>

<script setup>
const locationInput = defineModel({ default: "" });
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
  background-color: rgb(220, 220, 220);
  transition: all 0.2s ease;
}

input:hover {
  background-color: rgb(230, 230, 230);
}

input:focus {
  outline: none;
  background-color: rgb(240, 240, 240);
}

.submit-button {
  width: fit-content;
  border-radius: 0 8px 0 0;
}
</style>

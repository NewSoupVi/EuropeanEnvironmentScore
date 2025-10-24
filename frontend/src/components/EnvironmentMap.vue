<template>
  <div id="map" ref="mapContainer"></div>
</template>

<script>
import { ref, onMounted, onUnmounted, watch } from "vue";
import { useI18n } from "vue-i18n";
import L from "leaflet";

export default {
  name: "EnvironmentMap",
  emits: ["coordinates-changed"],
  setup(props, { emit }) {
    const { t, locale } = useI18n();
    const mapContainer = ref(null);
    let map = null;
    let marker = null;
    let isInitialMarker = true; // Track if we're still showing the initial Berlin TV Tower marker

    onMounted(() => {
      if (mapContainer.value) {
        // Initialize the map
        map = L.map(mapContainer.value).setView([52.5208339, 13.4089248], 12);

        // Add the tile layer
        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
          attribution: "© OpenStreetMap contributors",
        }).addTo(map);

        // Create initial marker at Berlin TV Tower
        marker = L.marker([52.5208339, 13.4089248]).addTo(map);
        marker.bindPopup(t("map.berlinTower")).openPopup();

        // Emit initial coordinates
        emit("coordinates-changed", 52.5208339, 13.4089248);

        // Add click event listener to the map
        map.on("click", (e) => {
          const { lat, lng } = e.latlng;

          // Move existing marker to new position
          marker.setLatLng([lat, lng]);

          // Remove popup when user selects their own location
          marker.unbindPopup();
          isInitialMarker = false;

          // Emit new coordinates
          emit("coordinates-changed", lat, lng);
        });
      }
    });

    // Watch for language changes and update popup if still showing initial marker
    watch(locale, () => {
      if (marker && isInitialMarker) {
        // Update the popup text with the new translation
        marker.bindPopup(t("map.berlinTower")).openPopup();
      }
    });

    onUnmounted(() => {
      if (map) {
        map.remove();
      }
    });

    return {
      mapContainer,
    };
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%;
  border-radius: 8px 8px 0 0;
  cursor: pointer;
  flex: 1;
}

#map:hover {
  cursor: pointer;
}

#map:active {
  cursor: grabbing;
}
</style>

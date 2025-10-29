<template>
  <div id="layered-map">
    <div id="map-container-outer">
      <div
        id="map-container-inner"
        ref="mapContainer"
        class="square-div"
      />
    </div>
    <div id="explanation" />
  </div>
</template>

<script setup>
import L from "leaflet";
import { ref, onMounted } from "vue";
const mapContainer = ref(null);
let map = null;

onMounted(() => {
  if (mapContainer.value) {
    // Initialize the map
    map = L.map(mapContainer.value).setView([52.5208339, 13.4089248], 15);

    // Add the tile layer
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution: "© OpenStreetMap contributors",
    }).addTo(map);
  }
});
</script>

<style scoped>
#layered-map {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

#map-container-outer {
  flex: 1;
  container-type: size;
  display: flex;
  justify-content: center;
  align-items: center;
}

.square-div {
  width: min(100cqw, 100cqh);
  aspect-ratio: 1;
}

#explanation {
  flex: 0.25;
}

@media (orientation: portrait) {
  #layered-map {
    flex-direction: row;
  }
}
</style>

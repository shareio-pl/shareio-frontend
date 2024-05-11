<template>
  <div class="map">
    <l-map :zoom="zoom" :center="center"
      :options="{ zoomControl: false, scrollWheelZoom: false, dragging: false, doubleClickZoom: false }" ref="mapRef">
      <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
      <l-marker :lat-lng="center"></l-marker>
    </l-map>
  </div>
</template>

<script>
import "leaflet/dist/leaflet.css"
import { LMap, LMarker, LTileLayer } from "@vue-leaflet/vue-leaflet";

export default {
  name: "MapPreview",
  components: {
    LMarker,
    LTileLayer,
    LMap,
  },
  props: {
    zoom: {
      type: Number,
      required: true,
    },
    center: {
      type: Array,
      required: true,
    },
    url: {
      type: String,
      required: true,
    },
    attribution: {
      type: String,
      required: true,
    },
  },
};
</script>

<style>
.map {
  height: 100%;
  width: 100%;
  overflow: hidden;
}
</style>
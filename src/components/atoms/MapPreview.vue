<template>
  <div class="map" v-if="coordinates">
    <l-map :zoom="zoom" :center="coordinates"
      :options="{ zoomControl: false, scrollWheelZoom: false, dragging: false, doubleClickZoom: false }" ref="mapRef"
      style="z-index: 0;">
      <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
      <l-marker :lat-lng="coordinates"></l-marker>
    </l-map>
  </div>
</template>

<script>
import "leaflet/dist/leaflet.css";
import axios from "axios";
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
      required: false,
    },
    address: {
      type: String,
      required: false,
    },
  },
  data() {
    return {
      coordinates: '',
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a target="_blank" href="https://osm.org/copyright">OpenStreetMap</a> contributors',
    };
  },
  methods: {
    async updateCenterByLocation(newAddress) {
      const response = await axios.get(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(newAddress)}`);
      if (response.data[0]) {
        this.coordinates = [response.data[0].lat, response.data[0].lon];
      }
    },
  },
  watch: {
    async address(newAddress) {
      await this.updateCenterByLocation(newAddress);
    },
  },
  async mounted() {
    if (this.address) {
      await this.updateCenterByLocation(this.address);
    }
    if (this.center) {
      this.coordinates = this.center;
    }
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
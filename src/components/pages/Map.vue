<template>
  <div id="map-page">
    <Header id="header"/>
    <ButtonPrimary button-text="Znajdź przedmioty blisko Ciebie!" id="button" @click="centerMap"/>
    <div id="map-initial" v-if="isFirstTime">
      <font-awesome-icon :icon="iconArrowUp" id="arrow-icon"/>
      <p>Kliknij, aby wycentrować mapę na Twojej lokalizacji </p>
    </div>
    <div id="map-container">
      <div
          class="map"
      >
        <l-map
            :zoom="zoom"
            :center="center"
        >
          <l-tile-layer
              :url="url"
              :attribution="attribution"
          ></l-tile-layer>
          <l-marker :lat-lng="markerLatLng"></l-marker>
        </l-map>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import {DEFAULT_OFFER_MAP_IMAGE, COLORS, FONT_SIZES} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faArrowUp} from "@fortawesome/free-solid-svg-icons";
import "leaflet/dist/leaflet.css"
import {LMap, LMarker, LTileLayer} from "@vue-leaflet/vue-leaflet";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Map",
  components: {
    LMarker,
    LTileLayer,
    LMap,
    FontAwesomeIcon, ButtonPrimary, Header
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      mapImage: DEFAULT_OFFER_MAP_IMAGE,
      isFirstTime: true,
      iconArrowUp: faArrowUp,
      userId: '',
      isMapReady: false,
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
          '&copy; <a target="_blank" href="https://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 5,
      center: [51.7467613, 19.4530878],
      markerLatLng: [51.7467613, 19.4530878],
    }
  },
  methods:
      {
        centerMap() {
          console.log('Map has been centred');
          this.isFirstTime = false;
          //TODO: add implementation
        },
      },
  mounted() {
    // axios.get(GATEWAY_ADDRESS + '/debug/createUser').then((response) => {
    //   console.log(response.data.id);
    //   this.userId = response.data.id;
    // });
  },
}
</script>

<style scoped>
#map-page {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#button {
  width: 40%;
  height: 70px;
  z-index: 3;
  font-size: 250%;
  margin-right: 2%;
}

#header {
  z-index: 3;
}

#map-container {
  position: fixed;
  width: 100%;
}

#map-initial {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  z-index: 2;
}

p {
  font-size: v-bind('FONT_SIZES.TITLE');
  background-color: v-bind('COLORS.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  width: 30%;
  margin-top: 5%;
  border-radius: 15px;
  z-index: 2;
}

#arrow-icon {
  scale: 500%;
  margin-top: -15%;
  z-index: 2;
}

.map {
  height: 100vh;
  z-index: 1;
}
</style>

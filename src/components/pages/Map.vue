<template>
  <div id="map-page">
    <Header id="header" />
    <ButtonPrimary button-text="Znajdź przedmioty blisko Ciebie!" id="button" @click="centerMap(userId)" />
    <div id="map-initial" v-if="isFirstTime">
      <font-awesome-icon :icon="iconArrowUp" id="arrow-icon" />
      <p>Kliknij, aby wycentrować mapę na Twojej lokalizacji </p>
    </div>
    <div id="map-container">
      <div class="map" v-if="markersAreLoaded">
        <l-map :zoom="zoom" :center="center" :min-zoom="minZoom" ref="mapRef">
          <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
          <div v-if="markersAreLoaded">
            <l-marker v-for="(marker, index) in markers" :key="index" :lat-lng="marker.location">
              <l-popup>
                <div v-for="(offer, index) in marker.offers" :key="index">
                  <span class="map-offer-preview">
                    <router-link :to="`/offer/${offer.id}`">{{ offer.name }}</router-link>
                  </span>
                </div>
              </l-popup>
            </l-marker>
          </div>
        </l-map>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import { COLORS, FONTS, FONT_SIZES, GATEWAY_ADDRESS } from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faArrowUp } from "@fortawesome/free-solid-svg-icons";
import "leaflet/dist/leaflet.css"
import { LMap, LMarker, LTileLayer, LPopup } from "@vue-leaflet/vue-leaflet";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Map",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    LMarker,
    LTileLayer,
    LMap,
    LPopup,
    FontAwesomeIcon, ButtonPrimary, Header
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      FONTS: FONTS,
      iconArrowUp: faArrowUp,
      isFirstTime: true,
      userId: '',
      url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution:
        '&copy; <a target="_blank" href="https://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 6,
      minZoom: 6, // 7 zawiera całą Polskę, ale nie na każdej rozdzielczości
      center: [52.066667, 19.466667], // Środek Polski; wieś Piątek
      offersIds: [],
      markerLatLng: [],
      markersAreLoaded: false
    }
  },
  computed: {
    markers() {
      return Object.entries(this.markerLatLng).map(([locationKey, offers]) => {
        const [latitude, longitude] = locationKey.split(',').map(Number);
        return {
          location: [latitude, longitude],
          offers
        };
      });
    }
  },
  methods:
  {
    async createUser() {
      const response = await axios.get(GATEWAY_ADDRESS + '/debug/createUser');
      this.userId = response.data.id;
    },
    async getOfferIds() {
      const response = await axios.get(GATEWAY_ADDRESS + '/debug/getOfferIds');
      this.offersIds = response.data.offerIds;
    },
    async getOffer(offerId) {
      const response = await axios.get(GATEWAY_ADDRESS + `/offer/get/${offerId}`);
      const locationKey = `${response.data.latitude},${response.data.longitude}`;
      if (!this.markerLatLng[locationKey]) {
        this.markerLatLng[locationKey] = [];
      }
      this.markerLatLng[locationKey].push({
        id: response.data.offerId,
        name: response.data.title
      });
    },
    centerMap(id) {
      console.log('Map has been centred');
      this.isFirstTime = false;

      axios.get(GATEWAY_ADDRESS + `/user/get/${id}`).then((response) => {
        console.log('User data', response.data);
        axios.get(GATEWAY_ADDRESS + `/address/location/get/${response.data.address.id}`)
          .then((response) => {
            console.log('Address data: ', response.data);

            this.zoom = 15;
            this.center = [response.data.latitude, response.data.longitude];
            this.$refs.mapRef.leafletObject.setView(this.center, this.zoom);
          });
      });
    },
  },
  async mounted() {
    await this.createUser();
    await this.getOfferIds();

    const offerPromises = this.offersIds.map(offerId => this.getOffer(offerId));
    await Promise.all(offerPromises);

    console.log('Markers: ', this.markerLatLng);
    this.markersAreLoaded = true;

    console.log("Computed markers: ", this.markers);
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
  margin-top: -5%;
  z-index: 2;
}

.map {
  height: 100vh;
  z-index: 1;
}

.map-offer-preview {
  cursor: pointer;
}

.map>>>.leaflet-popup-content-wrapper {
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  font-family: v-bind('FONTS.PRIMARY');
  /* It's necessary to use it here even if it's definied as main font for our webpage. */
}

.map>>>a {
  color: v-bind('COLORS.TEXT_SECONDARY');
}
</style>
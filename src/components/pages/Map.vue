<template>
  <div id="map-page">
    <Header/>
    <ButtonPrimary button-text="Znajdź przedmioty blisko Ciebie!" id="button" @click="centerMap"/>
    <img :src="mapImage" alt="map">
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import {DEFAULT_OFFER_MAP_IMAGE, FONTS} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Map",
  components: {ButtonPrimary, Header},
  data() {
    return {
      mapImage: DEFAULT_OFFER_MAP_IMAGE,
      FONTS: FONTS,
    }
  },
  methods:
      {
        centerMap(){
          console.log('Map has been centred');
          //TODO: add implementation
        }
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
  z-index: 2;
  font-size: 250%;
}

img {
  width: 100%;
  object-fit: cover;
  margin-top: -5%;
  z-index: 1;
}
</style>

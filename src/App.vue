<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <Header/>
    <OffersList :offers="offer"/>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import OffersList from "@/components/OffersList.vue";
import {FONTS} from "../public/Consts";

export default {
  name: "App",
  components: {
    Header,
    OffersList,
  },
  data() {
    return {
      offer: [
        {
          name: "Offer Name",
          address: "Offer Address",
          image:
              "https://i.gremicdn.pl/image/free/e16abd8fea3f4a1eba325b6a4262283e/?t=crop:1200:744:nowe:0:28,resize:fill:408:255,enlarge:1",
          numRatings: 12,
          rating: 5,
        },
        {
          name: "Offer Second",
          address: "Offer Address",
          image:
              "https://i.gremicdn.pl/image/free/e16abd8fea3f4a1eba325b6a4262283e/?t=crop:1200:744:nowe:0:28,resize:fill:408:255,enlarge:1",
          numRatings: 4,
          rating: 2.5,
        },
      ],
    };
  },
  computed: {
    FONTS() {
      return FONTS;
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>

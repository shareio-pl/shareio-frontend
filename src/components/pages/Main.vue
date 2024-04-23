<template>
  <div id="main-page">
    <Header/>
    <div id="closest-offer">
      <p>OFERTA NAJBLIŻEJ CIEBIE</p>
      <Offer id="a85fa0a7-0640-4871-a1cf-131e420bea50" />
    </div>
    <h1>Najnowsze w twojej okolicy</h1>
    <div id="newest-offers">
      <OfferPreview v-for="offer in numberOfOffers" :key="offer.id" :id=offer.id :is-new="true"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import Offer from "@/components/organisms/Offer.vue";
import OfferPreview from "@/components/organisms/OfferPreview.vue";
import {COLORS, FONT_SIZES} from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Main",
  components: {OfferPreview, Offer, Header},
  data() {
    return {
      numberOfOffers: 3, // change to offers: [],
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  }
}
</script>

<style scoped>
#main-page {
  background: linear-gradient(to bottom, #F0FBFF, #0A2635); /* light-blue to COLORS.BUTTON_PRIMARY_NORMAL
  /*background-color: v-bind('COLORS.SECONDARY');*/
  display: flex;
  flex-direction: column;
  height: 100%;
}

#closest-offer {
  margin-top: 3%;
}

p {
  background-color: v-bind('COLORS.BUTTON_PRIMARY_NORMAL');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: 18%;
  margin: -1% 5%;
  border-radius: 25px;
  padding: 10px;
  z-index: 2;
  position: absolute;
}

Offer {
  position: relative;
  z-index: 1;
}

h1 {
  margin-top: 1%;
  color: v-bind('COLORS.TEXT_PRIMARY');
}

OfferPreview {
  position: absolute;
  margin-top: 100px;
}
</style>

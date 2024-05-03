<template>
  <div id="main-page">
    <Header/>
    <div id="closest-offer" v-if="closestOffer">
      <p>OFERTA NAJBLIŻEJ CIEBIE</p>
      <Offer :id="closestOffer"/>
    </div>
    <h1>Najnowsze w Twojej okolicy</h1>
    <div id="newest-offers">
      <OfferPreview v-for="id in offersIds" :key="id" :id=id :is-new="true"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import Offer from "@/components/organisms/Offer.vue";
import OfferPreview from "@/components/organisms/OfferPreview.vue";
import {COLORS, FONT_SIZES, GATEWAY_ADDRESS} from "../../../public/Consts";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Main",
  components: {OfferPreview, Offer, Header},
  data() {
    return {
      offersIds: [],
      closestOffer: '',
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  },
  mounted() {
    axios.get(GATEWAY_ADDRESS + '/debug/getOfferIds').then((response) => {
      console.log('Offers: ', response.data.offerIds);

      this.offersIds = response.data.offerIds;
      this.closestOffer = this.offersIds[1];
    }).then(async () => {
      await console.log('Closest offer: ', this.offersIds[1]);

      if (this.closestOffer !== this.offersIds[1]) {
        console.log('Inside if');
        this.closestOffer = this.offersIds[1];
      }
    }).catch(error => {
      console.error('ERROR: ', error);

      this.emitter.emit('axiosError', {error: error.response.status});
    });
  }
}
</script>

<style scoped>
#main-page {
  background: linear-gradient(to bottom, #F0FBFF, #0A2635);
  /* light-blue to COLORS.BUTTON_PRIMARY_NORMAL
   /*background-color: v-bind('COLORS.SECONDARY');*/
  display: flex;
  flex-direction: column;
  height: 100%;
}

#closest-offer {
  margin-top: 2%;
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

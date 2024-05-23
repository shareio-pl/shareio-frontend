<template>
  <div id="main-page">
    <Header/>
    <div id="closest-offer" v-if="closestOffer">
      <p>OFERTA NAJBLIŻEJ CIEBIE</p>
      <Offer :id="closestOffer"/>
    </div>
    <h1>Najnowsze w Twojej okolicy</h1>
    <div id="newest-offers">
      <span v-for="pair in offerPairs" :key="pair">
        <OfferPreview :is-new="true" :id="pair[0]"/>
        <OfferPreview :is-new="true" :id="pair[1]"/>
      </span>
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
      offerPairs: [],
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
      this.closestOffer = this.offersIds[1]; //TODO: Change to endpoint call

      const closestOfferIndex = this.offersIds.indexOf(this.closestOffer);
      if (closestOfferIndex > -1) {
        this.offersIds.splice(closestOfferIndex, 1);
      }

      let numberOfOffers;
      if (this.offersIds.length % 2 === 0) {
        numberOfOffers = this.offersIds.length;
      } 
      else {
        numberOfOffers = this.offersIds.length - 1;
      }

      for (let offerId = 0; offerId < numberOfOffers; offerId += 2) {
        this.offerPairs.push([this.offersIds[offerId], this.offersIds[offerId + 1]]);
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
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
  display: flex;
  flex-direction: column;
  height: 100%;
}

#closest-offer {
  margin-top: 2%;
}

p {
  background-color: v-bind('COLORS.NOTIFICATION_PRIMARY');
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

#newest-offers {
  display: flex;
  flex-direction: column;
}

span{
  display: flex;
  justify-content: center;
}
</style>

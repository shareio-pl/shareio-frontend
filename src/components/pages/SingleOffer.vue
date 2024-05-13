<template>
  <div id="singleoffer-page">
    <Header/>
    <div id="single-offer" v-if="singleOffer">
      <Offer :id="singleOffer"/>
    </div>
    <h1>Inne oferty oddającego</h1>
    <div id="other-offers">
      <OfferPreview v-for="id in ownerOffers" :key="id" :id=id />
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
  name: "SingleOffer",
  components: {OfferPreview, Offer, Header},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      ownerOffers: [],
      offersIds: [],
      singleOffer: '',
    }
  },
  // TODO change to getOffersByUserId endpoint
  mounted() {
    axios.get(GATEWAY_ADDRESS + '/debug/getOfferIds').then((response) => {
      console.log('Offers: ', response.data.offerIds);

      this.offersIds = response.data.offerIds;
      this.singleOffer = this.$route.params.id;

      for (let offerId = 0; offerId < this.offersIds.length; offerId++) {
        if (this.singleOffer.ownerId === this.offersIds[offerId].ownerId
            && this.singleOffer !== this.offersIds[offerId])
          this.ownerOffers.push(this.offersIds[offerId]);
      }
    }).catch(error => {
      console.error('ERROR: ', error);

      this.emitter.emit('axiosError', {error: error.response.status});
    });
  }
}
</script>

<style scoped>
#singleoffer-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
  display: flex;
  flex-direction: column;
  height: 100%;
}

#single-offer {
  margin-top: 2%;
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

#other-offers {
  display: flex;
  flex-direction: column;
  align-items: center;

}

</style>
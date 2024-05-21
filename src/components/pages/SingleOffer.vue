<template>
  <div id="singleoffer-page">
    <Header/>
    <div id="single-offer" v-if="singleOffer">
      <Offer :id="singleOffer"/>
    </div>
    <h1>Inne oferty oddającego</h1>
    <div id="other-offers">
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
  name: "SingleOffer",
  components: {OfferPreview, Offer, Header},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      offerPairs: [],
      offersIds: [],
      singleOffer: this.$route.params.id,
      offerOwnerId: ''

    }
  },
  methods: {
    async getOwnerOffers() {
      try {
        let response = await axios.get(GATEWAY_ADDRESS + `/offer/get/${this.singleOffer}`);
        console.log(response);
        this.offerOwnerId = response.data.ownerId;
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', {error: error.response.status});
      }
      axios.get(GATEWAY_ADDRESS + `/offer/getOffersByUser/${this.offerOwnerId}`).then((response) => {
        console.log('Other Owner Offers: ', response.data.offerIds);
        this.offersIds = response.data.offerIds.filter((offerId) => offerId !== this.singleOffer);
        let numberOfOffers;
        if (this.offersIds.length % 2 === 0) {
          numberOfOffers = this.offersIds.length;
        } else {
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
  },
  mounted() {
    this.getOwnerOffers();
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
}

span {
  display: flex;
  justify-content: center;
}
</style>
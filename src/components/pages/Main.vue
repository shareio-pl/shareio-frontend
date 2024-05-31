<template>
  <div id="main-page">
    <Header/>
    <div id="closest-offer" v-if="closestOffer">
      <p v-if="token">OFERTA NAJBLIŻEJ CIEBIE</p>
      <p v-else>REKOMENDOWANA OFERTA</p>
      <Offer :id="closestOffer"/>
    </div>
    <h1 v-if="token">Najnowsze w Twojej okolicy</h1>
    <h1 v-else>Najnowsze oferty</h1>
    <div id="newest-offers">
      <span v-for="pair in offerPairs" :key="pair">
        <OfferPreview :is-new="true" :id="pair[0]" class="bigPreview"/>
        <OfferPreview :is-new="true" :id="pair[1]" class="bigPreview"/>
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
      token: '',
    }
  },
  methods: {
    getClosestOffer() {
      axios.get(GATEWAY_ADDRESS + `/offer/getClosestOfferForUser/${localStorage.getItem('userId')}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.token
        }
      }).then((response) => {
        console.log('Closest offer id: ', response.data);
        this.closestOffer = response.data;
      }).catch((error) => {
        console.error('ERROR: ', error);

        this.emitter.emit('axiosError', {error: error.response.status});
      });
    },
    getNewestOffers() { //TODO: check if it works after backend fix
      axios.get(GATEWAY_ADDRESS + '/offer/getNewest').then((response) => {
        console.log('Newest offers: ', response.data);
        this.offersIds = response.data;

        let closestOfferIndex = this.offersIds.indexOf(this.closestOffer);
        if (closestOfferIndex > -1) {
          this.offersIds.splice(closestOfferIndex, 1);
        }

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
    },
    getRecommendedOffer() {
      axios.get(GATEWAY_ADDRESS + '/offer/getAllOffers').then((response) => {
        this.closestOffer = response.data[0];
      }).catch(error => {
        console.error('ERROR: ', error);

        this.emitter.emit('axiosError', {error: error.response.status});
      });
    }
  },
  mounted() {
    if (localStorage.getItem('token')) {
      this.token = localStorage.getItem('token');

      this.getClosestOffer();
      this.getNewestOffers();
    } else {
      this.getRecommendedOffer();
      this.getNewestOffers();
    }
  },
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
  font-size: calc(2px + 1.6vw);
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: 18%;
  margin: -1% 5%;
  border-radius: 25px;
  padding: 5px;
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

span {
  display: flex;
  justify-content: center;
}

@media screen and (max-width: 450px) {
  p {
    width: 25%;
  }
}

@media only screen and (max-width: 1100px) {
  #newest-offers {
    flex-direction: column;
  }

  span {
    flex-direction: column;
  }

  .bigPreview {
    width: 88%;
    margin: 0 auto;
    margin-bottom: 10px;
  }
}
</style>

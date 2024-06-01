<template>
  <div id="my-account-page">
    <Header />
    <div id="my-account-content" v-if="id" style="align-items: center;">
      <div id="my-account-owner">
        <OwnerData :id="id" displaySelector=true />
      </div>
      <div id="my-account-offers">
        <div id="my-reserved-offers">
          <h1> Zarezerwowane oferty </h1>
          <span v-if="reservedOffers">
            <span v-for="offer in reservedOffers" :key="offer">
              <OfferReview :id="offer" />
            </span>
            <span v-if="reservedOffers.length === 0">
              <p> Brak zarezerwowanych ofert </p>
            </span>
          </span>
        </div>
        <div id="my-created-offers">
          <h1> Wystawione oferty </h1>
          <span v-if="createdOffers">
            <span v-for="offer in createdOffers" :key="offer">
              <OfferReview :id="offer" />
            </span>
            <span v-if="createdOffers.length === 0">
              <p> Brak stworzonych ofert </p>
            </span>
          </span>
        </div>
        <div id="my-ended-offers">
          <h1> Zakończone oferty </h1>
          <span v-if="endedOffers">
            <span v-for="offer in endedOffers" :key="offer.id">
              <OfferReview :id="offer" />
            </span>
            <span v-if="endedOffers.length === 0">
              <p> Brak zakończonych ofert </p>
            </span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import OwnerData from "@/components/organisms/OwnerData.vue";
import OfferReview from "@/components/organisms/OfferReview.vue";

import { GATEWAY_ADDRESS } from "../../../public/Consts";

import { jwtDecode } from "jwt-decode";
import axios from "axios";

export default {
  name: "MyAccount",
  components: {
    Header,
    OwnerData,
    OfferReview,
  },
  data() {
    return {
      id: null,
      changedImage: null,
      reservedOffers: null,
      createdOffers: null,
      endedOffers: null,
    }
  },
  mounted() {
    let token = localStorage.getItem('token');
    if (token) {
      this.id = jwtDecode(token).id;
    }
    this.setupImageSelectedListener();
    this.getReservedOffers();
    this.getCreatedOffers();
    this.getEndedOffers();
  },
  methods: {
    setupImageSelectedListener() {
      this.emitter.on('image-uploaded', (file) => {
        this.changedImage = file;
      });
    },
    getReservedOffers() {
      axios.get(GATEWAY_ADDRESS + '/offer/getReservedOffersByUser/' + this.id)
        .then(response => {
          console.log(response.data.offerIds);
          this.reservedOffers = response.data.offerIds;
        })
        .catch(error => {
          console.log(error);
        });
    },
    getCreatedOffers() {
      axios.get(GATEWAY_ADDRESS + '/offer/getCreatedOffersByUser/' + this.id)
        .then(response => {
          console.log("utworzeone", response.data.offerIds);
          this.createdOffers = response.data.offerIds;
        })
        .catch(error => {
          console.log(error);
        });
    },
    getEndedOffers() {
      axios.get(GATEWAY_ADDRESS + '/offer/getFinishedOffersByUser/' + this.id)
        .then(response => {
          console.log(response.data.offerIds);
          this.endedOffers = response.data.offerIds;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
}
</script>

<style scoped>
#my-account-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
}

h1 {
  margin-top: 1%;
}

#my-account-content {
  display: flex;
  flex-direction: column;
  margin-top: 2%;
  margin-left: 10%;
  width: 80%;
}

#my-account-offers {
  width: 90%;
}
</style>
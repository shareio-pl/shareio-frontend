<template>
  <div id="user-page">
    <Header />
    <div id="user-page-content" v-if="id">
      <div id="user-page-data">
        <OwnerData :id="id" style="margin-bottom: 1em; width:100%;" />
      </div>
      <div id="user-page-offers">
        <h1> Wystawione oferty </h1>
        <span v-if="createdOffers">
          <span v-for="offer in createdOffers" :key="offer">
            <OfferPreview :id="offer" style="width: 100%; margin-left: 0px; margin-right: 0px;" />
          </span>
          <span v-if="createdOffers.length === 0">
            <p> Użytkownik nie posiada obecnie wystawionych ofert. </p>
          </span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import OfferPreview from "../organisms/OfferPreview.vue";
import OwnerData from "../organisms/OwnerData.vue";

import axios from 'axios';

import { GATEWAY_ADDRESS } from "../../../public/Consts";

export default {
  name: "UserPage",
  components: {
    Header,
    OfferPreview,
    OwnerData,
  },
  data() {
    return {
      id: this.$route.params.id,
      createdOffers: null,
    }
  },
  mounted() {
    this.getCreatedOffers();
  },
  methods: {
    getCreatedOffers() {
      axios.get(GATEWAY_ADDRESS + '/offer/getCreatedOffersByUser/' + this.id)
        .then(response => {
          console.log("utworzeone", response.data.offerIds);
          this.createdOffers = response.data.offerIds;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
}

</script>

<style scoped>
#user-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
  padding-bottom: 5em;
}

#user-page-content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

#user-page-data {
  margin-top: 1em;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
  margin-bottom: 1em;
}

#user-page-offers {
  display: inline-block;
  flex-direction: column;
  align-items: center;
  width: 40%;
  margin: auto;
}
</style>
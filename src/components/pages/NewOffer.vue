<template>
  <div id="new-offer-page">
    <Header />
    <div id="new-offer-container">
      <AddOffer v-if="userId" :ownerId="userId" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import AddOffer from "@/components/organisms/AddOffer.vue";

import axios from "axios";
import { GATEWAY_ADDRESS } from "../../../public/Consts";

export default {
  name: "NewOffer",
  components: { Header, AddOffer },
  data() {
    return {
      userId: '',
    }
  },
  async mounted() {
    // TODO: get user from session

    let ids = await axios.get(GATEWAY_ADDRESS + '/debug/getOfferIds').then((response) => {
      console.log('Offers: ', response.data.offerIds);
      return response.data.offerIds;
    }).catch(error => {
      console.error('ERROR: ', error);
      this.emitter.emit('axiosError', { error: error.response.status });
    });

    console.log('userId: ', ids);
    await axios.get(GATEWAY_ADDRESS + `/offer/get/${ids[0]}`)
      .then((offerResponse) => {
        this.userId = offerResponse.data.ownerId;
      })
      .catch(error => {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
  },
}
</script>

<style scoped>
#new-offer-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 102vh;
}

#new-offer-container {
  margin-top: 2%;
}
</style>
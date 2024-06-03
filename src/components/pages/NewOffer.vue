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

import { jwtDecode } from "jwt-decode";

export default {
  name: "NewOffer",
  components: { Header, AddOffer },
  data() {
    return {
      userId: '',
    }
  },
  mounted() {
    let token = localStorage.getItem('token');
    this.userId = jwtDecode(token).id;
    console.log("NewOfferID", this.userId);
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
  margin-top: 1%;
}
</style>

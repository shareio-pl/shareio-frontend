<template>
  <div id="my-account-page">
    <Header />
    <div id="my-account-content" v-if="id">
      <div id="my-account-owner">
        <div id="my-account-owner-wrapper">
          <OwnerData :id="id" displaySelector=true style="margin-bottom: 1em;" />
          <FormLocationProfile :id="id" style="margin-bottom: 1em;" />
          <div class="my-account-delete">
            <div>
              <p class="my-account-delete-header" style="margin-bottom: 1em;"> Zarządzaj kontem </p>
            </div>
            <div class="my-account-delete-content">
              <ButtonPrimary buttonText="Usuń konto" style="width: 50%; margin-left:0; background-color:brown"
                @click="deleteAccount" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import OwnerData from "@/components/organisms/OwnerData.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import FormLocationProfile from "@/components/organisms/FormLocationProfile.vue";

import { COLORS, FONT_SIZES } from "../../../public/Consts";

import { jwtDecode } from "jwt-decode";

export default {
  name: "MyAccount",
  components: {
    Header,
    OwnerData,
    FormLocationProfile,
    ButtonPrimary,
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      id: null,
      changedImage: null,
      reservedOffers: null,
      createdOffers: null,
      myReservedOffers: null,
      pickedUpOffers: null,
      endedOffers: null,
    }
  },
  mounted() {
    let token = localStorage.getItem('token');
    if (token) {
      this.id = jwtDecode(token).id;
    }
    this.setupImageSelectedListener();
  },
  methods: {
    setupImageSelectedListener() {
      this.emitter.on('image-uploaded', (file) => {
        this.changedImage = file;
      });
    },
    deleteAccount() {
      if (confirm("Czy na pewno chcesz usunąć konto? Operacja jest nieodwracalna.")) {
        // TODO REMOVE
        alert("Twoje konto zostało by usunięte, ale wiemy, że chcesz pozostać tutaj!");
      }
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
  padding-bottom: 5em;
}

#my-account-content {
  margin-top: 0;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  width: 100%;
  margin: 0 auto;
}

#my-account-owner {
  margin-top: 1em;
  width: 35%;
  min-width: 500px;
  align-self: flex-start;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

#my-account-owner-wrapper {
  border-radius: 0.5em;
  padding: 1em;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

.down-offer {
  color: v-bind('COLORS.PRIMARY');
}

.my-account-delete-header {
  width: 100%;
  height: 25%;
  margin-bottom: 0px;
  color: v-bind('COLORS.TEXT_PRIMARY');
  background-color: v-bind('COLORS.SECONDARY');
  font-size: v-bind("FONT_SIZES.PRIMARY");
}
</style>
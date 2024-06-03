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
              <p class="my-account-delete-header" style="margin-bottom: 1em;"> Usuń konto </p>
            </div>
            <div class="my-account-delete-content">
              <ButtonPrimary buttonText="Usuń konto" style="width: 50%; margin-left:0; background-color:brown"
                @click="deleteAccount" />
            </div>
          </div>
        </div>
      </div>
      <div id="my-account-offers">
        <div id="my-reserved-offers">
          <h1> Wystawione, zarezerwowane oferty </h1>
          <span v-if="reservedOffers">
            <span v-for="offer in reservedOffers" :key="offer">
              <OfferReview :id="offer" style="width: 100%;" />
            </span>
            <span v-if="reservedOffers.length === 0">
              <p> Nikt jeszcze nie zarezerwował twoich ofert! </p>
            </span>
          </span>
        </div>
        <div id="my-created-offers">
          <h1> Wystawione oferty </h1>
          <span v-if="createdOffers">
            <span v-for="offer in createdOffers" :key="offer">
              <OfferManage :id="offer" style="width: 100%;" />
            </span>
            <span v-if="createdOffers.length === 0">
              <p> Brak stworzonych ofert </p>
            </span>
          </span>
        </div>
        <div id="reserved-by-me-offers">
          <h1 class="down-offer"> Twoje zarezerwowane oferty </h1>
          <span v-if="myReservedOffers">
            <span v-for="offer in myReservedOffers" :key="offer">
              <OfferReserved :id="offer" style="width: 100%;" />
            </span>
            <span v-if="myReservedOffers.length === 0 || myReservedOffers == null">
              <p class="down-offer"> Nie zarezerwowano żadnych ofert - przejrzyj dostępne! </p>
            </span>
          </span>
        </div>
        <div id="picked-up-by-me-offers">
          <h1 class="down-offer"> Odebrane przez ciebie oferty </h1>
          <span v-if="pickedUpOffers">
            <span v-for="offer in pickedUpOffers" :key="offer">
              <OfferReview :id="offer" style="width: 100%;" />
            </span>
            <span v-if="pickedUpOffers.length === 0 || pickedUpOffers == null">
              <p class="down-offer
              "> Nie odebrano żadnych ofert - zarezerwuj coś! </p>
            </span>
          </span>
        </div>
        <div id="my-ended-offers">
          <h1 class="down-offer"> Zakończone oferty </h1>
          <span v-if="endedOffers">
            <span v-for="offer in endedOffers" :key="offer.id">
              <OfferPreview :id="offer" style="width: 80%;" />
            </span>
            <span v-if="endedOffers.length === 0">
              <p class="down-offer"> Brak zakończonych ofert </p>
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
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

import OfferReview from "@/components/organisms/OfferReview.vue";
import OfferManage from "@/components/organisms/OfferManage.vue";
import OfferPreview from "@/components/organisms/OfferPreview.vue";
import OfferReserved from "@/components/organisms/OfferReserved.vue";
import FormLocationProfile from "@/components/organisms/FormLocationProfile.vue";

import { GATEWAY_ADDRESS, COLORS, FONT_SIZES } from "../../../public/Consts";

import { jwtDecode } from "jwt-decode";
import axios from "axios";

export default {
  name: "MyAccount",
  components: {
    Header,
    OwnerData,
    OfferReview,
    OfferManage,
    OfferPreview,
    OfferReserved,
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
    this.getReservedOffers();
    this.getCreatedOffers();
    this.getEndedOffers();
    this.getOffersReservedByMe();
    this.getPickedUpOffers();
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
    getOffersReservedByMe() {
      axios.get(GATEWAY_ADDRESS + '/offer/getReservedOffersByReciever/' + this.id)
        .then(response => {
          console.log("Moje oferty które zarezerowowałem: ", response.data);
          console.log(response.data.offerIds);
          this.myReservedOffers = response.data;
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
    },
    getPickedUpOffers() {
      axios.get(GATEWAY_ADDRESS + '/offer/getFinishedOffersByReciever/' + this.id)
        .then(response => {
          console.log("Oferty, które odebrałem i muszę ocenić: ", response.data);
          this.pickedUpOffers = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    deleteAccount() {
      if (confirm("Czy na pewno chcesz usunąć konto? Operacja jest nieodwracalna.")) {
        axios.delete(GATEWAY_ADDRESS + '/user/deleteUser/' + this.id)
          .then(response => {
            console.log(response);
            localStorage.removeItem('token');
            this.$router.push('/');
          })
          .catch(error => {
            console.log(error);
          });
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
  width: 90%;
  margin: 0 auto;
}

#my-account-owner {
  margin-top: 1em;
  width: 40%;
  align-self: flex-start;
  display: flex;
  flex-direction: column;
}

#my-account-owner-wrapper {
  width: 90%;
  border-radius: 0.5em;
  padding: 1em;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

#my-account-offers {
  display: flex;
  flex-direction: column;
  margin-top: 1em;
  width: 60%;
}

#my-account-offers>>>OfferPreview {
  width: 1000px;
}

#my-reserved-offers #my-created-offers #my-ended-offers {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
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
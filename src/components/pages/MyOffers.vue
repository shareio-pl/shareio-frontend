<template>
  <div id="my-offers-page">
    <Header />
    <div id="my-offers-content" v-if="id">
      <div id="my-offers-offers">
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
              <OfferPreview class="endOffer" :id="offer" style="width: 80%;" />
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
import OfferReview from "@/components/organisms/OfferReview.vue";
import OfferManage from "@/components/organisms/OfferManage.vue";
import OfferPreview from "@/components/organisms/OfferPreview.vue";
import OfferReserved from "@/components/organisms/OfferReserved.vue";

import { GATEWAY_ADDRESS, COLORS, FONT_SIZES } from "../../../public/Consts";

import { jwtDecode } from "jwt-decode";
import axios from "axios";

export default {
  name: "MyAccount",
  components: {
    Header,
    OfferReview,
    OfferManage,
    OfferPreview,
    OfferReserved,
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
    this.setupOfferPickedListener();
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
    setupOfferPickedListener() {
      this.emitter.on('offer-picked', (data) => {
        this.myReservedOffers = this.myReservedOffers.filter(offer => offer.id !== data.id);
        this.getPickedUpOffers();
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
        alert("Twoje konto zostało by usunięte, ale wiemy, że chcesz pozostać tutaj!");
      }
    }
  }
}
</script>

<style scoped>
#my-offers-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
  padding-bottom: 5em;
}

#my-offers-content {
  margin-top: 0;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  width: 90%;
  margin: 0 auto;
}


#my-offers-offers {
  display: flex;
  flex-direction: column;
  margin-top: 1em;
  width: 60%;
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

h1 {
  margin-top: 1em;
}

@media (max-width: 1000px) {
  .endOffer {
    width: 100% !important;
  }
}

@media screen and (max-width: 450px) {
  #my-offers-content ::v-deep .offer-preview-image {
    aspect-ratio: 1/2;
  }
}
</style>

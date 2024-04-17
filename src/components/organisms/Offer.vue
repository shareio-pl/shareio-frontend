<template>
  <div class="offer-card">
    <div class="offer-left">
      <div class="offer-left-image">
        <img class="offer-left-image" :src="offerImage" alt="Offer image" />
      </div>
      <div class="offer-left-data">
        <p class="offer-left-giver"> Oddająca osoba: </p>
        <UserData class="offer-user" :userFirstName="userFirstName" :userSurname="userSurname" />
        <Stars class="offer-stars" :filledStars="amountOfStars" :ratingsAmount="amountOfRatings" />
      </div>
    </div>
    <div class="offer-content">
      <h2 class="offer-content-title">{{ offerTitle }}</h2>
      <div class="offer-content-metadata">
        <p> Wystawiono: {{ submittedOn }}</p>
        <p> Lokalizacja: {{ location }}</p>
        <p> Stan: {{ condition }}</p>
        <p class="offer-content-metadata-desc">{{ offerDescription }}</p>
      </div>
    </div>
    <div class="offer-right">
      <img class="offer-right-image" :src="offerMapImage" alt="Offer map image" />
      <ButtonPrimary class="offer-right-button" :buttonText="offerButtonName" @click="submitOffer" />
    </div>
  </div>
</template>

<script>
import UserData from "@/components/atoms/UserData.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import Stars from "@/components/atoms/Stars.vue";

import { COLORS } from "../../../public/Consts";
import { FONT_SIZES } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Offer",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      offerTitle: '',
      offerDescription: '',
      submittedOn: '',
      location: '',
      condition: '',
      amountOfStars: 0,
      amountOfRatings: 0,
      userFirstName: '',
      userSurname: '',
      offerImage: null,
      offerMapImage: null,
    }
  },
  props: {
    id: {
      type: Number,
      required: true
    },
    offerButtonName: {
      type: String,
      default: 'Zarezerwuj'
    }
  },
  components: {
    UserData,
    ButtonPrimary,
    Stars
  },
  methods: {
    submitOffer() {
      //TODO
      console.log('Button on Offer was clicked.');
    }
  }
}
</script>

<style scoped>
.offer-card {
  display: flex;
  width: 90%;
  height: 50%;
  margin: 0 auto;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

.offer-left {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  flex: 0 0 30%;
  box-sizing: border-box;
}

.offer-left-image {
  border-top-left-radius: 20%;
  border-bottom-right-radius: 20%;
  aspect-ratio: 1/1;
}

.offer-left-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.offer-left-data {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.offer-left-giver {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-stars {
  margin-left: 10%;
}

.offer-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 2%;
  flex: 0 0 50%;
}

.offer-content-title {
  padding-top: 5%;
  font-size: v-bind('FONT_SIZES.TITLE');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-content-metadata {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-content-metadata p {
  margin-bottom: 0.1em;
  text-align: left;
}

.offer-content-metadata-desc {
  margin-top: 10%;
  font-size: v-bind('FONT_SIZES.SECONDARY');
}

.offer-right {
  padding: 1%;
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 0 0 20%;
  box-sizing: border-box;
}

.offer-right-image {
  margin-top: 40%;
  border-radius: 30px;
  width: 100%;
}

.offer-right-button {
  margin-top: 40%;
  width: 90%;
  height: 10%;
}
</style>
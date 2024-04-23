<template>
  <div class="offer-card">
    <div class="offer-left">
      <div class="offer-left-image">
        <img class="offer-left-image" :src="offerImage" alt="Offer image"/>
      </div>
      <p class="offer-left-giver"> Oddająca osoba: </p>
      <div class="offer-left-data">
        <UserData class="offer-user" :userFirstName="userFirstName" :userSurname="userSurname"/>
        <Stars class="offer-stars" :filledStars="amountOfStars" :ratingsAmount="amountOfRatings"/>
      </div>
    </div>
    <div class="offer-content">
      <h2 class="offer-content-title">{{ offerTitle }}</h2>
      <div class="offer-content-metadata">
        <p style="font-weight: bold"> Wystawiono: {{ submittedOn }}</p>
        <p style="font-weight: bold"> Lokalizacja: {{ location }}</p>
        <p style="font-weight: bold"> Stan: {{ condition }}</p>
        <p class="offer-content-metadata-desc">{{ offerDescription }}</p>
      </div>
    </div>
    <div class="offer-right">
      <img class="offer-right-image" :src="offerMapImage" alt="Offer map image"/>
      <ButtonPrimary class="offer-right-button" :buttonText="offerButtonName" @click="submitOffer"/>
    </div>
  </div>
</template>

<script>
import UserData from "@/components/atoms/UserData.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import Stars from "@/components/atoms/Stars.vue";
import axios from 'axios'

import {COLORS} from "../../../public/Consts";
import {FONT_SIZES} from "../../../public/Consts";
import {DEFAULT_OFFER_IMAGE} from "../../../public/Consts";
import {DEFAULT_OFFER_MAP_IMAGE} from "../../../public/Consts";
import {GATEWAY_ADDRESS} from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Offer",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      offerTitle: 'Szop',
      offerDescription: 'The raccoon, also spelled racoon and sometimes called the common raccoon to distinguish it from the other species, is a mammal native to North America. It is the largest of the procyonid family, having a body length of 40 to 70 cm, and a body weight of 5 to 26 kg.',
      submittedOn: '01/01/2001',
      location: 'Uć, 15 km stąd',
      condition: 'nowy',
      amountOfStars: 4,
      amountOfRatings: 37,
      userFirstName: 'Janusz',
      userSurname: 'Kowalski',
      offerImage: DEFAULT_OFFER_IMAGE,
      offerMapImage: DEFAULT_OFFER_MAP_IMAGE,
    }
  },
  props: {
    id: {
      type: String,
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
    },
  },
  mounted() {
    axios.get(GATEWAY_ADDRESS + `/offer/get/${this.id}`).then((response) => {
      console.log('Offer ', this.id, ': ', response.data);

      this.offerTitle = response.data.title;
      this.offerDescription = response.data.description;
      this.submittedOn = response.data.creationDate;
      this.location = response.data.city;
      this.condition = response.data.condition;
      this.amountOfStars = response.data.ownerRating;
      this.amountOfRatings = response.data.ownerReviewCount;
      this.userFirstName = response.data.ownerName;
      this.userSurname = response.data.ownerSurname;
    }).catch(error => {
      console.error('ERROR: ', error);
    });
  },
}
</script>

<style scoped>
.offer-card {
  display: flex;
  width: 90%;
  height: 55%;
  margin: 0 auto;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  border-radius: 20% 25px 25px 25px;
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
  margin-bottom: 3%;
  margin-right: 8%;
}

.offer-left-giver {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-stars {
  margin-left: 3%;
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
  font-size: v-bind('FONT_SIZES.PRIMARY');
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
  margin-top: 15%;
  border-radius: 30px;
  width: 100%;
  height: 50%;
}

.offer-right-button {
  margin-top: 55%;
  width: 100%;
  height: 15%;
}
</style>

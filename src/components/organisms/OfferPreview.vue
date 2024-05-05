<template>
  <div class="offer-preview-card">
    <div class="offer-preview-image">
      <img :src="offerPreviewImage" alt="Offer image"/>
    </div>
    <div class="offer-preview-content">
      <h2 class="offer-preview-title">{{ title }}</h2>
      <p class="offer-preview-is-new" v-if="isNew"> Nowe! </p>
      <p class="offer-preview-is-not-new" v-else></p> <!-- An hack to keep the location in the same place; -->
      <p class="offer-preview-location">{{ location }}</p>
    </div>
    <div class="offer-preview-action">
      <UserData class="offer-preview-user" :userFirstName="userFirstName" :userSurname="userLastName"/>
      <Stars class="offer-preview-stars" :filledStars="starsAmount" :ratingsAmount="ratingsAmount"/>
    </div>
  </div>
</template>

<script>
import Stars from '../atoms/Stars.vue';
import UserData from '../atoms/UserData.vue';
import {COLORS, FONT_SIZES} from "../../../public/Consts";

import {DEFAULT_PREVIEW_OFFER_IMAGE} from "../../../public/Consts";
import {GATEWAY_ADDRESS} from "../../../public/Consts";
import axios from "axios";

export default {
  name: "OfferPreview",
  components: {
    Stars,
    UserData,
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      userFirstName: 'Rafał',
      userLastName: 'Cybula',
      starsAmount: 3,
      ratingsAmount: 12,
      location: 'Remiszewiece',
      title: 'Oferta',
      offerPreviewImage: DEFAULT_PREVIEW_OFFER_IMAGE,
    };
  },
  props: {
    id: {
      type: Number,
      required: true
    },
    isNew: {
      type: Boolean,
      required: false
    },
  },
  mounted() {
    axios.get(GATEWAY_ADDRESS + `/offer/get/${this.id}`).then((response) => {
      this.userFirstName = response.data.ownerName;
      this.userLastName = response.data.ownerSurname;
      this.starsAmount = response.data.ownerRating;
      this.ratingsAmount = response.data.ownerReviewCount;
      this.location = response.data.city;
      this.title = response.data.title;
    }).catch(error => {
      console.error('ERROR: ', error);

      this.emitter.emit('axiosError', {error: error.response.status});
    });
  }
}
</script>

<style scoped>
.offer-preview-card {
  display: flex;
  justify-content: space-between;
  width: 90%;
  height: 150px;
  margin: 7px auto;
  border: 10px solid v-bind('COLORS.PRIMARY');
  border-radius: 25px;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

.offer-preview-is-new {
  background-color: v-bind('COLORS.NOTIFICATION_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: 10%;
  margin-bottom: 0.5%;
  margin-top: 0.5%;
  border-radius: 25px;
  padding: 10px;
}

.offer-preview-is-not-new {
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  margin-bottom: 2%;
  padding: 10px;
}

.offer-preview-image {
  height: 100%;
  width: auto;
  margin-right: 20px;
  border-radius: 10px;
  aspect-ratio: 1/1;
}

.offer-preview-image img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  border-radius: 15px 10px 10px 15px;
}

.offer-preview-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-grow: 1;
}

.offer-preview-title {
  margin-top: 0.8%;
  margin-bottom: 0.5%;
  display: flex;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-preview-location {
  margin-top: 0.5%;
  font-size: v-bind('FONT_SIZES.STARS');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-preview-action {
  padding: 2%;
  display: flex;
  flex-direction: column;
}

.offer-preview-user {
  margin-bottom: 8%;
}

.offer-preview-stars {
  margin-left: 10%;
  flex-direction: row;
  align-self: center;
  font-size: v-bind('FONT_SIZES.STARS');
}
</style>

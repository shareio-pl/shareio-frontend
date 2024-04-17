<template>
  <div class="offer-preview-card">
    <div class="offer-preview-image">
      <img :src="offerPreviewImage" alt="Offer image" />
    </div>
    <div class="offer-preview-content">
      <h2 class="offer-preview-title">{{ title }}</h2>
      <p class="offer-preview-is-new" v-if="isNew"> Nowa oferta! </p>
      <p class="offer-preview-location">{{ location }}</p>
    </div>
    <div class="offer-preview-action">
      <UserData class="offer-preview-user" :userFirstName="userFirstName" :userSurname="userLastName" />
      <Stars class="offer-preview-stars" :filledStars="starsAmount" :ratingsAmount="ratingsAmount" />
    </div>
  </div>
</template>

<script>
import Stars from '../atoms/Stars.vue';
import UserData from '../atoms/UserData.vue';
import { COLORS, FONT_SIZES } from "../../../public/Consts";

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
      userFirstName: '',
      userLastName: '',
      starsAmount: '',
      ratingsAmount: '',
      location: '',
      title: '',
      offerPreviewImage: '',
    };
  },
  props: {
    buttonName: {
      type: String,
      required: true
    },
    isNew: {
      type: Boolean,
      required: true
    },
  }
}
</script>

<style>
.offer-preview-card {
  display: flex;
  justify-content: space-between;
  width: 90%;
  height: 150px;
  margin: 0 auto;
  border: 10px solid v-bind('COLORS.PRIMARY');
  border-radius: 25px;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

.offer-preview-is-new {
  background-color: v-bind('COLORS.NOTIFICATION_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  margin-bottom: 0.5%;
  border-radius: 25px;
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
  border-radius: 15px;
}

.offer-preview-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-grow: 1;
}

.offer-preview-title {
  margin-top: 1.2%;
  margin-bottom: 0.5%;
  display: flex;
  font-size: v-bind('FONT_SIZES.PRIMARY');
}

.offer-preview-location {
  margin-top: 0%;
  font-size: v-bind('FONT_SIZES.SECONDARY');
}

.offer-preview-action {
  padding: 1%;
  display: flex;
  flex-direction: column;
}

.offer-preview-user {
  margin-bottom: 8%;
}

.offer-preview-stars {
  flex-direction: row;
  align-self: center;
  font-size: v-bind('FONT_SIZES.STARS');
}
</style>
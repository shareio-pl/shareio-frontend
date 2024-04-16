<template>
  <div class="offer-preview-card">
    <div class="offer-preview-image">
      <img :src="offerPreviewImage" alt="Offer image" />
    </div>
    <div class="offer-preview-content">
      <h2 class="offer-preview-title">{{ title }}</h2>
      <ButtonPrimary class="offer-preview-button" :buttonText="buttonName" :style="{ marginLeft: '0px' }"
        @click="onClick"></ButtonPrimary>
      <p class="offer-preview-location">{{ location }}</p>
    </div>
    <div class="offer-preview-action">
      <UserData class="offer-preview-user" :userFirstName="userFirstName" :userSurname="userLastName" />
      <Stars class="offer-preview-stars" :filledStars="starsAmount" />
    </div>
  </div>
</template>

<script>
import Stars from '../atoms/Stars.vue';
import UserData from '../atoms/UserData.vue';
import ButtonPrimary from '../atoms/ButtonPrimary.vue';
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { DEFAULT_PREVIEW_OFFER_IMAGE } from "../../../public/Consts";

export default {
  name: "OfferPreview",
  components: {
    Stars,
    UserData,
    ButtonPrimary,
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      userFirstName: 'Janusz',
      userLastName: 'Kowalski',
      starsAmount: '2',
      location: 'Test',
      title: 'Test',
      offerPreviewImage: DEFAULT_PREVIEW_OFFER_IMAGE,
    };
  },
  methods: {
    onClick() {
      // TODO
    },
  },
  props: {
    buttonName: {
      type: String,
      required: true
    }
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
  border: 10px solid v-bind('COLORS.OFFER_BACKGROUND');
  border-radius: 25px;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
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
  margin-top: 1.5%;
  font-size: v-bind('FONT_SIZES.SECONDARY');
}

.offer-preview-action {
  padding: 1%;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.offer-preview-stars {
  font-size: v-bind('FONT_SIZES.STARS');
}
</style>
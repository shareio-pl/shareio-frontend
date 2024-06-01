<template>
  <div v-if="imageIsLoading" class="offer-preview-card">
    <ImageLoadingAnimation />
  </div>
  <div v-else class="offer-preview-card" @click="navigateToOfferPage">
    <div class="offer-preview-image">
      <img :src="offerPreviewImage" alt="Offer image" />
    </div>
    <div class="offer-preview-content">
      <h2 class="offer-preview-title">{{ title }}</h2>
      <p class="offer-preview-is-new" v-if="isNew"> Nowe! </p>
      <p class="offer-preview-is-not-new" v-else></p> <!-- An hack to keep the location in the same place; -->
      <p class="offer-preview-location">{{ city }}, {{ street }}</p>
    </div>
    <div class="offer-preview-action">
      <UserData class="offer-preview-user" :userFirstName="userFirstName" :userSurname="userLastName"
        :userImage="userImage" />
      <Stars class="offer-preview-stars" :filledStars="starsAmount" :ratingsAmount="ratingsAmount" />
    </div>
  </div>
</template>

<script>
import Stars from '../atoms/Stars.vue';
import UserData from '../atoms/UserData.vue';
import ImageLoadingAnimation from '../atoms/ImageLoadingAnimation.vue';

import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { DEFAULT_USER_PROFILE_IMAGE } from '../../../public/Consts';
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import axios from "axios";

export default {
  name: "OfferPreview",
  components: {
    Stars,
    UserData,
    ImageLoadingAnimation,
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      userFirstName: '',
      userLastName: '',
      starsAmount: 0,
      ratingsAmount: 0,
      city: '',
      street: '',
      title: '',
      reviewId: null,
      imageIsLoading: true,
      offerPreviewImage: null,
      userImage: DEFAULT_USER_PROFILE_IMAGE,
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
    this.getOfferData();
    if (this.reviewId !== null) {
      this.emitter.emit('review-not-done', { id: this.id });
    }
  },
  methods: {
    navigateToOfferPage() {
      window.location.href = `/offer/${this.id}`;
    },
    arrayBufferToBase64(buffer) {
      return btoa(
        new Uint8Array(buffer).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    },
    async getOfferData() {
      try {
        let response = await axios.get(GATEWAY_ADDRESS + `/offer/get/${this.id}`);
        console.log(response);
        this.userFirstName = response.data.ownerName;
        this.userLastName = response.data.ownerSurname;
        this.starsAmount = response.data.ownerRating;
        this.ratingsAmount = response.data.ownerReviewCount;
        this.city = response.data.city;
        this.street = response.data.street;
        this.title = response.data.title;
        this.offerPreviewImage = await this.getImageData(response.data.photoId);
        this.userImage = await this.getImageData(response.data.ownerPhotoId);
        this.imageIsLoading = false;
        this.reviewId = response.data.reviewId;
        if (this.reviewId !== null) {
          this.emitter.emit('review-not-done', { id: this.id });
        }
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      }
    },
    async getImageData(photoId) {
      try {
        const response = await axios.get(GATEWAY_ADDRESS + `/image/get/${photoId}`, { responseType: 'arraybuffer' });
        let image = this.arrayBufferToBase64(response.data);
        return `data:image/jpeg;base64,${image}`;
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      }
    },
  },
}
</script>

<style scoped>
.offer-preview-card {
  display: flex;
  justify-content: space-between;
  width: 44%;
  height: 150px;
  margin: 7px;
  border: 10px solid v-bind('COLORS.PRIMARY');
  border-radius: 25px;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  cursor: pointer;
}

.offer-preview-is-new {
  background-color: v-bind('COLORS.NOTIFICATION_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: 25%;
  min-width: 65px;
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
  margin-top: 3%;
  margin-bottom: 3%;
  display: flex;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-preview-location {
  margin-top: 2%;
  font-size: v-bind('FONT_SIZES.STARS');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-preview-action {
  padding: 2%;
  margin-right: max(2%, 15px);
  width: 30%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.offer-preview-user {
  margin-bottom: 8%;
  margin-right: 5%;
}

.offer-preview-stars {
  flex-direction: row;
  align-self: center;
  font-size: v-bind('FONT_SIZES.STARS');
  margin-left: 4%;
}

@media (max-width: 500px) {
  .offer-preview-action {
    display: none;
  }
}
</style>

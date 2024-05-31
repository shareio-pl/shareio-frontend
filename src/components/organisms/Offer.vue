<template>
  <div class="offer-card">
    <div class="offer-left">
      <div v-if="imageIsLoading" class="offer-left-image">
        <ImageLoadingAnimation />
      </div>
      <div v-else class="offer-left-image">
        <img class="offer-left-image" :src="offerImage" alt="Offer image">
      </div>
      <p class="offer-left-giver"> Oddająca osoba: </p>
      <div class="offer-left-data">
        <UserData class="offer-user" :userFirstName="userFirstName" :userSurname="userSurname" :userImage="userImage" />
        <Stars class="offer-stars" :filledStars="amountOfStars" :ratingsAmount="amountOfRatings" />
      </div>
    </div>
    <div class="offer-content">
      <h2 class="offer-content-title">{{ offerTitle }}</h2>
      <div class="offer-content-metadata">
        <p> Wystawiono: <span style="font-weight: bold">{{ submittedOn }}</span></p>
        <p> Lokalizacja: <span style="font-weight: bold">{{ location }}</span></p>
        <p> Stan: <span style="font-weight: bold">{{ condition }}</span></p>
        <p class="offer-content-metadata-desc">{{ offerDescription }}</p>
      </div>
    </div>
    <div class="offer-right">
      <div class="offer-right-map">
        <MapPreview v-if="mapDataLoaded" :zoom="zoom" :center="center" />
      </div>
      <ButtonPrimary class="offer-right-button" :buttonText="offerButtonName" @click="submitOffer" />
    </div>
  </div>
</template>
<script>
import UserData from "@/components/atoms/UserData.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import Stars from "@/components/atoms/Stars.vue";
import MapPreview from "@/components/atoms/MapPreview.vue";
import ImageLoadingAnimation from "@/components/atoms/ImageLoadingAnimation.vue";

import axios from 'axios'
import "leaflet/dist/leaflet.css"
import { COLORS } from "../../../public/Consts";
import { FONT_SIZES } from "../../../public/Consts";
import { GATEWAY_ADDRESS } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Offer",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      zoom: 16,
      center: [0, 0],
      mapDataLoaded: false,
      imageIsLoading: true,
      offerImage: '',
      offerMapImage: '',
      userImage: '',
      offerTitle: '',
      offerDescription: '',
      submittedOn: '',
      location: '',
      condition: '',
      amountOfStars: '',
      amountOfRatings: '',
      userFirstName: '',
      userSurname: '',
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
    Stars,
    MapPreview,
    ImageLoadingAnimation,
  },
  methods: {
    submitOffer() {
      //TODO
      console.log('Button on Offer was clicked.');
    },
    arrayBufferToBase64(buffer) {
      return btoa(
        new Uint8Array(buffer).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    },
    // These methods are async, because otherwise they'd return undefined in getImageData.
    // Alternatively another option would be to pass the variable for image data in here
    // as a parameter
    async getOfferData() {
      try {
        const response = await axios.get(GATEWAY_ADDRESS + `/offer/get/${this.id}`);
        console.log('Offer ', this.id, ': ', response.data);
        this.offerTitle = response.data.title;
        this.offerDescription = response.data.description;
        this.submittedOn = response.data.creationDate.substring(0, 10);
        if (localStorage.getItem('token') === null) {
          this.location = response.data.city + ', ' + response.data.street;
        }
        else {
          this.location = response.data.city + ', ' + response.data.street + ' (' + response.data.distance + ' od Ciebie)';
        }
        this.condition = response.data.condition;
        this.amountOfStars = response.data.ownerRating;
        this.amountOfRatings = response.data.ownerReviewCount;
        this.userFirstName = response.data.ownerName;
        this.userSurname = response.data.ownerSurname;
        this.center = [response.data.latitude, response.data.longitude];
        this.mapDataLoaded = true;
        this.offerImage = await this.getImageData(response.data.photoId);
        this.userImage = await this.getImageData(response.data.ownerPhotoId);
        this.imageIsLoading = false;
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
  mounted() {
    this.getOfferData();
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
  overflow: hidden;
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
  font-size: calc(10px + 1.0vw);

}

.offer-left-giver {
  font-size: calc(10px + 1.0vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-stars {
  margin-left: 4%;
}

.offer-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 2%;
  flex: 0 0 45%;
}

.offer-content-title {
  padding-top: 3%;
  text-align: start;
  font-size: calc(15px + 1.5vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.offer-content-metadata {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: calc(13px + 0.5vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
  margin-top: 7%;
}

.offer-content-metadata p {
  margin-bottom: 0.1em;
  text-align: left;
}

.offer-content-metadata-desc {
  margin-top: 10%;
  font-size: calc(13px + 0.5vw);
  width: 90%;
}

.offer-right {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 0 0 25%;
  box-sizing: border-box;
  justify-content: space-between;
}

.offer-right-map {
  width: 100%;
  height: 50%;
  object-fit: cover;
  margin-left: auto;
  border-top-right-radius: 25px;
  border-bottom-left-radius: 25px;
  overflow: hidden;
  z-index: 1;
}

.offer-right-button {
  width: 75%;
  height: 15%;
  margin-bottom: 15%;
}
@media screen and (max-width: 450px) {
  .offer-right-map {
    margin-right: 33%;
  }
}
</style>

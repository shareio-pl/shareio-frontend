<template>
  <div class="offer-card">
    <div class="offer-left">
      <div v-if="imageIsLoading" class="offer-left-image">
        <ImageLoadingAnimation/>
      </div>
      <div v-else class="offer-left-image">
        <img class="offer-left-image" :src="offerImage" alt="Offer image">
      </div>
      <p class="offer-left-giver"> Oddająca osoba: </p>
      <div class="offer-left-data">
        <UserData class="offer-user" :userFirstName="userFirstName" :userSurname="userSurname" :userImage="userImage"/>
        <Stars class="offer-stars" :filledStars="amountOfStars" :ratingsAmount="amountOfRatings"/>
      </div>
    </div>
    <div class="offer-content">
      <h2 class="offer-content-title">{{ offerTitle }}</h2>
      <div class="offer-content-metadata">
        <p> Wystawiono: <span style="font-weight: bold">{{ submittedOn }}</span></p>
        <p> Lokalizacja: <span style="font-weight: bold">{{ location }} </span></p>
        <p> Stan: <span style="font-weight: bold">{{ condition }}</span></p>
        <p class="offer-content-metadata-desc">{{ offerDescription }}</p>
      </div>
    </div>
    <div class="offer-right">
      <div class="offer-right-map">
        <MapPreview v-if="mapDataLoaded" :zoom="zoom" :center="center"/>
      </div>
      <span v-if="status === 'CREATED' && userId != null" class="offer-right-button">
        <ButtonPrimary class="button" :buttonText="offerButtonName" @click="submitOffer"/>
      </span>
      <span v-if="status === 'RESERVED' && userId != null" class="offer-right-button">
        <ButtonPrimary disabled='true' class="button-disabled" :buttonText="timeUntilUnreserved"/>
      </span>
      <span v-if="userId == null" class="offer-right-button">
        <ButtonPrimary disabled='true' class="button-disabled" buttonText="Zaloguj się, aby zarezerwować"
                       style="line-height: calc(11px + 0.6vw); cursor: pointer;" @click="this.$router.push('/login')"/>
        <!-- przycisk rezerwacji widoczny jest też na stronie głównej-->
      </span>
    </div>
  </div>
</template>
<script>
import UserData from "@/components/atoms/UserData.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import Stars from "@/components/atoms/Stars.vue";
import MapPreview from "@/components/atoms/MapPreview.vue";
import ImageLoadingAnimation from "@/components/atoms/ImageLoadingAnimation.vue";

import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import "leaflet/dist/leaflet.css";

import {COLORS} from "../../../public/Consts";
import {FONT_SIZES} from "../../../public/Consts";
import {GATEWAY_ADDRESS} from "../../../public/Consts";

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
      offersIds: [], // TODO: Remove once get ID from session
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
      userId: null,
      timeUntilUnreserved: '',
      status: '',
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
      this.reserveOffer();
    },
    arrayBufferToBase64(buffer) {
      return btoa(
          new Uint8Array(buffer).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    },
    getTimeUntilUnreserved() {
      if (this.unreservationDate) {
        const now = new Date();
        const unreservationDate = new Date(this.unreservationDate);
        const diffTime = Math.abs(unreservationDate - now);
        const hours = Math.floor(diffTime / (1000 * 60 * 60)).toString().padStart(2, '0');
        const minutes = Math.floor((diffTime / (1000 * 60)) % 60).toString().padStart(2, '0');
        const seconds = Math.floor((diffTime / 1000) % 60).toString().padStart(2, '0');
        this.timeUntilUnreserved = `Czas do odbioru: ${hours}:${minutes}:${seconds}`;
      }
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
          this.location = response.data.city + ', ' + response.data.street + ' (' + response.data.distance + ' km od Ciebie)';
        }
        this.condition = response.data.condition;
        this.amountOfStars = response.data.ownerRating;
        this.amountOfRatings = response.data.ownerReviewCount;
        this.userFirstName = response.data.ownerName;
        this.userSurname = response.data.ownerSurname;
        this.center = [response.data.latitude, response.data.longitude];
        this.mapDataLoaded = true;
        this.status = response.data.status;
        this.unreservationDate = response.data.unreservationDate;
        this.offerImage = await this.getImageData(response.data.photoId);
        this.userImage = await this.getImageData(response.data.ownerPhotoId);
        this.imageIsLoading = false;
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', {error: error.response.status});
      }
    },
    async getImageData(photoId) {
      try {
        const response = await axios.get(GATEWAY_ADDRESS + `/image/get/${photoId}`, {responseType: 'arraybuffer'});
        let image = this.arrayBufferToBase64(response.data);
        return `data:image/jpeg;base64,${image}`;
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', {error: error.response.status});
      }
    },
    async prepareDataToSend() {
      let formData = {
        offerId: this.id,
        receiverId: this.userId
      }
      return formData;
    },
    async reserveOffer() {
      let data = await this.prepareDataToSend();
      console.log('Data to send: ', data);
      axios.post(GATEWAY_ADDRESS + `/offer/reserve`, data, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      })
          .then(response => {
            console.log("Response: ", response.data);
            console.log('Offer ', this.id, ' was reserved successfully.');
            axios.get(GATEWAY_ADDRESS + `/offer/get/${this.id}`)
                .then(response => {
                  this.unreservationDate = response.data.unreservationDate;
                  console.log('Status: ', response.data.status);
                  this.status = 'RESERVED';
                  console.log('Unreservation date: ', this.unreservationDate);
                })
                .catch(error => {
                  console.error('ERROR: ', error);
                  this.emitter.emit('axiosError', {error: error.response.status});
                });
          })
          .catch(error => {
            console.error('ERROR: ', error);
            this.emitter.emit('axiosError', {error: error.response.status});
          });
    },
  },
  async mounted() {
    let token = localStorage.getItem('token');
    if (token) {
      this.userId = jwtDecode(token).id;
    }
    await this.getOfferData();
    this.getTimeUntilUnreserved();
    setInterval(this.getTimeUntilUnreserved, 1000);
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

.button {
  width: 100%;
  height: 100%;
}

.button-disabled {
  width: 100%;
  height: 100%;
  background-color: v-bind('COLORS.BUTTON_DISABLED');
  border: none;
}

.button-disabled:hover {
  background-color: v-bind('COLORS.BUTTON_DISABLED');
}
</style>

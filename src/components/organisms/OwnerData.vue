<template>
  <div id="owner-data">
    <div id="owner-header">
      <p> Informacje o użytkowniku </p>
    </div>
    <div id="owner-below" class="flex-container">
      <div id="owner-image" v-if="displaySelector">
        <ImageSelector v-if="ownerImage" :initial_image="ownerImage" display_small_font=true />
      </div>
      <div id="owner-image" v-else>
        <img v-if="ownerImage" :src="ownerImage" />
      </div>
      <div id="owner-info">
        <div id="owner-name">{{ ownerFirstName }}</div>
        <div id="owner-surname">{{ ownerSurname }}</div>
        <div id="owner-mail">{{ ownerEmail }}</div>
      </div>
      <div id="owner-stars">
        <div id="owner-stars-display">
          <div class="custom-star-card">
            <Stars ref="stars" :filledStars="ownerStars" :ratingsAmount="ownerRating"
              style="display: flex; flex-direction: column;" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Stars from '../atoms/Stars.vue';
import ImageSelector from '../atoms/ImageSelector.vue';
import axios from 'axios';

import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';
import { FONT_SIZES, COLORS } from "../../../public/Consts";
import { GATEWAY_ADDRESS } from "../../../public/Consts";

export default {
  name: 'OwnerData',
  components: {
    Stars,
    ImageSelector
  },
  props: {
    id: {
      type: String,
      required: true
    },
    displaySelector: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      ownerFirstName: '',
      ownerSurname: '',
      ownerEmail: '',
      ownerStars: '',
      ownerRating: '',
      ownerImageId: null,
      ownerImage: null,
    }
  },
  methods: {
    getStarIcon(index) {
      const fullStars = Math.floor(this.ownerStars);
      const decimalPart = this.ownerStars - fullStars;

      if (index <= fullStars) {
        return filledStar;
      } else if (decimalPart >= 0.75) {
        return filledStar;
      } else if (index === fullStars + 1 && decimalPart >= 0.25) {
        return halfFilledStar;
      } else {
        return unfilledStar;
      }
    },
    setupStarsElement() {
      const starsElement = this.$refs.stars.$el;
      const starRatings = starsElement.querySelector('.star-ratings');
      const stars = starsElement.querySelector('.stars');
      starsElement.insertBefore(stars, starRatings);
    },
    setupImageEmitter() {
      this.emitter.on('image-selected', (data) => {
        this.emitter.emit('image-selected', data);
      });
    },
    async getOwnerData() {
      axios.get(GATEWAY_ADDRESS + `/user/get/${this.id}`).then((response) => {
        console.log(response.data);
        this.ownerFirstName = response.data.name;
        this.ownerSurname = response.data.surname;
        this.ownerEmail = response.data.email;
        this.ownerImageId = response.data.photoId.id;
        // TODO - temporary
        this.ownerStars = 4.5;
        this.ownerRating = 123;
      })
        .then(() => {
          this.getImageData(this.ownerImageId);
        })
        .catch(error => {
          console.error('ERROR: ', error);

          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    async getImageData(photoId) {
      await axios.get(GATEWAY_ADDRESS + `/image/get/${photoId}`, { responseType: 'arraybuffer' }).then((response) => {
        let image_buffer = this.arrayBufferToBase64(response.data);
        this.ownerImage = `data:image/jpeg;base64,${image_buffer}`;
      }).catch(error => {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
    },
    arrayBufferToBase64(buffer) {
      return btoa(
        new Uint8Array(buffer).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    },
  },
  async mounted() {
    await this.getOwnerData();
  },
}

</script>

<style scoped>
#owner-data {
  height: 125px;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

#owner-header {
  width: 100%;
  height: 25%;
  margin-bottom: 0px;
  color: v-bind('COLORS.TEXT_PRIMARY');
  background-color: v-bind('COLORS.SECONDARY');
}

.flex-container {
  display: flex;
  width: 100%;
}

#owner-image {
  width: 85px;
  height: 85px;
  border-radius: 40%;
  margin-top: 0.3em;
  margin-left: 0.3em;
  overflow: hidden;
}

#owner-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

#owner-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 0.8em;
  margin-top: 0.2em;
  width: 40%;
}

#owner-stars {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-right: 1em;
  margin-top: 0.5em;
}

#owner-name,
#owner-surname,
#owner-mail {
  margin: 0;
}

.custom-star-card {
  display: flex;
  flex-direction: column;
  align-items: center;

}

.custom-star-card>>>.star-items {
  font-size: v-bind('FONT_SIZES.TITLE');
}

.custom-star-card>>>.star-ratings {
  font-size: v-bind('FONT_SIZES.PRIMARY');
  margin-right: 0;
}
</style>
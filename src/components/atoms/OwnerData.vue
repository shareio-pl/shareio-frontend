<template>
  <div id="owner-data">
    <div id="owner-header">
      <p> Informacje o autorze </p>
    </div>
    <div id="owner-below" class="flex-container">
      <div id="owner-image">
        <img :src="ownerImage" alt="">
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
import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';
import { DEFAULT_USER_PROFILE_IMAGE } from "../../../public/Consts";
import { FONT_SIZES, COLORS } from "../../../public/Consts";
import Stars from './Stars.vue';

export default {
  name: 'OwnerData',
  components: {
    Stars
  },
  props: {
    ownerFirstName: {
      type: String,
      required: true
    },
    ownerSurname: {
      type: String,
      required: true
    },
    ownerEmail: {
      type: String,
      required: true
    },
    ownerStars: {
      type: Number,
      required: true
    },
    ownerRating: {
      type: Number,
      required: true
    },
    ownerImage: {
      type: String,
      default: DEFAULT_USER_PROFILE_IMAGE
    },
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
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
    }
  },
  mounted() {
    const starsElement = this.$refs.stars.$el;
    const starRatings = starsElement.querySelector('.star-ratings');
    const stars = starsElement.querySelector('.stars');
    starsElement.insertBefore(stars, starRatings);
  }
}
</script>

<style scoped>
#owner-data {
  width: 40%;
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
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
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
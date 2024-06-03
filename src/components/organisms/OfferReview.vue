<template>
  <div class="offer-review">
    <div class="offer-review-row">
      <span class="offer-preview-review">
        <OfferPreview :id="id" style="width: 100%;" />
      </span>
      <span class="offer-review-rating" @click="handleClick">
        <div v-show="!isReviewDone" class="offer-review-stars" ref="stars">
          <FontAwesomeIcon v-for="index in 5" :key="index" :icon="getStarIcon(index)" class="star" />
        </div>
        <div v-show="isReviewDone">
          <p class="offer-rated-description"> Oferta została już oceniona! </p>
        </div>
      </span>
    </div>
  </div>
</template>

<script>
import { FONTS, COLORS, FONT_SIZES, GATEWAY_ADDRESS } from "../../../public/Consts";

import axios from "axios";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';

import OfferPreview from "@/components/organisms/OfferPreview.vue";

export default {
  name: "OfferReview",
  components: {
    OfferPreview,
    FontAwesomeIcon
  },
  data() {
    return {
      FONTS: FONTS,
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      localFilledStars: '',
      isReviewDone: false,
    };
  },
  props: {
    id: {
      type: Number,
      required: true
    },
  },
  methods: {
    handleClick(event) {
      if (this.isReviewDone) {
        return;
      }
      let clickedStar = this.detectClickedStar(event);
      this.setStars(clickedStar);
      this.sendReview();
    },
    detectClickedStar(event) {
      console.log(event);
      const starContainer = this.$refs.stars;
      const firstStar = starContainer.querySelector('svg');
      const starStart = firstStar.getBoundingClientRect().left;
      const clickPosition = event.clientX - starStart;
      const starWidth = firstStar.getBoundingClientRect().width;
      const clickedStar = Math.round(clickPosition / starWidth * 2) / 2;
      return clickedStar;
    },
    getStarIcon(index) {
      if (index <= this.localFilledStars) {
        return filledStar;
      } else if (index - 0.5 === this.localFilledStars) {
        return halfFilledStar;
      } else {
        return unfilledStar;
      }
    },
    setStars(value) {
      console.log(value);
      this.localFilledStars = value;
    },
    sendReview() {
      // TODO: remove
      console.log('Sending review with score: ', this.localFilledStars);
      axios.post(GATEWAY_ADDRESS + '/offer/addReview', {
        offerId: this.id,
        reviewValue: this.localFilledStars
      }).then((response) => {
        console.log(response);
        this.isReviewDone = true;
      })
        .catch((error) => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    setupIsReviewNotDoneListener() {
      this.emitter.on('review-not-done', (data) => {
        console.log('Received review-not-done event with data: ', data);
        if (data.offerId === this.id) {
          this.isReviewDone = false;
        }
      });
    },
    mounted() {
      this.setupIsReviewNotDoneListener();
    }
  },
}

</script>

<style scoped>
.offer-review-row {
  display: flex;
  align-items: center;
}

.offer-preview-review {
  width: 70%
}

.offer-review-rating {
  width: 30%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.offer-review-stars {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 2%;
  margin-bottom: 2%;
  font-size: v-bind('FONT_SIZES.TITLE');
  color: v-bind('COLORS.OFFER_BACKGROUND');
  padding: 3%;
  padding-top: 1%;
  cursor: pointer;
}

.offer-rated-description {
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.PRIMARY');
}
</style>
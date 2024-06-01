<template>
  <div class="offer-review">
    <div class="offer-review-row">
      <span class="offer-preview-review">
        <OfferPreview :id="id" style="width: 100%;" />
      </span>
      <span class="offer-review-rating" @click="handleClick">
        <div class="offer-review-stars" ref="stars">
          <FontAwesomeIcon v-for="index in 5" :key="index" :icon="getStarIcon(index)" class="star" />
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
      axios.post(GATEWAY_ADDRESS + '/offer/addReview', {
        offerId: this.id,
        reviewValue: this.localFilledStars
      }).then((response) => {
        console.log(response);
      })
        .catch((error) => {
          console.log(error);
        });
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
}

.offer-review-stars {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-left: 2%;
  margin-bottom: 2%;
  font-size: v-bind('FONT_SIZES.TITLE');
  padding: 3%;
  padding-top: 1%;
  cursor: pointer;
}
</style>
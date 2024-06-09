<template>
  <div class="offer-review">
    <div class="offer-review-row">
      <span class="offer-preview-review">
        <OfferPreview :id="id" style="width: 100%;" />
      </span>
      <span class="offer-review-rating">
        <transition-group name="fade" mode="out-in">
          <div v-if="!isReviewDone && offerLoaded" key="stars">
            <div class="offer-review-stars" ref="stars" @mousemove="handleMouseMove" @mouseleave="handleMouseLeave" @click="handleClick">
              <FontAwesomeIcon v-for="index in 5" :key="index" :icon="getStarIcon(index)" class="star" />
            </div>
            <ButtonPrimary @click="confirmReview" class="confirm-button"
                           :button-text="buttonText" style="width: 60%;margin-left:12%;"/>
          </div>
          <div v-if="isReviewDone && offerLoaded" key="message">
            <p class="offer-rated-description"> Oferta została już oceniona! </p>
          </div>
        </transition-group>
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
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

export default {
  name: "OfferReview",
  components: {
    ButtonPrimary,
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
      offerLoaded: false,
      buttonText: "Zatwierdź",
      hoverRating: 0
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
    },
    detectClickedStar(event) {
      const starContainer = this.$refs.stars;
      const firstStar = starContainer.querySelector('svg');
      const starStart = firstStar.getBoundingClientRect().left;
      const clickPosition = event.clientX - starStart;
      const starWidth = firstStar.getBoundingClientRect().width;
      const clickedStar = Math.round(clickPosition / starWidth * 2) / 2;
      return clickedStar;
    },
    handleMouseMove(event) {
      let hoverStar = this.detectClickedStar(event);
      this.hoverRating = hoverStar;
    },
    handleMouseLeave() {
      this.hoverRating = 0;
    },
    getStarIcon(index) {
      let rating = this.hoverRating || this.localFilledStars;
      if (index <= rating) {
        return filledStar;
      } else if (index - 0.5 === rating) {
        return halfFilledStar;
      } else {
        return unfilledStar;
      }
    },
    setStars(value) {
      this.localFilledStars = value;
    },
    confirmReview() {
      this.sendReview();
    },
    sendReview() {
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
      this.emitter.on('review-done', (data) => {
        if (data.offerId === this.id) {
          this.isReviewDone = true;
        }
      });
    },
    setupOfferLoadedListener() {
      this.emitter.on('offer-loaded', (data) => {
        if (data.id === this.id) {
          this.offerLoaded = true;
        }
      });
    }
  },
  mounted() {
    this.setupIsReviewNotDoneListener();
    this.setupOfferLoadedListener();
  }
}
</script>
<style scoped>
.offer-review-row {
  display: flex;
  align-items: center;
}

.offer-preview-review {
  width: 80%;
}

.offer-review-rating {
  width: 20%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.offer-review-stars {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 1.3em;
  margin-bottom: 2%;
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.OFFER_BACKGROUND');
  padding: 3%;
  padding-top: 1%;
  cursor: pointer;
}

.offer-rated-description {
  margin-left: 1em;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.PRIMARY');
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity .0s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

/* Media queries for responsiveness */
@media (max-width: 1000px) {
  .offer-review-row {
    flex-direction: column;
  }

  .offer-preview-review,
  .offer-review-rating {
    width: 100%;
    margin-bottom: 1em;
  }

  .offer-review-rating {
    margin-left: 0;
  }

  .offer-review-stars {
    font-size: v-bind('FONT_SIZES.PRIMARY');
    padding: 2%;
  }

  .offer-rated-description {
    margin-left: 0;
    font-size: v-bind('FONT_SIZES.SECONDARY');
    text-align: center;
  }

  .confirm-button {
    width: 100%;
    margin-left: 0;
  }
}
</style>

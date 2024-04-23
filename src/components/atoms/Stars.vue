<template>
  <div class="star-card">
    <span class="star-ratings"> ({{ ratingsAmount }}) </span>
    <div class="stars">
      <font-awesome-icon class="star-items" v-for="star in 5" :key="star" :icon="getStarIcon(star)" />
    </div>
  </div>
</template>

<script>
import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { FONT_SIZES } from '../../../public/Consts';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Stars",
  props: {
    filledStars: {
      type: Number,
      required: true,
    },
    ratingsAmount: {
      type: Number,
      required: false,
    }
  },
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
    }
  },
  methods: {
    getStarIcon(index) {
      const fullStars = Math.floor(this.filledStars);
      const decimalPart = this.filledStars - fullStars;

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
}
</script>

<style scoped>
.star-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.star-ratings {
  font-size: v-bind('FONT_SIZES.SECONDARY');
  margin-right: 14%;
}

.stars{
  display: flex;
}

.star-items {
  font-size: v-bind('FONT_SIZES.STARS');
}
</style>

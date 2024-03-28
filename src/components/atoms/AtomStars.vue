<template>
  <div class="star-rating">
    <font-awesome-icon v-for="i in 5" :key="i" :icon="getStarIcon(i)" />
  </div>
</template>

<script>
import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  name: "AtomStars",
  props: {
    filledStars: {
      type: Number,
      required: true,
    }
  },
  components: {
    FontAwesomeIcon 
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

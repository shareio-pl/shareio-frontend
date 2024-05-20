<template>
  <div class="filter-open-rate">
    <div class="filter-header">
      <div class="filter-header-left">
        <FontAwesomeIcon :icon="propIcon" id="prop-icon" />
        <div class="filter-name">{{ filterName }}</div>
      </div>
      <FontAwesomeIcon :icon="showStars ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
    <div class="filter-open-rate-stars" @click="handleClick">
      <div class="filter-stars" v-show="showStars" ref="stars">
        <FontAwesomeIcon v-for="index in 5" :key="index" :icon="getStarIcon(index)" class="star" />
      </div>
    </div>
  </div>
</template>

<script>
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { faStar as unfilledStar, faStarHalfAlt as halfFilledStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as filledStar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FilterOpenRate",
  components: {
    FontAwesomeIcon
  },
  props: {
    filterName: {
      type: String,
      required: true
    },
    filledStars: {
      type: Number,
      required: false
    },
    propIcon: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      localFilledStars: this.filledStars,
      iconChevronUp: faChevronUp,
      iconChevronDown: faChevronDown,
      showStars: true,
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS
    }
  },
  methods: {
    handleClick(event) {
      let clickedStar = this.detectClickedStar(event);
      this.setStars(clickedStar);
      this.emitter.emit('filter-stars', { starsAmount: this.localFilledStars });
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
    onArrowClick() {
      this.showStars = !this.showStars;
    }
  }
}
</script>

<style scoped>
.filter-open-rate {
  display: flex;
  flex-direction: column;
  width: 25%;
  background-color: v-bind('COLORS.PRIMARY');
  border-radius: 0.5em;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}

.filter-header {
  display: flex;
  justify-content: space-between;
  margin-left: 2%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  padding: 3%;
  cursor: pointer;
}

.filter-header-left {
  display: flex;
  justify-content: flex-start;
  width: 90%;
}

.filter-stars {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-left: 2%;
  margin-bottom: 2%;
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  padding: 3%;
  padding-top: 1%;
  cursor: pointer;
}

#arrow-icon {
  cursor: pointer;
  margin-right: 1%;
  margin-top: 1%;
}

#prop-icon {
  width: 10%;
}
</style>

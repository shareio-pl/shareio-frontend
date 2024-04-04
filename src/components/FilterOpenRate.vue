<template>
  <div class="filter-name">{{ filterName }}</div>
  <div class="filter-open-rate" @click="handleClick">
    <Stars ref="stars" :filledStars="localFilledStars" />
  </div>
</template>

<script>
import Stars from './atoms/Stars.vue';
import { FONT_SIZES } from '../../public/Consts';

export default {
  name: "FilterOpenRate",
  components: {
    Stars
  },
  props: {
    filterName: {
      type: String,
      required: true
    },
    filledStars: {
      type: Number,
      required: false
    }
  },
  data() {
    return {
      localFilledStars: this.filledStars,
      FONT_SIZES: FONT_SIZES,
    }
  },
  methods: {
    setStars(value) {
      console.log(value);
      this.localFilledStars = value;
    },
    handleClick(event) {
      const starContainer = this.$refs.stars.$el;
      const firstStar = starContainer.querySelector('svg');
      const starStart = firstStar.getBoundingClientRect().left;
      const clickPosition = event.clientX - starStart;
      const starWidth = firstStar.getBoundingClientRect().width;
      const clickedStar = Math.round(clickPosition / starWidth * 2) / 2;
      this.setStars(clickedStar);
    }
  }
}
</script>

<style scoped>
.filter-name {
  margin-left: min(5%, 10px);
  font-size: v-bind('FONT_SIZES.PRIMARY');
  text-align: left;
}

.filter-open-rate {
  margin-left: min(5%, 10px);
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
</style>
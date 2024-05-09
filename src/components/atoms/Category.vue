<template>
  <div class="category">
    <span class="dot"> &bull;</span>
    <span class="category-display"> {{ category }}</span>
    <span class="number">({{ numberOfOffers }})</span>
  </div>
</template>

<script>
import { FONT_SIZES } from "../../../public/Consts";
import { COLORS } from "../../../public/Consts";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Category",
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
    };
  },
  props: {
    category: {
      type: String,
      required: true,
    },
    numberOfOffers: {
      type: Number,
      required: true,
    },
  },
};
</script>

<style scoped>
.category {
  height: 30px;
  margin-left: min(5%, 10px);
  width: 100%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.category:hover {
  cursor: pointer;
  color: v-bind('COLORS.TEXT_HOVER');
}

.dot {
  margin-right: 10px;
  float: left;
}

.category-display {
  float: left;
}

.number {
  float: right;
  margin-right: min(10%, 20px);
}
</style>
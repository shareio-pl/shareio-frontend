<template>
  <div class="category">
    <span>&bull; {{ category }}</span>
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
  display: flex;
  justify-content: space-between;
  margin-left: min(5%, 10px);
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.category:hover {
  cursor: pointer;
  color: v-bind('COLORS.TEXT_HOVER');
}

.category span {
  margin-right: min(5%, 10px);
}
</style>
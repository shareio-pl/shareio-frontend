<template>
  <button class="button-secondary">
    {{ buttonText }}
  </button>
</template>

<script>
import { COLORS, FONT_SIZES } from '../../../public/Consts';
export default {
  name: 'ButtonSecondary',
  props: {
    buttonText: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    };
  },
}
</script>

<style scoped>
.button-secondary {
  width: 20%;
  height: 45px;
  background-color: v-bind('COLORS.BUTTON_SECONDARY_NORMAL');
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}

.button-secondary:hover {
  background-color: v-bind('COLORS.BUTTON_SECONDARY_HOVER');
}
</style>

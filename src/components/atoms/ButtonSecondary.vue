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
  height: 45px;
  background-color: v-bind('COLORS.BUTTON_SECONDARY_NORMAL');
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: calc(20% + 0.5vw);
  font-size: calc( 6px + 1.1vw );

}

.button-secondary:hover {
  background-color: v-bind('COLORS.BUTTON_SECONDARY_HOVER');
}
</style>

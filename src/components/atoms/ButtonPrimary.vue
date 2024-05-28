<template>
  <button class="button-primary">
    {{ buttonText }}
  </button>
</template>

<script>
import { COLORS, FONT_SIZES } from '../../../public/Consts';
export default {
  name: 'ButtonPrimary',
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
.button-primary {
  width: calc(20% + 0.5vw);
  height: 45px;
  margin-left: 2%;
  border-radius: 20px;
  background-color: v-bind('COLORS.BUTTON_PRIMARY_NORMAL');
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: calc( 7px + 1.1vw );
  line-height: 14px;
}

.button-primary:hover {
  background-color: v-bind('COLORS.BUTTON_PRIMARY_HOVER');
}
</style>
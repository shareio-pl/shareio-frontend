<template>
  <button class="button-primary">
    {{ buttonText }}
  </button>
</template>

<script>
import { COLORS } from '../../../public/Consts';
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
    };
  },
}
</script>

<style scoped>
.button-primary {
  width: 9%;
  height: 45px;
  margin-left: 2%;
  border-radius: 20px;
  background-color: v-bind('COLORS.BUTTON_PRIMARY_NORMAL');
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}

.button-primary:hover {
  background-color: v-bind('COLORS.BUTTON_PRIMARY_HOVER');
}
</style>
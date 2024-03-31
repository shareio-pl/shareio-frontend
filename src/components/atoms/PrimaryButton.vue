<template>
  <button class="primary-button" @click="handleClick">
    {{ buttonText }}
  </button>
</template>

<script>
import { COLORS } from '../../../public/Consts';
export default {
  name: 'PrimaryButton',
  props: {
    buttonText: {
      type: String,
      required: true
    }
  },
  methods: {
    handleClick() {
      this.$emit('click');
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
.primary-button {
  width: 9%;
  height: 45px;
  margin-left: 2%;
  border-radius: 20px;
  background-color: v-bind('COLORS.BUTTON_PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.primary-button:hover {
  background-color: v-bind('COLORS.BUTTON_SECONDARY');
  counter-reset: v-bind('COLORS.TEXT_SECONDARY');
}
</style>


<template>
  <div class="date-picker">
    <VueDatePicker class="datepicker" v-model="date" :placeholder="placeholder" utc @update:model-value="handleDate"
      ref="datepicker" :max-date="new Date().setFullYear(new Date().getFullYear() - 13)"
      :start-date="new Date().setFullYear(new Date().getFullYear() - 13)" />
  </div>
</template>

<script>
/* Documentation: https://vue3datepicker.com/props/modes/ */
import { FONT_SIZES, COLORS, FONTS } from "../../../public/Consts";

import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

export default {
  components: { VueDatePicker },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      FONTS: FONTS,
      date: null,
    };
  },
  props: {
    placeholder: {
      type: String,
      default: '',
    },
  },
  methods: {
    handleDate(value) {
      console.log(value);
      this.emitter.emit('date', { date: value });
    },
  },
}
</script>

<style >
.date-picker {
  display: flex;
  justify-content: center;
  align-items: center;
}

.dp__theme_light {
  width: 40%;
  height: 150%;
  --dp-text-color: gray;
  --dp-font-size: v-bind("FONT_SIZES.PRIMARY");
  --dp-background-color: v-bind("COLORS.OFFER_BACKGROUND");
  --dp-font-family: v-bind("FONTS.PRIMARY");
  --dp-common-padding: 0.75rem;
}
</style>
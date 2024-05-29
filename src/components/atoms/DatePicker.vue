<template>
  <div class="date-picker">
    <VueDatePicker class="datepicker" v-model="date" :placeholder="placeholder" utc @update:model-value="handleDate"
      ref="datepicker" :enable-time-picker="false" :max-date=current_date_minus_eighteen
      :start-date=current_date_minus_eighteen />
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
      // Looks inelegant, but setFullYear returns a timestamp, not date object. 
      // This way there won't be warnings about wrong type.
      current_date_minus_eighteen: new Date(new Date().setFullYear(new Date().getFullYear() - 18))
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
<template>
  <div class="date-picker">
    <VueDatePicker :class="['datepicker', { 'error-border': error.active }]" locale="pl-PL" cancelText="Anuluj"
      selectText="Wybierz" v-model="date" :placeholder="placeholder" utc @update:model-value="handleDate" ref="datepicker"
      :enable-time-picker="false" :max-date=current_date_minus_eighteen :start-date=current_date_minus_eighteen />
    <p class="field-input-paragraph" v-if="error.active && error.message !== ''">{{ error.message }}</p>
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
    error: {
      type: Object,
      default: () => ({ active: false, message: "" }),
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
}

.dp__theme_light {
  --dp-text-color: gray;
  --dp-font-size: v-bind("FONT_SIZES.PRIMARY");
  --dp-background-color: v-bind("COLORS.OFFER_BACKGROUND");
  --dp-font-family: v-bind("FONTS.PRIMARY");
  --dp-common-padding: 0.75rem;
}

.dp__input_wrap {
  width: 250%;
  height: max-content;
  border-bottom: 2px solid transparent
}

.dp__input_wrap:hover {
  border-bottom: 2px solid v-bind("COLORS.NOTIFICATION_PRIMARY_FOCUS");
}

.dp__pointer {
  height: 55.6px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.error-border .dp__input_wrap {
  border-bottom: 2px solid v-bind("COLORS.NOTIFICATION_PRIMARY_ERROR") !important;
  transition: border-bottom 1s ease-in-out;
}

.field-input-paragraph {
  color: v-bind("COLORS.PRIMARY");
}
</style>
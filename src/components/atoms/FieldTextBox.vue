<template>
  <div class="field">
    <label class="field-label" v-bind:for="modelValue">{{ label }}</label>
    <textarea class="field-input" :type="type" :value="modelValue" @input="updateValue" placeholder="&nbsp;"
      :class="{ 'error-border': error.active }" />
    <p class="field-input-paragraph" v-if="error.active">{{ error.message }}</p>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from '../../../public/Consts';

export default {
  name: "FieldTextBox",
  props: {
    modelValue: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      default: "text",
    },
    error: {
      type: Object,
    },
    label: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
    };
  },
  methods: {
    updateValue(event) {
      this.emitter.on("update:modelValue", event.target.value);
    },
  },
};
</script>

<style scoped>
.field-input {
  width: 100%;
  height: 300px;
  appearance: none;
  padding: 0.75rem;
  border: none;
  border-bottom: 2px solid transparent;
  outline: none;
  border-radius: 0.25rem;
  color: v-bind("COLORS.TEXT_SECONDARY");
  font-size: v-bind("FONT_SIZES.PRIMARY");
  background-color: v-bind("COLORS.OFFER_BACKGROUND");
  transition: border-bottom .5s ease-in-out;
}

textarea {
  resize: none;
}

.field-input:focus {
  border-bottom: 2px solid greenyellow;
}

.field-input:not(:placeholder-shown) {
  border-bottom: 2px solid blue;
}

label {
  position: relative;
  width: 100%;
  overflow: hidden;
}

span {
  position: absolute;
  left: 50%;
  top: 25%;
  font-size: v-bind("FONT_SIZES.PRIMARY");
  color: gray;
  transform-origin: 0 0;
  transform: translate(-50%, 0);
  transition: all .5s ease;
  pointer-events: none;
}

input:focus+span {
  transform: translate(-50%, -50%) scale(.75);
}

input:not(:placeholder-shown)+span {
  transform: translate(-50%, -50%) scale(.75)
}

.error-border {
  border-bottom: 2px solid red !important;
  transition: border-bottom 1s ease-in-out;
}
</style>
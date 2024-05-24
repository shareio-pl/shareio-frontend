<template>
  <div class="field">
    <label class="field-label" v-bind:for="modelValue">
      <textarea class="field-input" :type="type" :value="modelValue" @input="updateValue" placeholder="&nbsp;"
        :class="{ 'error-border': error.active }" />
      <span>{{ label }}</span>
    </label>
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
      // Same reasoning as in the FieldInput
      this.$emit("update:modelValue", event.target.value);
    },
  },
};
</script>

<style scoped>
.field-input {
  width: 100%;
  height: 200px;
  appearance: none;
  padding: 1.25rem;
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
  border-bottom: 2px solid v-bind("COLORS.FORM_FOCUS");
}

.field-input:not(:placeholder-shown) {
  border-bottom: 2px solid v-bind("COLORS.FORM_CORRECT");
}

label {
  position: relative;
  width: 100%;
  overflow: hidden;
}

span {
  position: absolute;
  left: 50%;
  top: 5%;
  font-size: v-bind("FONT_SIZES.PRIMARY");
  color: gray;
  transform-origin: 0 0;
  transform: translate(-50%, 0);
  transition: all .5s ease;
  pointer-events: none;
}

textarea:focus+span {
  transform: translate(-50%, -50%) scale(.75);
}

textarea:not(:placeholder-shown)+span {
  transform: translate(-50%, -50%) scale(.75)
}

.error-border {
  border-bottom: 2px solid v-bind("COLORS.FORM_ERROR") !important;
  transition: border-bottom 1s ease-in-out;
}
</style>
<template>
  <div class="field">
    <label class="field-label" v-bind:for="modelValue">
      <input class="field-input" :type="type" :value="modelValue" @input="updateValue" :id="modelValue"
        placeholder="&nbsp;" :class="{ 'error-border': error.active }">
      <span>{{ label }}</span>
    </label>
    <p class="field-input-paragraph" v-if="error.active">{{ error.message }}</p>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from '../../../public/Consts';

export default {
  name: "FieldInput",
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
      required: false,
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
      // I would prefer to stick with this.$emit for this one particular task. 
      // It's just very convenient to be able to just use v-model on the parent
      // Compared to having to write in the mounted the logic, also as I have a 
      // lot of forms I'd have to write a lot of ifs and also include name of the form
      // in the event
      this.$emit("update:modelValue", event.target.value);
    },
  },
};
</script>

<style scoped>
.field {
  position: relative;
  margin-top: 1.5rem;
}

.field-input {
  width: 100%;
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
  border-bottom: 2px solid v-bind("COLORS.FORM_ERROR") !important;
  transition: border-bottom 1s ease-in-out;
}
</style>

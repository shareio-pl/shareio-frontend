<template>
  <div id="option">
    <div class="checkbox-and-label" @click="toggleCheck">
      <div class="checkbox-circle">
        <div v-if="optionName === selectedOption" class="checked"></div>
      </div>
      <span class="checkbox-name">{{ displayName }}</span>
    </div>
  </div>
</template>


<script>
import { COLORS, FONT_SIZES } from "../../../public/Consts";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Option",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      isSelected: false,
    }
  },
  props: {
    displayName: {
      type: String,
      required: true,
    },
    optionName: {
      type: String,
      required: true,
    },
    selectedOption: {
      type: String,
      default: null,
    },
  },
  methods: {
    toggleCheck() {
      this.emitter.emit('selected-option', { displayName: this.displayName, optionName: this.optionName });
    }
  },
}
</script>

<style scoped>
#option {
  display: flex;
  flex-direction: column;
  height: 50%;
  margin-left: 2%;
  font-size: calc(9px + 0.7vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
  flex-grow: 1;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.checkbox-and-label {
  display: flex;
  align-items: center;
}

.checkbox-circle {
  max-width: 20px;
  max-height: 20px;
  min-width: 20px;
  min-height: 20px;
  border-radius: 50%;
  border: 1px solid v-bind('COLORS.BORDER_BLACK');
  background: v-bind('COLORS.OFFER_FOREGROUND');
  position: relative;
  cursor: pointer;
  margin-right: 3%;
  margin-left: 1%;
}

.checked {
  width: 65%;
  height: 65%;
  border-radius: 50%;
  background-color: v-bind('COLORS.BORDER_BLACK');
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

@media screen and (max-width: 850px) {
  .checkbox-circle {
    max-width: 15px;
    max-height: 15px;
    min-width: 15px;
    min-height: 15px;
  }
}
</style>
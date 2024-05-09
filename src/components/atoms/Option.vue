<template>
  <div id="option">
    <div class="checkbox-and-label" @click="toggleCheck">
      <div class="checkbox-circle">
        <div v-if="name === selectedOption" class="checked"></div>
      </div>
      <span class="checkbox-name">{{ name }}</span>
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
    name: {
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
      this.emitter.emit('selected-option', { optionName: this.name });
    }
  },
}
</script>

<style scoped>
#option {
  display: flex;
  flex-direction: column;
  background-color: v-bind('COLORS.MENU_WHITE');
  height: 50%;
  margin-left: 2%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.checkbox-and-label {
  display: flex;
  align-items: center;
}

.checkbox-circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 1px solid v-bind('COLORS.BORDER_BLACK');
  position: relative;
  cursor: pointer;
  margin-right: 3%;
  margin-left: 1%;
}

.checked {
  width: 70%;
  height: 70%;
  border-radius: 50%;
  background-color: v-bind('COLORS.BORDER_BLACK');
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
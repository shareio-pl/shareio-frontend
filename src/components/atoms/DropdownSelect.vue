<template>
  <div class="dropdown-select">
    <div class="dropdown-select-header">
      <div class="dropdown-select-header-left">
        <FontAwesomeIcon :icon="iconChevronDown" class="arrow-icon" />
      </div>
    </div>
    <div class="select-wrapper">
      <select :class="{ 'selected': selectedOption !== '' }" v-model="selectedOption"
        @change="onSortChange(selectedOption)">
        <option disabled value="">{{ placeholder }}</option>
        <option v-for="(option, index) in options" :key="index" :value="option.category">
          {{ option.displayName }}
        </option>
      </select>
      <font-awesome-icon :icon="iconChevronDown" class="arrow-icon" />
    </div>
  </div>
</template>

<script>
import { faChevronDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { COLORS, FONT_SIZES, FONTS } from '../../../public/Consts';

export default {
  name: "DropdownSelect",
  components: {
    FontAwesomeIcon
  },
  props: {
    options: {
      type: Array,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    placeholder: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      FONTS: FONTS,
      iconChevronDown: faChevronDown,
      selectedOption: '',
    };
  },
  methods: {
    onSortChange(option) {
      console.log('Option has been changed to: ', option);
      this.emitter.emit('dropdown-change', { option: option, type: this.type });
    },
  },
  mounted() {
    console.log("Options: ", this.options);
  }
}
</script>

<style scoped>
.select-wrapper {
  position: relative;
  display: inline-block;
  width: 100%;
  background-color: v-bind('COLORS.PRIMARY');
  border-radius: 0.25rem;
}

.select-wrapper select {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-color: v-bind('COLORS.PRIMARY');
  width: 100%;
  height: 45px;
  border: none;
}

.select-wrapper select.selected {
  color: v-bind("COLORS.TEXT_SECONDARY");
}

option {
  background-color: v-bind('COLORS.PRIMARY');
  font-family: v-bind("FONTS.PRIMARY");
  color: v-bind("COLORS.TEXT_SECONDARY");
}

.arrow-icon {
  position: absolute;
  right: 1%;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
}

.dropdown-select-header {
  display: flex;
  justify-content: space-between;
  margin-left: 2%;
  padding: 3%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: gray;
  cursor: pointer;
}

.dropdown-select-header-left {
  display: flex;
  align-items: center;
  width: 90%;
}
</style>

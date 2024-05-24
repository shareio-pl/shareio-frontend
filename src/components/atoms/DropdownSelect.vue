<template>
  <div class="select-wrapper">
    <select v-model="selectedOption" @change="onSortChange(selectedOption)">
      <option v-for="(option, index) in options" :key="index" :value="option.category">
        {{ option.displayName }}
      </option>
    </select>
    <font-awesome-icon :icon="iconChevronDown" class="arrow-icon" />
  </div>
</template>

<script>
import { faChevronDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { COLORS } from '../../../public/Consts';

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
    }
  },
  data() {
    return {
      COLORS: COLORS,
      iconChevronDown: faChevronDown,
      selectedOption: "Najbliższe",
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
  width: 45%;
}

.select-wrapper select {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-color: v-bind('COLORS.MENU_WHITE');
  width: 100%;
  height: 45px;
}

.arrow-icon {
  position: absolute;
  right: 1%;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
}
</style>

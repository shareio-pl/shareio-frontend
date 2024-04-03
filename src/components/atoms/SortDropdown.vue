<template>
  <div class="select-wrapper">
    <select>
      <option v-for="(option, index) in options" :key="index">
        {{ option }}
      </option>
    </select>
    <font-awesome-icon :icon="dropdownIcon" class="arrow-icon" />
  </div>
</template>

<script>
import { faChevronDown } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { COLORS } from '../../../public/Consts';

export default {
  name: "SortDropdown",
  components: {
    FontAwesomeIcon
  },
  props: {
    options: {
      type: Array,
      required: true,
    },
    dropdownIcon: {
      type: Object,
      default: faChevronDown
    },
  },
  data() {
    return {
      COLORS: COLORS,
    };
  },
}
</script>

<style scoped>
.select-wrapper {
  position: relative;
  display: inline-block;
  width: 90%;
}

.select-wrapper select {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-color: v-bind('COLORS.MENU_WHITE');
  width: 100%;
}

.arrow-icon {
  position: absolute;
  right: 0.5%;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
}
</style>
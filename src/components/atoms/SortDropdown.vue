<template>
  <div class="select-wrapper">
    <select  v-model="selectedOption" @change="onSortChange(selectedOption)">
      <option v-for="(option, index) in options" :key="index" :value="option">
        Sortuj: {{ option }}
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
  name: "SortDropdown",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      COLORS: COLORS,
      options: ["Najbliższe", "Najdalsze", "Najnowsze", "Najstarsze", "Najwyżej oceniane"],
      iconChevronDown: faChevronDown,
      selectedOption: "Najbliższe",
    };
  },
  methods:{
    onSortChange(option)
    {
      console.log('Sorting changed to: ', option);
      this.emitter.emit('sortChange', {option: option});
    }
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

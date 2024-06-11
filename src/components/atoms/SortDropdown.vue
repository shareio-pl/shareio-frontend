<template>
  <div class="select-wrapper">
    <select v-model="selectedOption" @change="onSortChange(selectedOption)">
      <option v-for="(option, index) in options" :key="index" :value="option">
        &nbsp; Sortuj: {{ option.displayName }}
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
      options: [
        { displayName: "Najbliższe", sortType: "CLOSEST" },
        { displayName: "Najdalsze", sortType: "FURTHEST" },
        { displayName: "Najnowsze", sortType: "NEWEST" },
        { displayName: "Najstarsze", sortType: "OLDEST" },
        { displayName: "Najwyżej oceniane", sortType: "HIGHEST_RATED" }
      ],
      iconChevronDown: faChevronDown,
      selectedOption: { displayName: "Najbliższe", sortType: "CLOSEST" },
    };
  },
  methods: {
    onSortChange(option) {
      console.log('Sorting changed to: ', option);
      this.emitter.emit('sortChange', { option: option.sortType });
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
  right: 3%;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
}

option {

}

@media (max-width: 900px) {
  .arrow-icon {
    display: none;
  }
}

</style>

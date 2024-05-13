<template>
  <div class="filter-options">
    <div class="filter-header">
      <div class="filter-name">{{ name }}</div>
      <FontAwesomeIcon :icon="showOptions ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
    <div class="filter-options-list" v-show="showOptions">
      <Option v-for="option in options" :key="option.name" :name="option.name" :selected-option="selectedOption" />
    </div>
  </div>
</template>

<script>
import Option from "./Option.vue";
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FilterOptions",
  components: {
    FontAwesomeIcon, Option
  },
  props: {
    name: {
      type: String,
      required: true
    },
  },
  data() {
    return {
      iconChevronUp: faChevronUp,
      iconChevronDown: faChevronDown,
      showOptions: true,
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      options: '',
      /*options: [
        { name: 'Test1' },
        { name: 'Test2' },
        { name: 'Test3' },
        { name: 'Test4' },
        { name: 'Test5' },
        { name: 'Longer test name'},
        { name: "Długość"}
      ],*/
      selectedOption: null,
    }
  },
  methods: {
    onArrowClick() {
      this.showOptions = !this.showOptions;
    },
    setSelectEmitter() {
      this.emitter.on('selected-option', (data) => {
        console.log('Received selected option in FilterOptions: ', data.optionName);
        this.selectedOption = data.optionName;
        this.emitter.emit('filter-selected', { optionName: data.optionName });
      });
    },
    getConditions() {
      // TODO
    }
  },
  mounted() {
    this.setSelectEmitter();
    this.getConditions();
  }
}
</script>

<style scoped>
.filter-options {
  display: flex;
  flex-direction: column;
  width: 25%;
  background-color: v-bind('COLORS.MENU_WHITE');
  border: solid v-bind('COLORS.BORDER_BLACK');
}

#arrow-icon {
  cursor: pointer;
  margin-right: 1%;
  margin-top: 1%;
}

.filter-header {
  display: flex;
  justify-content: space-between;
  margin-left: 2%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.filter-options-list {
  display: flex;
  flex-direction: column;
  margin-left: 1%;
  margin-bottom: 2%;
}
</style>
<template>
  <div class="filter-options">
    <div class="filter-header">
      <div class="filter-header-left">
        <FontAwesomeIcon :icon="propIcon" id="prop-icon" />
        <div class="filter-name">{{ name }}</div>
      </div>
      <FontAwesomeIcon :icon="showOptions ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
    <div class="filter-options-list" v-show="showOptions">
      <div class="option" v-for="option in options" :key="option.name">
        <Option :name="option.name" :selected-option="selectedOption" />
      </div>
    </div>
  </div>
</template>

<script>
import Option from "./Option.vue";
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

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
    propIcon: {
      type: Object,
      required: false
    },
    clear: {
      type: Number,
      required: false
    }
  },
  data() {
    return {
      iconChevronUp: faChevronUp,
      iconChevronDown: faChevronDown,
      showOptions: true,
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      options: [],
      selectedOption: null,
    }
  },
  methods: {
    onArrowClick() {
      this.showOptions = !this.showOptions;
    },
    setSelectEmitter() {
      this.emitter.on('selected-option', (data) => {
        this.selectedOption = data.optionName;
        this.emitter.emit('filter-options', { optionName: data.optionName });
      });
    },
    getConditions() {
      axios.get(GATEWAY_ADDRESS + `/offer/getConditions`).then((response) => {
        console.log('Received conditions: ', response.data);
        this.options = response.data.conditions.map(condition => ({ name: condition.displayName }));
      });
    },
    clearFilter() {
      this.selectedOption = null;
    }
  },
  watch: {
    clear: function () {
      this.clearFilter();
    }
  },
  mounted() {
    this.setSelectEmitter();
    this.getConditions();
  }
}
</script>

<style>
.filter-options {
  display: flex;
  flex-direction: column;
  width: 25%;
  background-color: v-bind('COLORS.PRIMARY');
  border-radius: 0.5em;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}

#arrow-icon {
  cursor: pointer;
  margin-right: 1%;
  margin-top: 1%;
}

#prop-icon {
  margin-left: -0.2em;
  width: 10%;
}

.filter-header {
  display: flex;
  justify-content: space-between;
  margin-left: 2%;
  padding: 3%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  cursor: pointer;
}

.filter-header-left {
  display: flex;
  align-items: center;
  width: 90%;
}

.filter-options-list {
  display: flex;
  flex-direction: column;
  margin-left: 3%;
  margin-bottom: 2%;
}

.option {
  width: 96%;
  border-radius: 0.3em;
}
</style>
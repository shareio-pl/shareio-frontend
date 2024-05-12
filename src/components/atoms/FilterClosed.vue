<template>
  <div id="filter">
    <div id="header">
      <span v-if="direction"> {{ name }} (od najnowszych) </span>
      <span v-else> {{ name }} (od najstarszych) </span>
      <font-awesome-icon :icon="direction ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
  </div>
</template>

<script>
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FilterClosed",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronDown: faChevronDown,
      iconChevronUp: faChevronUp,
      direction: true,
    }
  },
  props:
  {
    name:
    {
      type: String,
      required: true,
    },
  },
  components: { FontAwesomeIcon },
  methods: {
    onArrowClick() {
      this.direction = !this.direction;
      this.emitter.emit('filter-closed', { direction: this.direction });
    },
  },
}
</script>

<style scoped>
#filter {
  display: flex;
  flex-direction: column;
  width: 25%;
  background-color: v-bind('COLORS.PRIMARY');
  border-radius: 0.5em;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}

#header {
  display: flex;
  height: 50%;
  justify-content: space-between;
  margin-left: 1%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  padding: 3%;
  cursor: pointer;
}

#arrow-icon {
  cursor: pointer;
  margin-right: 1%;
  margin-top: 1%;
}
</style>
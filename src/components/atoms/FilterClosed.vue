<template>
  <div id="filter">
    <div id="header">
      <h1>
        {{ name }}
      </h1>
      <font-awesome-icon :icon="iconChevronDown" id="arrow-icon" @click="onArrowClick"/>
    </div>
  </div>
</template>

<script>
import {COLORS, FONT_SIZES} from "../../../public/Consts";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faChevronDown} from "@fortawesome/free-solid-svg-icons";
export default {
  name: "FilterClosed",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronDown: faChevronDown,
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
  components: {FontAwesomeIcon},
  methods:
      {
        filter() {
          console.log('User input: ', this.input);
          this.emitter.emit('filter', {input: this.input});
          this.input = '';
        },
      },
}
</script>

<style scoped>
#filter {
  display: flex;
  flex-direction: column;
  width: 25%;
  background-color: v-bind('COLORS.MENU_WHITE');
  border: solid v-bind('COLORS.BORDER_BLACK');
}

#header {
  display: flex;
  height: 50%;
  margin-left: 1%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

#arrow-icon {
  scale: 250%;
  margin-top: 4%;
  margin-left: 10%;
  cursor: pointer;
}

</style>
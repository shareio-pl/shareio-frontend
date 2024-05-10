<template>
  <div id="filter">
    <div id="header">
      <h1>
        {{ name }}
      </h1>
      <font-awesome-icon :icon="showInput ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
    <input id="input" v-show="showInput" type="text" :placeholder="placeholder" @keyup.enter="filter" v-model="input">
  </div>
</template>

<script>
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FilterOpenDefault",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronUp: faChevronUp,
      iconChevronDown: faChevronDown,
      showInput: true,
      input: '',
    }
  },
  props:
  {
    name:
    {
      type: String,
      required: true,
    },
    placeholder:
    {
      type: String,
      required: true,
    },
  },
  components: { FontAwesomeIcon },
  methods:
  {
    onArrowClick() {
      this.showInput = !this.showInput;
    },
    filter() {
      console.log('User input: ', this.input);
      this.emitter.emit('filter', { input: this.input });
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
  justify-content: space-between;
  display: flex;
  height: 50%;
  margin-left: 1%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

#arrow-icon {
  cursor: pointer;
  margin-right: 1%;
  margin-top: 1%;
}

input {
  margin-left: 2%;
  margin-bottom: 5%;
  width: 88%;
  height: 35%;
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  background-color: v-bind('COLORS.PRIMARY');
}

::placeholder {
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}
</style>

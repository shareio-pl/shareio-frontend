<template>
  <div id="filter">
    <div id="header">
      {{ name }}
      <font-awesome-icon :icon="showInput ? iconChevronUp : iconChevronDown" id="arrow-icon" @click="onArrowClick" />
    </div>
    <input id="input" v-show="showInput" type="text" onkeypress="return /[0-9]/i.test(event.key)"
      :placeholder="placeholder" @keyup.enter="filter" v-model="input">
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
    identifier:
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
      this.emitter.emit('filter-open-default', { input: this.input, identifier: this.identifier });
      this.input = '';
    },
    isNumberKey(evt) {
      var charCode = (evt.which) ? evt.which : evt.keyCode
      if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
      return true;
    }
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
  justify-content: space-between;
  display: flex;
  height: 50%;
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

input {
  margin-left: 5%;
  margin-bottom: 5%;
  width: 90%;
  height: 35%;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
}

::placeholder {
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}
</style>

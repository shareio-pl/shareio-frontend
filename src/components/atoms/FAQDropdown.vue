<template>
  <div class="faq-dropdown" @click="onArrowClick">
    <div class="faq-header">
      <div class="header-left">
        {{ title }}
      </div>
      <font-awesome-icon :icon="showMessage ? iconChevronUp : iconChevronDown" id="arrow-icon"/>
    </div>
    <p v-show="showMessage">
      {{ message }}
    </p>
  </div>
</template>

<script>
import {FONT_SIZES, COLORS} from "../../../public/Consts";

import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faChevronUp} from "@fortawesome/free-solid-svg-icons";
import {faChevronDown} from "@fortawesome/free-solid-svg-icons";

export default {
  name: "FAQDropdown",
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronUp: faChevronUp,
      iconChevronDown: faChevronDown,
      showMessage: false,
    }
  },
  props: {
    title: {
      type: String,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
  },
  methods: {
    onArrowClick() {
      this.showMessage = !this.showMessage;
    }
  }
}
</script>

<style scoped>
.faq-dropdown {
  display: flex;
  flex-direction: column;
  background-color: v-bind('COLORS.PRIMARY');
  border-radius: 0.5em;
}

.faq-header {
  justify-content: space-between;
  display: flex;
  margin-left: 1%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.SECONDARY');
  padding: 0.75em;
  cursor: pointer;
}

#arrow-icon {
  cursor: pointer;
  margin: 0;
  margin-top: 0.5%;
  margin-right: 0.5%;
}

p {
  margin-left: 2%;
  margin-right: 2%;
  width: 96%;
  margin-bottom: 1em;
  padding: 0.25em;
  width: auto;
  color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  align-items: flex-start;
  text-align: left;
}

.header-left {
  display: flex;
  align-items: center;
  width: 90%;
}
</style>

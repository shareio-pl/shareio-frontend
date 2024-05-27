<template>
  <div class="dots">
    <div class="dot-1">
      <span>.</span>
      <span>.</span>
      <span>.</span>
    </div>
    <div class="dot-2">
      <span>.</span>
      <span>.</span>
      <span>.</span>
    </div>
    <div class="dot-3">
      <span>.</span>
      <span>.</span>
      <span>.</span>
    </div>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Dots",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  }
}

</script>

<style scoped>
.dots {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: v-bind('FONT_SIZES.TITLE');
  color: v-bind('COLORS.TEXT_SECONDARY');
  width: 90%;
  margin-bottom: 0.5em;
}

.dot-1 {
  width: 10%;
}

.dot-2 {
  width: 30%;
}

.dot-3 {
  width: 60%;
}

.dot-1,
.dot-2,
.dot-3 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.dot-1 span,
.dot-2 span,
.dot-3 span {
  margin: -0.5em 0;
}
</style>
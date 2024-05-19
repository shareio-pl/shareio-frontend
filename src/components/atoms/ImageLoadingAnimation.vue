<template>
  <div class="skeleton"></div>
</template>

<script>
import { COLORS } from "../../../public/Consts";

export default {
  name: "ImageLoadingAnimation",
  data() {
    return {
      COLORS: COLORS,
    }
  },
}
</script>

<style>
.skeleton {
  background: v-bind('COLORS.ANIMATION_START');
  width: 100%;
  height: 100%;
  border-radius: 10px;
  animation: pulse 1s infinite ease-in-out;
}

@keyframes pulse {
  0% {
    background: v-bind('COLORS.ANIMATION_START');
  }

  50% {
    background: v-bind('COLORS.ANIMATION_MIDWAY');
  }

  100% {
    background: v-bind('COLORS.ANIMATION_END');
  }
}
</style>
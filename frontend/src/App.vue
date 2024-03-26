<template>
  <Header :style="{ fontFamily: FONTS_PRIMARY }" />
</template>

<script>
import Header from "@/components/Header.vue";
import { FONTS } from "../public/Consts";

export default {
  name: "App",
  components: {
    Header,
  },
  data() {
    return {
      FONTS_PRIMARY: FONTS.PRIMARY, // Using FONTS.PRIMARY unfortunately doesn't work, as Vue doesn't seem to pick that up as a value if I import from consts.
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>

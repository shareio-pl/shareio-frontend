<template>
  <Header :style="{ fontFamily: FONTS.PRIMARY }" />
</template>

<script>
import Header from "@/components/Header.vue";
import { FONTS } from "../public/Consts";

export default {
  name: "App",
  components: {
    Header,
  },
  computed: {
    FONTS() {
      return FONTS;
    },
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

<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <Main />
  </div>
</template>

<script>
import {FONTS} from "../public/Consts";
import Main from "@/components/pages/Main.vue";

export default {
  name: "App",
  components: {
    Main,
  },
  data() {
    return {
      
    };
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

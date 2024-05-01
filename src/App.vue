<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <AxiosError/>
    <Main />
  </div>
</template>

<script>
import {FONTS} from "../public/Consts";
import Main from "@/components/pages/Main.vue";
import AxiosError from "@/components/pages/AxiosError.vue";

export default {
  name: "App",
  components: {
    AxiosError,
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

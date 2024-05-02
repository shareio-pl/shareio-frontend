<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <Map/>
  </div>
</template>

<script>
import {FONTS} from "../public/Consts";
import Map from "@/components/pages/Map.vue";

export default {
  name: "App",
  components: {
    Map,
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

<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <AxiosError/>
    <DefaultError/>
    <Menu/>
    <MenuGuest/>
    <RouterView/>
  </div>
</template>

<script>
import {FONTS} from "../public/Consts";
import AxiosError from "@/components/pages/AxiosError.vue";
import DefaultError from "@/components/pages/DefaultError.vue";
import Menu from "@/components/pages/Menu.vue";
import MenuGuest from "@/components/pages/MenuGuest.vue";

export default {
  name: "App",
  components: {
    DefaultError,
    AxiosError,
    Menu,
    MenuGuest,
  },
  data() {
    return {};
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

<template>
  <div :style="{ fontFamily: FONTS.PRIMARY }">
    <AxiosError />
    <DefaultError />
    <DefaultSuccess />
    <Menu />
    <RouterView />
  </div>
</template>

<script>
import { FONTS } from "../public/Consts";
import { jwtDecode } from "jwt-decode";
import AxiosError from "@/components/pages/AxiosError.vue";
import DefaultError from "@/components/pages/DefaultError.vue";
import DefaultSuccess from "@/components/pages/DefaultSuccess.vue";
import Menu from "@/components/pages/Menu.vue";

export default {
  name: "App",
  components: {
    DefaultError,
    AxiosError,
    DefaultSuccess,
    Menu
  },
  computed: {
    FONTS() {
      return FONTS;
    },
  },
  mounted() {
    let token = localStorage.get('token');
    if (token && jwtDecode(token).exp < Date.now() / 1000) {
      localStorage.removeItem('token');
    }
  }
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

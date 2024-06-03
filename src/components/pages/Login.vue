<template>
  <div id="login-page">
    <Header />
    <h1>Zaloguj się</h1>
    <FormLogin />
    <span @click="onRegister">Nie posiadasz konta? Zarejestruj się!</span>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import FormLogin from "@/components/organisms/FormLogin.vue";
import {COLORS, FONT_SIZES} from "../../../public/Consts";

export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  components: {
    Header,
    FormLogin,
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  },
  methods: {
    onRegister() {
      this.$router.push('/register');
    },
  }
}

</script>

<style scoped>
#login-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
}

h1 {
  margin-top: 1%;
  margin-bottom: 1%;
}

span {
  font-size: calc(13px + 0.85vw);
  color: v-bind('COLORS.TEXT_PRIMARY');
  margin-top: 5%;
  cursor: pointer;
}

span:hover {
  text-decoration: underline;
}

</style>
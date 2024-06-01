<template>
  <div id="login-page">
    <Header />
    <h1>Zaloguj się</h1>
    <FormLogin />
    <span @click="onRegister">Nie posiadasz konta? Zarejestruj się</span>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import FormLogin from "@/components/organisms/FormLogin.vue";

export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  components: {
    Header,
    FormLogin,
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
}

span {
  margin-top: 5%;
  cursor: pointer;
}

</style>
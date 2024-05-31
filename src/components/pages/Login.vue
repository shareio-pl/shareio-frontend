<template>
  <div id="login-page">
    <Header />
    <h1>Zaloguj się</h1>
    <FormLogin />
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
</style>
<template>
  <div id="register-page">
    <Header />
    <img src="../../assets/favicon_big.png" alt="" class="logo">
    <h1>Zarejestruj się</h1>
    <FormRegister />
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import FormRegister from "@/components/organisms/FormRegister.vue";

export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name: "Register",
  components: {
    Header,
    FormRegister,
  }
}
</script>

<style scoped>
#register-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
}

h1 {
  margin-top: 0;
  margin-bottom: 1%;
}

img {
  width:5%;
  height: 5%;
}

.logo {
  margin-top: 0%;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 0%;
}
</style>


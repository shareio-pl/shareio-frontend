<template>
  <div id="changepassword-page">
    <Header />
    <h1>Zmień hasło</h1>
    <FormChangePass />
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import FormChangePass from "@/components/organisms/FormChangePass.vue";

export default {
  //eslint-disable-next-line vue/multi-word-component-names
  name: "ChangePass",
  components: {
    Header,
    FormChangePass,
  }
}
</script>

<style scoped>
#changepassword-page {
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
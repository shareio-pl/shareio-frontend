<template>
  <div id="register-page">
    <Header />
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
}
</style>


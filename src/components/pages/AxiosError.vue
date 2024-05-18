<template>
  <div v-if="isShown" class="background">
    <img :src="url" v-on:click="closeError" alt="Imagine a cat here">
  </div>
</template>

<script>
export default {
  name: "AxiosError",
  data() {
    return {
      url: 'https://http.cat/',
      url_base: 'https://http.cat/',
      isShown: false,
    }
  },
  methods:
  {
    closeError() {
      this.isShown = false;
      this.url = 'https://http.cat/';
    }
  },
  mounted() {
    this.emitter.on('axiosError', (data) => {
      console.log('Error code: ', data.error);
      this.url = this.url_base + data.error;
      console.log('URL: ', this.url);
      this.isShown = true;
    });
  },
}
</script>

<style scoped>
img {
  position: fixed;
  width: 50%;
  height: 75%;
  top: 10%;
  left: 25%;
  object-fit: cover;
}

.background {
  width: 100%;
  height: 100%;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  top: 0;
  left: 0;
  z-index: 5;
}
</style>

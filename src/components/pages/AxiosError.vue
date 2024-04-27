<template>
  <div v-if="isShown" class="background">
    <img :src="cat" v-on:click="closeError" alt="Imagine a cat here">
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AxiosError",
  data() {
    return {
      cat: null,
      isShown: false,
    }
  },
  methods:
      {
        closeError() {
          this.isShown = false;
        }
      },
  mounted() {
    this.emitter.on('axiosError', (data) => {
      console.log('Error code: ', data.error);
      axios.get(`https://http.cat/${data.error}`).then((response) => {
        this.error = response;
      }).catch(() => {
        console.log('Cat did not want to come');
      });

      this.isShown = true;
      setTimeout(this.closeError, 5000);
    });
  },
}
</script>

<style scoped>
img {
  position: fixed;
  width: 50%;
  height: 50%;
  top: 25%;
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
}
</style>

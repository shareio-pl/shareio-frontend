<template>
  <div v-if="isShown" class="background">
    <div class="message" v-on:click="closeError">
      <p>
        {{ this.message }}
      </p>
    </div>
  </div>
</template>

<script>
import {COLORS} from "../../../public/Consts";

export default {
  name: "DefaultSuccess",
  data() {
    return {
      message: '',
      isShown: false,
      COLORS: COLORS,
    }
  },
  methods:
      {
        closeError() {
          this.isShown = false;
        }
      },
  mounted() {
    this.emitter.on('success', (data) => {
      this.message = data.message;
      this.isShown = true;

      setTimeout(this.closeError, 3000);
    })
  },
}
</script>

<style scoped>
.background {
  width: 100%;
  height: 100%;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  top: 0;
  left: 0;
  z-index: 5;
}

.message {
  display: flex;
  position: fixed;
  width: 50%;
  height: 50%;
  top: 20%;
  left: 25%;
  justify-content: center;
  align-items: center;
  font-size: 500%;
  background-color: rgba(0, 125, 0, 0.8);
  text-align: center;
  border-radius: 15px;
  color: v-bind('COLORS.TEXT_PRIMARY');
}
</style>

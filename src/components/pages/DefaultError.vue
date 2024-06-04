<template>
  <div v-if="isShown" v-on:click="closeError" class="background">
    <div class="error" v-on:click="closeError">
      <p>
        {{ this.error }}
      </p>
    </div>
  </div>
</template>

<script>
import { COLORS } from "../../../public/Consts";

export default {
  name: "DefaultError",
  data() {
    return {
      error: 'Test Error',
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
    this.emitter.on('error', (data) => {
      this.error = data.error;
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

.error {
  display: flex;
  position: fixed;
  width: 50%;
  height: 50%;
  top: 20%;
  left: 25%;
  justify-content: center;
  align-items: center;
  font-size: 500%;
  background-color: rgba(255, 0, 0, 0.8);
  text-align: center;
  border-radius: 15px;
  color: v-bind('COLORS.TEXT_PRIMARY');
}
</style>

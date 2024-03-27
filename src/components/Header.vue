<template>
  <div id="header" :style="{ background: COLORS.PRIMARY }">
    <img src="../../src/assets/logo.png" alt="logo" />
    <button
      :style="{ background: COLORS.SECONDARY, color: COLORS.TEXT_SECONDARY }"
    >
      Ogłoszenia
    </button>
    <button
      :style="{ background: COLORS.SECONDARY, color: COLORS.TEXT_SECONDARY }"
    >
      Mapa
    </button>
    <button
      :style="{ background: COLORS.SECONDARY, color: COLORS.TEXT_SECONDARY }"
    >
      Wystaw Przedmiot
    </button>
    <button
      :style="{ background: COLORS.SECONDARY, color: COLORS.TEXT_SECONDARY }"
    >
      Jak to działa?
    </button>
    <div id="user" :style="{ color: COLORS.TEXT_PRIMARY }">
      <img src="../../src/assets/logo.png" alt="logo" />
      <div>
        <p>IMIĘ</p>
        <p>NAZWISKO</p>
      </div>
      <img src="../../src/assets/logo.png" alt="logo" />
    </div>
  </div>
</template>

<script>
import { COLORS } from "../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data() {
    return {
      COLORS: COLORS,
    };
  },
};
</script>

<style scoped>
#header {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100px;
}

img {
  margin-left: 1%;
}

#user {
  display: flex;
  align-items: center;
  margin-left: 10%;
  height: 10px;
}

button {
  width: 9%;
  height: 45px;
  margin-left: 2%;
  border-radius: 20px;
  font-size: 93%;
}
</style>

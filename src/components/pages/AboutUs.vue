<template>
  <div id="AboutUs">
    <Header />
    <img :src="image" alt="nasz zespół">
    <h1>{{ title }}</h1>
    <p>{{ text }}</p>
  </div>
</template>

<script>
import { ABOUT_US_PAGE_IMAGE, ABOUT_US_PAGE_TEXT, COLORS, FONT_SIZES } from "../../../public/Consts";
import Header from "@/components/organisms/Header.vue";

export default {
  name: "AboutUs",
  components: { Header },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      title: 'Szopy są naszą pasją!',
      text: ABOUT_US_PAGE_TEXT,
      image: ABOUT_US_PAGE_IMAGE,
    }
  },
}
</script>

<style scoped>
#AboutUs {
  display: flex;
  flex-direction: column;
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
  height: 100%;
  width: 100%;
  min-height: 100vh;
}

img {
  width: 86%;
  margin-top: 1%;
  margin-left: 7%;
  margin-right: 7%;
  height: 480px;
}

h1 {
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.TITLE');
  margin-top: 1%;
}

p {
  color: v-bind('COLORS.TEXT_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  width: 86%;
  margin-top: 0.7%;
  margin-left: 7%;
  margin-right: 7%;
}
</style>

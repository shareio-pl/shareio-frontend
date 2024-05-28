<template>
  <div class="user-data">
    <div class="user-image">
      <img :src="userImage" alt="">
    </div>
    <div class="user-info">
      <div class="user-name">{{ userFirstName }}</div>
      <div class="user-surname">{{ userSurname }}</div>
    </div>
  </div>
</template>

<script>
import { DEFAULT_USER_PROFILE_IMAGE } from "../../../public/Consts";
import { FONT_SIZES, COLORS } from "../../../public/Consts";

export default {
  name: 'UserData',
  props: {
    userFirstName: {
      type: String,
      required: true
    },
    userSurname: {
      type: String,
      required: true
    },
    userImage: {
      type: String,
      default: DEFAULT_USER_PROFILE_IMAGE
    }
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
    }
  }
}
</script>

<style scoped>
.user-data {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.user-image {
  width: calc(20px + 2vw);
  height: calc(20px + 2vw);
  border-radius: 50%;
  overflow: hidden;
  align-self: flex-start;
}

.user-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  margin-left: 0.8em;
  /* Percentage here wouldn't be the best choice, I think.
  Because then the margin varies too much on the width of component this atom would be in;
  in my testing the difference was noticeable. */
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}

.user-name {
  margin-bottom: -5%;
}
</style>

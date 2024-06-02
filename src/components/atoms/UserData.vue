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
  font-size: calc(10px + 1.0vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
}

@media screen and (min-width: 1200px) {
  div {
    font-size: v-bind('FONT_SIZES.PRIMARY');
  }
}

.user-image {
  width: calc(20px + 2vw);
  height: calc(20px + 2vw);
  border-radius: 50%;
  overflow: hidden;
}

.user-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  margin-left: 1.3em;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}

.user-name {
  margin-bottom: -5%;
}
</style>

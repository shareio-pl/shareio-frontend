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
import {DEFAULT_USER_PROFILE_IMAGE} from "../../../public/Consts";
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
  }
}
</script>

<style scoped>
.user-image {
  width: 50px;
  height: 50px;
  border-radius: 50%; 
  overflow: hidden;
}

.user-image img {
  width: 100%;
  height: 100%;
  object-fit: cover; 
}

.user-data {
  display: flex;
  align-items: center;
}

.user-info {
  margin-left: 0.8em; 
  /* Percentage here wouldn't be the best choice, I think. 
  Because then the margin varies too much on the width of component this atom would be in; 
  in my testing the difference was noticeable. */
}

.user-name {
  margin-bottom: -25%;
}
</style>
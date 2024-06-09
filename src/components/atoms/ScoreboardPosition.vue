<template>
  <div class="scoreboard-position" style="border-bottom: solid black 2px;">
    <div class="position" :class="{ 'current-user': currentUser }">{{ position }}</div>
    <div class="score" :class="{ 'current-user': currentUser }">{{ score }}</div>
    <div class="name" :class="{ 'current-user': currentUser }" @click="sendToProfile">{{ nameAndSurname }}</div>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from "../../../public/Consts";

export default {
  name: "ScoreboardPosition",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  },
  props: {
    position: {
      type: Number,
      required: true
    },
    nameAndSurname: {
      type: String,
      required: true
    },
    userId: {
      type: String,
      required: true
    },
    score: {
      type: Number,
      required: true
    },
    currentUser: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    sendToProfile() {
      console.log(this.userId);
      this.$router.push(`/user/${this.userId}`);
    }
  }
}

</script>

<style scoped>
.scoreboard-position {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.2em;
  background-color: v-bind('COLORS.PRIMARY');
  font-size: calc(9px + 0.7vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
  width: 90%;
  position: relative;
}

.scoreboard-position>div {
  text-align: center;
  position: relative;
  border-bottom: black 2px;
}

.scoreboard-position>div:not(:last-child)::after {
  content: "";
  position: absolute;
  right: 0;
  top: -0.2em;
  bottom: -0.2em;
  width: 2px;
  background-color: #000;

}

.position {
  width: 10%;
}

.name {
  width: 60%;
}

.score {
  width: 30%;
}

.name:hover {
  cursor: pointer;
  text-decoration: underline;
}

.current-user {
  font-weight: bolder;
}
</style>

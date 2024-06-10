<template>
  <div id="Scoreboard">
    <Header />
    <div id="scoreboard-table">
      <div class="column-titles">
        <div class="column-title1">Pozycja</div>
        <div class="column-title2">Wynik</div>
        <div class="column-title3">Użytkownik</div>
      </div>
      <ScoreboardPosition v-for="(user) in topUsers.slice(0, 10)" :key="user.userId" :userId="user.userId"
        :score="user.score" :nameAndSurname="user.nameAndSurname" :position="user.position"
        :current-user="user.userId === this.currentUser.userId" />
      <Dots />
      <div v-if="!isCurrentUserInTopUsers" class="current-user-row">
        <ScoreboardPosition :userId="currentUser.userId" :score="currentUser.score"
          :nameAndSurname="currentUser.nameAndSurname" :position="currentUser.position" />
      </div>
      <Dots v-if="!isCurrentUserInTopUsers && isCurrentUserLast" />
    </div>
  </div>
</template>

<script>
import { COLORS, FONT_SIZES, GATEWAY_ADDRESS } from "../../../public/Consts";
import Header from "@/components/organisms/Header.vue";
import ScoreboardPosition from "@/components/atoms/ScoreboardPosition.vue";
import axios from "axios";
import Dots from "@/components/atoms/Dots.vue";
import { jwtDecode } from "jwt-decode";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Scoreboard",
  components: { Header, ScoreboardPosition, Dots },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      topUsers: [],
      currentUser: { userId: '' },
      lastPosition: '',
    }
  },
  mounted() {
    let token = localStorage.getItem('token');
    if (token) {
      this.currentUser.userId = jwtDecode(token).id;
    }
  },
  async created() {
    await this.fetchTopUsers();
    await this.fetchCurrentUser();
    await this.fetchNumberOfUsers();
  },
  computed: {
    isCurrentUserInTopUsers() {
      return this.topUsers.slice(0, 10).some(user => user.userId === this.currentUser.userId);
    },
    isCurrentUserLast() {
      return this.lastPosition - this.currentUser.position;
    }
  },
  methods: {
    async fetchTopUsers() {
      axios.get(GATEWAY_ADDRESS + `/offer/getTopScoreUserList`).then(response => {
        console.log(response.data); // to delete
        this.topUsers = response.data;
      }).catch(error => {
        console.error('Error fetching top users: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
    },
    async fetchCurrentUser() {
      axios.get(GATEWAY_ADDRESS + `/offer/getScore/${this.currentUser.userId}`).then(response => {
        this.currentUser = response.data;
      }).catch(error => {
        console.error('Error fetching current user: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
    },
    async fetchNumberOfUsers() {
      axios.get(GATEWAY_ADDRESS + `/user/getAll`).then(response => {
        this.lastPosition = response.data.length;
      }).catch(error => {
        console.error('Error fetching number of all user: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
    },
  }
}
</script>

<style scoped>
#Scoreboard {
  display: flex;
  flex-direction: column;
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  height: 100%;
  width: 100%;
  min-height: 100vh;
}

#scoreboard-table {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.column-titles {
  display: flex;
  width: 100%;
  justify-content: space-around;
  font-weight: bold;
  padding: 10px 0;
  font-size: calc(15px + 1.5vw);
}

.column-titles {
  flex: 1;
  text-align: center;
}

.current-user-row {
  display: flex;
  width: 100%;
  justify-content: space-around;
}

.column-title1 {
  width: 19%;
}

.column-title2 {
  width: 15%;
}

.column-title3 {
  width: 64%;
}

.current-user-row ::v-deep .name {
  font-weight: bold;
}
</style>
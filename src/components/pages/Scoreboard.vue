<template>
  <div id="Scoreboard">
    <Header />
    <div id="scoreboard-table">
      <div class="column-titles">
        <div class="column-title1">Pozycja</div>
        <div class="column-title2">Wynik</div>
        <div class="column-title3">Użytkownik</div>
      </div>
      <ScoreboardPosition
          v-for="(user, index) in topUsersMock"
          :key="user.userId"
          :score="user.score"
          :user-id="user.userId"
          :surname="user.surname"
          :name="user.name"
          :position="index + 1"
      />
      <Dots class="middleDots"/>
      <div v-if="currentUser && !isCurrentUserInTopUsers" class="current-user-row">
        <ScoreboardPosition
            :score="currentUser.score"
            :user-id="currentUser.userId"
            :surname="currentUser.surname"
            :name="currentUser.name"
            :position="positionOutsideTopTen"
        />
      </div>
      <Dots class="endingDots"/>
    </div>
  </div>
</template>

<script>
import { COLORS, FONT_SIZES } from "../../../public/Consts";
import Header from "@/components/organisms/Header.vue";
import ScoreboardPosition from "@/components/atoms/ScoreboardPosition.vue";
import axios from "axios";
import Dots from "@/components/atoms/Dots.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Scoreboard",
  components: { Header, ScoreboardPosition, Dots },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      topUsersMock: this.generateMockData(),
      topUsers: [],
      currentUser: { userId: '22', score: '222', surname: 'Taylor', name: 'Sailor' }, // : null
      positionOutsideTopTen: 0
    }
  },
  async created() {
    await this.fetchTopUsers();
    await this.fetchCurrentUser();
  },
  computed: {
    isCurrentUserInTopUsers() {
      return this.topUsers.some(user => user.userId === this.currentUser.userId);
    }
  },
  methods: {
    async fetchTopUsers() {
      try {
        const response = await axios.get('/offer/getTopScoreUserList');
        this.topUsers = response.data.slice(0, 10);
      } catch (error) {
        console.error('Error fetching top users:', error);
      }
    },
    async fetchCurrentUser() {
      try {
        const response = await axios.get('/user/getCurrentUserScore'); // Endpoint to get current user's score
        this.currentUser = response.data;

        if (!this.isCurrentUserInTopUsers) {
          const allUsersResponse = await axios.get('/offer/getAllScores');
          const allUsers = allUsersResponse.data;
          const currentUserRank = allUsers.findIndex(user => user.userId === this.currentUser.userId) + 1;
          this.positionOutsideTopTen = currentUserRank;
        }
      } catch (error) {
        console.error('Error fetching current user:', error);
      }
    },
    generateMockData() {
      return [
        { userId: '1', score: '100', surname: 'Smith', name: 'John' },
        { userId: '2', score: '95', surname: 'Doe', name: 'Jane' },
        { userId: '3', score: '90', surname: 'Brown', name: 'Bob' },
        { userId: '4', score: '85', surname: 'Johnson', name: 'Alice' },
        { userId: '5', score: '80', surname: 'Williams', name: 'Chris' },
        { userId: '6', score: '75', surname: 'Jones', name: 'Pat' },
        { userId: '7', score: '70', surname: 'Garcia', name: 'Kim' },
        { userId: '8', score: '65', surname: 'Martinez', name: 'Alex' },
        { userId: '9', score: '60', surname: 'Hernandez', name: 'Lee' },
        { userId: '10', score: '55', surname: 'Lopez', name: 'Sam' },
      ];
    }
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
</style>
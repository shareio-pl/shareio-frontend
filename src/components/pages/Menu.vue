<template>
  <div v-if="showMenu" @click='changeStateOfMenu' :class="['menu-page', { sticky: isSticky }]" ref="menuPage">
    <div class="menu-items">
      <div class="pages-menu">
        <hr class="firstLine" />
        <div v-if="isLoggedIn" id="user-data">
          <UserData ref="userDataComp" class="user" :user-surname="surname" :user-first-name="name" :user-image="image"
            style="display: flex; flex-direction: column;" />
        </div>
        <hr v-if="isLoggedIn" />
        <span @click="onOffersClick">Ogłoszenia</span>
        <hr />
        <span @click="onMapClick">Mapa</span>
        <hr />
        <span @click="onAddOfferClick">Nowa oferta</span>
        <hr />
        <span @click="onAboutUsClick">O nas</span>
        <hr />
      </div>
      <span v-if="isLoggedIn" @click="onMyAccountClick">Moje konto</span>
      <hr v-if="isLoggedIn" class="conditionalLines" />
      <span v-if="isLoggedIn" @click="onHelpClick">Pomoc</span>
      <hr v-if="isLoggedIn" class="conditionalLines" />
      <span v-if="isLoggedIn" @click="onPasswordChangeClick">Zmień hasło</span>
      <span v-else @click="onLogin">Zaloguj się</span>
      <hr />
      <span v-if="isLoggedIn" @click="onLogout">Wyloguj się</span>
      <span v-else @click="onRegister">Zarejestruj się</span>
    </div>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS, GATEWAY_ADDRESS } from "../../../public/Consts";
import UserData from "@/components/atoms/UserData.vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Menu",
  components: { UserData },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      showMenu: false,
      isSticky: false,
      surname: '',
      name: '',
      photoId: '',
      image: null,
    };
  },
  methods: {
    changeStateOfMenu() {
      this.showMenu = !this.showMenu;
      if (this.showMenu) {
        window.addEventListener('scroll', this.handleScroll);
        this.$nextTick(() => {
          this.handleScroll();
        });
        this.emitter.emit('menu-opened');
      } else {
        window.removeEventListener('scroll', this.handleScroll);
        this.emitter.emit('menu-closed');
      }
    },
    handleScroll() {
      if (window.scrollY > 100) {
        this.isSticky = true;
        this.$refs.menuPage.style.top = '0px';
      } else {
        this.isSticky = false;
        this.$refs.menuPage.style.top = (-window.scrollY + 100) + 'px';
      }
    },
    onMyAccountClick() {
      this.$router.push("/myAccount");
    },
    onHelpClick() {
      this.$router.push("/help");
    },
    onPasswordChangeClick() {
      this.$router.push("/changePassword");
    },
    onLogout() {
      localStorage.removeItem('token');

      if (this.$route.path === '/') {
        window.location.reload();
      } else {
        this.$router.push('/').then(() => {
          window.location.reload();
        })
          .catch(error => {
            console.error(error);
          })
      }
    },
    onLogin() {
      this.$router.push('/login');
    },
    onRegister() {
      this.$router.push('/register');
    },
    onOffersClick() {
      this.$router.push('/offers');
    },
    onMapClick() {
      this.$router.push('/map');
    },
    onAddOfferClick() {
      this.$router.push('/newOffer');
    },
    onAboutUsClick() {
      this.$router.push('/about');
    },
    async getUserData() {
      let token = localStorage.getItem('token');
      axios.get(GATEWAY_ADDRESS + `/user/get/${jwtDecode(token).id}`).then((response) => {
        console.log('Logged User: ', response.data);
        console.log('Logged image id: ', response.data.photoId.id);
        this.name = response.data.name;
        this.surname = response.data.surname;
        this.photoId = response.data.photoId.id;
        console.log('PhotoId: ', this.photoId);
      })
        .then(() => {
          this.getImageData(this.photoId);
        })
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    async getImageData(photoId) {
      console.log('PhotoId: ', photoId);
      await axios.get(GATEWAY_ADDRESS + `/image/get/${photoId}`, { responseType: 'arraybuffer' }).then((response) => {
        let image_buffer = this.arrayBufferToBase64(response.data);
        this.image = `data:image/jpeg;base64,${image_buffer}`;
      }).catch(error => {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      });
    },
    arrayBufferToBase64(buffer) {
      return btoa(
        new Uint8Array(buffer).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    },
  },
  async mounted() {
    if (localStorage.getItem('token')) {
      await this.getUserData();
    }
    this.emitter.on('change-menu', () => {
      this.changeStateOfMenu();
    });
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('token');
    }
  },
};
</script>

<style scoped>
.menu-page {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(249, 235, 224, 0.81);
  top: 100px;
  left: 0;
  z-index: 5;
}

.menu-items {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  width: 8%;
  margin-right: 6%;
  margin-left: 86%;
  white-space: nowrap;
}

.menu-items>span {
  display: flex;
  flex-direction: column;
  align-self: flex-end;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: calc(13px + 0.5vw);
  cursor: pointer;
  font-weight: bold;
}

@media screen and (min-width: 1200px) {
  .menu-items>span {
    font-size: v-bind('FONT_SIZES.PRIMARY');
  }
}

.menu-items>span:hover {
  text-decoration: underline;
}

hr {
  margin-left: auto;
  width: 100%;
  color: v-bind('COLORS.TEXT_SECONDARY');
  border-width: 2px;
}

.pages-menu>span {
  display: flex;
  flex-direction: column;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: calc(13px + 0.5vw);
  cursor: pointer;
  font-weight: bold;
}

.pages-menu>span:hover {
  text-decoration: underline;
}

.user {
  font-size: calc(13px + 0.5vw);
  font-weight: bold;
}

@media screen and (min-width: 850px) {
  .pages-menu {
    display: none;
  }

  .conditionalLines {
    display: none;
  }
}

@media screen and (max-width: 850px) {
  .menu-page {
    width: 30%;
    margin-left: 70%;
    background-color: rgba(249, 235, 224);
  }

  .menu-items {
    width: 100%;
    margin-left: 0;
  }

  .menu-items>span {
    align-self: auto;
  }

  .firstLine {
    margin-top: 0;
  }
}
</style>


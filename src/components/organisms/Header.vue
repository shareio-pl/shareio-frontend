<template>
  <div>
    <div id="header" v-if="!showDrawer">
      <div id="header_wrapper">
        <img src="../../assets/logo.png" alt="" @click="onLogoClick" class="logo">
        <div id="buttons">
          <ButtonPrimary class="button" button-text="Ogłoszenia" @click="onOffersClick"/>
          <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
          <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
          <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
        </div>
        <div id="user-data" @click="changeMenuState" v-if="!isSmallScreen">
          <UserData :user-surname="surname" :user-first-name="name" :user-image="image"/>
          <font-awesome-icon :icon="menuIsShown ? iconChevronUp : iconChevronDown" id="arrow-icon"/>
        </div>
      </div>
    </div>
    <div id="header-drawer">
      <img src="../../assets/logo.png" alt="Logo" @click="onLogoClick" class="logo">
      <font-awesome-icon :icon="iconHamburger" id="hamburger-icon" class="showMenu" @click="changeMenuState"/>
    </div>
  </div>
</template>

<script>
import {COLORS, FONT_SIZES, GATEWAY_ADDRESS} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faBars, faChevronDown, faChevronUp} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import {jwtDecode} from "jwt-decode";


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {FontAwesomeIcon, UserData, ButtonPrimary},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconHamburger: faBars,
      iconChevronDown: faChevronDown,
      iconChevronUp: faChevronUp,
      menuIsShown: false,

      surname: '',
      name: '',
      photoId: '',
      image: null,
      isSmallScreen: false,
    }
  },
  methods:
        {
        onOffersClick() {
          this.$router.push('/offers');
        },
        onMapClick() {
          this.$router.push('/map');
        },
        onNewOfferClick() {
          this.$router.push('/newOffer');
        },
        onAboutUsClick() {
          this.$router.push('/about');
        },
        onLogoClick() {
          this.$router.push("/");
        },
        changeMenuState() {
          this.emitter.emit('change-menu');
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
                this.emitter.emit('axiosError', {error: error.response.status});
              });
        },
        async getImageData(photoId) {
          console.log('PhotoId: ', photoId);
          await axios.get(GATEWAY_ADDRESS + `/image/get/${photoId}`, {responseType: 'arraybuffer'}).then((response) => {
            let image_buffer = this.arrayBufferToBase64(response.data);
            this.image = `data:image/jpeg;base64,${image_buffer}`;
          }).catch(error => {
            console.error('ERROR: ', error);
            this.emitter.emit('axiosError', {error: error.response.status});
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

    this.emitter.on('menu-closed', () => {
      this.menuIsShown = false;
    });
    this.emitter.on('menu-opened', () => {
      this.menuIsShown = true;
    });
  },
};
</script>

<style scoped>
@media screen and (min-width: 850px) {
  #header {
    display: flex;
    width: 100%;
    height: 100px;
    background-color: v-bind('COLORS.PRIMARY');
  }

  #arrow-icon {
    font-size: v-bind('FONT_SIZES.IMPORTANT');
    color: v-bind('COLORS.TEXT_SECONDARY');
    padding-left: 1em;
    box-sizing: border-box;
  }

  #header_wrapper {
    display: flex;
    width: 96%;
    height: 100%;
    align-items: center;
    justify-content: space-between;
    margin: 0 auto;
  }

  img {
    cursor: pointer;
    aspect-ratio: 3.166;
    height: 100px;
  }

  .logo {
    margin-left: 1%;
  }

  #buttons {
    flex-grow: 1;
    display: flex;
    align-items: center;
    margin-left: 3%;
  }

  .button {
    min-width: 50px;
  }

  #user-data {
    display: flex;
    align-items: center;
    cursor: pointer;
    box-sizing: border-box;
    margin-right: 1.1%;
    margin-bottom: 0;
  }

  #header-drawer img, .showMenu {
    display: none;
  }
}

#hamburger-icon {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
  cursor: pointer;
  margin-right: 11%;
  z-index: 4;
}

@media screen and (max-width: 850px) {
  #header {
    display: none;
  }

  #header-drawer {
    display: flex;
    width: 100%;
    height: 100px;
    background-color: v-bind('COLORS.PRIMARY');
    justify-content: space-between;
    align-items: center;
    padding-left: 1%;
    position: relative;
  }

  #header-drawer img {
    cursor: pointer;
    aspect-ratio: 3.166;
    height: calc(3px + 11vw);
  }

  #header-drawer .showMenu {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    margin-right: 1%;
  }

  #hamburger-icon {
    font-size: v-bind('FONT_SIZES.IMPORTANT');
    color: v-bind('COLORS.TEXT_SECONDARY');
  }
}
</style>

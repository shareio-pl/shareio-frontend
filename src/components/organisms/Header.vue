<template>
  <div id="header">
    <div id="header_wrapper">
      <img src="../../assets/logo.png" @click="onLogoClick">
      <div id="buttons">
        <ButtonPrimary class="button" button-text="Ogłoszenia" @click="onOffersClick"/>
        <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
        <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
        <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
      </div>
      <div id="user-data" @click="changeMenuState">
        <UserData :user-surname="this.surname" :user-first-name="this.name" :user-image="image"/>
        <font-awesome-icon :icon="menuIsShown ? iconChevronUp : iconChevronDown" id="arrow-icon"/>
      </div>
    </div>
  </div>
</template>

<script>
import {COLORS, FONT_SIZES, GATEWAY_ADDRESS} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faChevronDown, faChevronUp} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {FontAwesomeIcon, UserData, ButtonPrimary},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronDown: faChevronDown,
      iconChevronUp: faChevronUp,
      menuIsShown: false,
      surname: '',
      name: '',
      image: null,
    };
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
        getUserData() {
          axios.get(GATEWAY_ADDRESS + `/user/get/${localStorage.getItem('userId')}`).then((response) => {
            console.log('Logged User: ', response.data);
            this.name = response.data.name;
            this.surname = response.data.surname;

            return response.data.photoId;
          }).then((imageId) => {
            // TODO: fix CORS
            axios.get(GATEWAY_ADDRESS + `/image/get/${imageId}`, {responseType: 'arraybuffer'}).then((response) => {
              this.image = this.arrayBufferToBase64(response.data);
            }).catch(error => {
              console.error('ERROR: ', error);

              this.emitter.emit('axiosError', {error: error.response.status});
            })
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
  mounted() {
    if (localStorage.getItem('token')) {
      this.getUserData();
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
#header {
  display: flex;
  width: 100%;
  height: 100px;
  background-color: v-bind('COLORS.PRIMARY');
}

#header_wrapper {
  display: flex;
  width: 96%;
  margin-right: 1%;
  height: 100%;
  align-items: center;
  justify-content: space-between;
  margin: 0 auto;
}

img {
  cursor: pointer;
  aspect-ratio: 3.166;
  height: 100px;
  /* 475 px / 150px */
}

#buttons {
  flex-grow: 1;
  display: flex;
  align-items: center;
  margin-left: 3%;
}

.button {
  min-width: 150px;
}

#user-data {
  display: flex;
  align-items: center;
  cursor: pointer;
  box-sizing: border-box;
  margin-right: 1.1%;
}

#arrow-icon {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
  padding-left: 1em;
  box-sizing: border-box;
}
</style>

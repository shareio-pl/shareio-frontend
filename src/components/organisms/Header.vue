<template>
  <div>
    <div id="header" v-if="!showDrawer">
      <div id="header_wrapper">
        <img src="../../assets/logo.png" alt="" @click="onLogoClick" class="logo">
        <div id="buttons">
          <ButtonPrimary class="button" button-text="Oferty" @click="onOffersClick"/>
          <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
          <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
          <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
        </div>
        <div id="user-data" @click="changeMenuState" v-if="!isSmallScreen">
          <UserData :user-surname="surname" :user-first-name="name"/>
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
import {COLORS} from "../../../public/Consts";
import {FONT_SIZES} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faBars} from "@fortawesome/free-solid-svg-icons";
import {faChevronUp} from "@fortawesome/free-solid-svg-icons";
import {faChevronDown} from "@fortawesome/free-solid-svg-icons";

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
      surname: 'Nazwisko',
      name: 'Imię',
      isSmallScreen: false,
    };
  },
  mounted() {
    this.emitter.on('menu-closed', () => {
      this.menuIsShown = false;
    });
    this.emitter.on('menu-opened', () => {
      this.menuIsShown = true;
    });
  },
  methods: {
    onUserDataClick() {
      // TODO: implementation
    },
    onOffersClick() {
      this.$router.push('/offers');
    },
    onMapClick() {
      this.$router.push('/map');
    },
    onNewOfferClick() {
      // TODO: implementation
    },
    onAboutUsClick() {
      // TODO: implementation
    },
    onLogoClick() {
      this.$router.push('/');
    },
    changeMenuState() {
      this.emitter.emit('change-menu');
    }
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
  }

  #hamburger-icon {
    font-size: v-bind('FONT_SIZES.IMPORTANT');
    color: v-bind('COLORS.TEXT_SECONDARY');
  }
}
</style>

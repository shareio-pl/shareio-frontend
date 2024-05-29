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
        <div id="user-data" @click="onUserDataClick" v-if="!isSmallScreen">
          <UserData :user-surname="surname" :user-first-name="name"/>
          <font-awesome-icon :icon="iconHamburger" id="hamburger-icon"/>
        </div>
      </div>
    </div>

    <div id="header-drawer">
      <img src="../../assets/logo.png" alt="Logo" @click="onLogoClick" class="logo">
      <font-awesome-icon :icon="iconHamburger" id="hamburger-icon" class="showButtons" @click="onHamburgerClick"/>
    </div>

    <div id="drawer" v-if="showDrawer">
      <div id="user-drawer">
        <UserData :user-surname="surname" :user-first-name="name" class="userName"/>
      </div>
      <ButtonPrimary class="button" button-text="Oferty" @click="onOffersClick"/>
      <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
      <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
      <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
    </div>
  </div>
</template>

<script>
import { COLORS } from "../../../public/Consts";
import { FONT_SIZES } from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: { FontAwesomeIcon, UserData, ButtonPrimary },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconHamburger: faBars,
      surname: 'Nazwisko',
      name: 'Imię',
      showDrawer: false,
      isSmallScreen: false,
    };
  },
  mounted() {
    window.addEventListener('resize', this.updateWidth);
    this.updateWidth();
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
    onHamburgerClick() {
      this.showDrawer = !this.showDrawer;
    },
    updateWidth() {
      this.width = window.innerWidth;
      this.isSmallScreen = this.width < 850;
      if (parseInt(this.width) >= 850) {
        this.showDrawer = false;
      }
    },
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

  #drawer, #header-drawer img, .showButtons, #user-drawer {
    display: none;
  }
}

#hamburger-icon {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
  padding-left: 1em;
  cursor: pointer;
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

  #header-drawer .showButtons {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
  }

  #drawer {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: start;
    position: absolute;
    top: 10px;
    right: 0;
    width: 30%;
    background-color: v-bind('COLORS.PRIMARY');
    padding: 20px;
    z-index: 3;
    border-bottom: 2px solid;
    border-color: v-bind('COLORS.SECONDARY');
    opacity: 0.96;
  }

  #user-drawer {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 10px;
  }

  #user-drawer .userName {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  #drawer .button {
    min-width: 100px;
    width: 100%;
    margin-bottom: 10px;
  }

  #hamburger-icon {
    font-size: v-bind('FONT_SIZES.IMPORTANT');
    color: v-bind('COLORS.TEXT_SECONDARY');
  }
}

@media screen and (max-width: 450px) {
  #drawer {
    width: 50%;
  }
}
</style>

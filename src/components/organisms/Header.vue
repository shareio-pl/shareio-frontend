<template>
  <div>
    <div id="header" v-if="!showDrawer">
      <div id="header_wrapper">
        <img src="../../assets/logo.png" alt="" @click="onLogoClick">
        <div id="buttons">
          <ButtonPrimary class="button" button-text="Oferty" @click="onOffersClick"/>
          <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
          <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
          <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
        </div>
        <div id="user-data" @click="onUserDataClick">
          <UserData :user-surname="this.surname" :user-first-name="this.name"/>
          <font-awesome-icon :icon="iconChevronDown" id="arrow-icon"/>
        </div>
      </div>
    </div>

    <div id="header-drawer">
      <img src="../../assets/logo.png" alt="Logo" @click="onLogoClick">
      <font-awesome-icon :icon="showDrawer ? iconChevronUp : iconChevronDown" id="arrow-icon" class="showButtons"
                         @click="onArrowClick"/>
      <div id="user-drawer" @click="onUserDataClick">
        <UserData :user-surname="this.surname" :user-first-name="this.name" class="userName"/>
        <font-awesome-icon :icon="iconChevronDown" id="arrow-icon" class="userArrow"/>
      </div>
    </div>
    <div id="drawer" v-if="showDrawer">
      <ButtonPrimary class="button" button-text="Oferty" @click="onOffersClick"/>
      <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick"/>
      <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick"/>
      <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick"/>
    </div>
  </div>
</template>

<script>
import {COLORS} from "../../../public/Consts";
import {FONT_SIZES} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faChevronDown, faChevronUp} from "@fortawesome/free-solid-svg-icons";

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
      surname: 'Nazwisko',
      name: 'Imię',
      showDrawer: false,
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
    onArrowClick() {
      this.showDrawer = !this.showDrawer;
    },
    updateWidth() {
      this.width = window.innerWidth;
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

#arrow-icon {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
  padding-left: 1em;
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
    justify-content: center;
    align-items: center;
  }

  #header-drawer img {
    cursor: pointer;
    aspect-ratio: 3.166;
    height: calc(3px + 11vw);
    margin-left: 1%;
  }

  #user-drawer {
    display: flex;
    flex-direction: row;
    align-items: center;
    cursor: pointer;
    margin: 1% auto;
    margin-right: 2%;
  }

  #drawer {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    top: 100px;
    width: 100%;
    height: 85px;
    background-color: v-bind('COLORS.PRIMARY');
    padding: 20px;
    z-index: 1;
  }
}
  @media screen and (max-width: 700px) {
    #user-drawer {
      flex-direction: row;
      align-items: center;
    }
    #user-drawer .userName {
      font-size: 15px;
      margin-left:5%;
    }
  }

  @media screen and (max-width: 500px) {
    #drawer {
      height: 60px;
      margin-bottom: 0;
    }
    #header-drawer img {
      height: 38px;
    }
    #user-drawer .userName {
      font-size: 13px;
      margin-left:0;
    }
  }


</style>

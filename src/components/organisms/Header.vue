<template>
  <div id="header">
    <div id="header_wrapper">
      <img src="../../assets/logo.png" @click="onLogoClick">
      <div id="buttons">
        <ButtonPrimary class="button" button-text="Ogłoszenia" @click="onOffersClick" />
        <ButtonPrimary class="button" button-text="Mapa" @click="onMapClick" />
        <ButtonPrimary class="button" button-text="Nowa oferta" @click="onNewOfferClick" />
        <ButtonPrimary class="button" button-text="O nas" @click="onAboutUsClick" />
      </div>
      <div id="user-data" @click="onUserDataClick">
        <UserData :user-surname="this.surname" :user-first-name="this.name" />
        <font-awesome-icon :icon="iconChevronDown" id="arrow-icon" />
      </div>
    </div>
  </div>
</template>

<script>
import { COLORS } from "../../../public/Consts";
import { FONT_SIZES } from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: { FontAwesomeIcon, UserData, ButtonPrimary },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      iconChevronDown: faChevronDown,
      surname: 'Nazwisko',
      name: 'Imię',
    };
  },
  methods:
  {
    onUserDataClick() {
      // TODO: implementation
    },
    onOffersClick() {
      // TODO: implementation
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
      // TODO: implementation
    },
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

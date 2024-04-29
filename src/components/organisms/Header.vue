<template>
  <div id="header">
    <img src="../../assets/logo.png" @click="onLogoClick">
    <div id="buttons">
      <ButtonPrimary button-text="Ogłoszenia" @click="onOffersClick"/>
      <ButtonPrimary button-text="Mapa" @click="onMapClick"/>
      <ButtonPrimary button-text="Nowa oferta" @click="onNewOfferClick"/>
      <ButtonPrimary button-text="O nas" @click="onAboutUsClick"/>
    </div>
    <div id="user-data" @click="onUserDataClick">
      <UserData :user-surname="this.surname" :user-first-name="this.name"/>
      <font-awesome-icon :icon="iconChevronDown" id="arrow-icon"/>
    </div>
  </div>
</template>

<script>
import {COLORS} from "../../../public/Consts";
import {FONT_SIZES} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import UserData from "@/components/atoms/UserData.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faChevronDown} from "@fortawesome/free-solid-svg-icons";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  components: {FontAwesomeIcon, UserData, ButtonPrimary},
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
          // TODO: implementation
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

img {
  margin-left: 2%;
  cursor: pointer;
}

#buttons {
  display: flex;
  align-items: center;
  width: 100%;
  margin-left: 3%;
}

#user-data {
  display: flex;
  align-items: center;
  width: 18%;
  cursor: pointer;
}

#arrow-icon {
  scale: 175%;
  margin-left: 8%;
}
</style>

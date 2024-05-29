<template>
  <div v-if="showMenu" @click='changeStateOfMenu' :class="['menu-page', { sticky: isSticky }]" ref="menuPage">
    <div class="menu-items">
      <div class="pages-menu">
        <div id="user-data">
          <UserData class="user" :user-surname="surname" :user-first-name="name"/>
        </div>
        <hr/>
        <span @click="onOffersClick">Oferty</span>
        <hr/>
        <span @click="onMapClick">Mapa</span>
        <hr/>
        <span @click="onAddOfferClick">Nowa oferta</span>
        <hr/>
        <span @click="onAboutUsClick">O nas</span>
        <hr/>
      </div>
      <span @click="onMyAccountClick">Moje konto</span>
      <span @click="onHelpClick">Pomoc</span>
      <span @click="onPasswordChangeClick">Zmień hasło</span>
      <hr/>
      <span @click="onLogout">Wyloguj się</span>
    </div>
  </div>
</template>

<script>
import {FONT_SIZES, COLORS} from "../../../public/Consts";
import UserData from "@/components/atoms/UserData.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Menu",
  components: {UserData},
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      showMenu: false,
      isSticky: false,
      surname: 'Nazwisko',
      name: 'Imię',
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
      // TODO: Add router push
    },
    onHelpClick() {
      // TODO: Add router push
      console.log('Help clicked');
    },
    onPasswordChangeClick() {
      // TODO: Add router push
    },
    onLogout() {
      // TODO: Add router push
    },
    onLogin() {
      // TODO: Add router push
    },
    onOffersClick() {
      this.$router.push('/offers');
    },
    onMapClick() {
      this.$router.push('/map');
    },
    onAddOfferClick() {
      // TODO: Add router push
    },
    onAboutUsClick() {
      // TODO: Add router push
    },
  },
  mounted() {
    this.emitter.on('change-menu', () => {
      this.changeStateOfMenu();
    });
  },
};
</script>

<style scoped>
.menu-page {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(249, 235, 224, 0.86);
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

.menu-items > span {
  display: flex;
  flex-direction: column;
  align-self: flex-end;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: calc(13px + 0.5vw);
  cursor: pointer;
  font-weight: bold;
}

.menu-items > span:hover {
  text-decoration: underline;
}

hr {
  margin-left: auto;
  width: 100%;
  color: v-bind('COLORS.TEXT_SECONDARY');
  border-width: 2px;
}

.pages-menu > span {
  display: flex;
  flex-direction: column;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: calc(13px + 0.5vw);
  cursor: pointer;
  font-weight: bold;
}

.pages-menu > span:hover {
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
}

@media screen and (max-width: 850px) {
  .menu-page {
    width: 30%;
    margin-left: 70%;
    background-color: rgba(249, 235, 224, 0.92);
  }

  .menu-items {
    width: 100%;
    margin-left: 0;
  }

  .menu-items > span {
    align-self: auto;
  }
}
</style>




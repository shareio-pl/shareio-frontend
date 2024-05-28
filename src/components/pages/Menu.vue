<template>
  <div v-if="showMenu" @click='changeStateOfMenu' :class="['menu-page', { sticky: isSticky }]" ref="menuPage">
    <div class="menu-items">
      <span v-if="isLogged" @click="onMyAccountClick">Moje konto</span>
      <span @click="onHelpClick">Pomoc</span>
      <span @click="onPasswordChangeClick">Zmień hasło</span>
      <hr />
      <span v-if="isLogged" @click="onLogout">Wyloguj się</span>
      <span v-else @click="onLogin">Zaloguj się</span>
    </div>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Menu",
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      showMenu: false,
      isSticky: false,
      isLogged: false, // To obtain from get ID from session (?)
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
  background-color: rgba(249, 235, 224, 0.79);
  top: 100px;
  left: 0;
  z-index: 5;
}

.menu-items {
  display: flex;
  flex-direction: column;
  width: 10%;
  margin-left: 90%;
}

.menu-items>span {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-right: 4%;
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  cursor: pointer;
  font-weight: bold;
}

hr {
  margin-left: auto;
  width: 100%;
  color: v-bind('COLORS.TEXT_SECONDARY');
  border-width: 2px;
}
</style>


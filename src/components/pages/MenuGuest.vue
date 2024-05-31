<template>
  <div v-if="showMenu" @click='changeStateOfMenu' :class="['menu-page', { sticky: isSticky }]" ref="menuPage">
    <div class="menu-items">
      <div class="pages-menu">
        <hr class="firstLine" />
        <span @click="onOffersClick">Oferty</span>
        <hr />
        <span @click="onAddOfferClick">Nowa oferta</span>
        <hr />
        <span @click="onAboutUsClick">O nas</span>
      </div>
      <hr class="longLine" />
      <span @click="onLogin">Zaloguj się</span>
      <hr class="spaceLine" />
      <span @click="onRegister">Zarejestruj się</span>
      <hr class="longLine" />
    </div>
  </div>
</template>

<script>
import { FONT_SIZES, COLORS } from "../../../public/Consts";
export default {
  name: "MenuGuest",
  data() {
    return {
      FONT_SIZES,
      COLORS,
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
    setupUserPictureElement() {
      const userDataElement = this.$refs.userDataComp.$el;
      const userPicture = userDataElement.querySelector('.user-image');
      const userInfo = userDataElement.querySelector('.user-data');
      userDataElement.insertBefore(userInfo, userPicture);
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
    onAddOfferClick() {
      this.$router.push('/login');
    },
    onAboutUsClick() {
      this.$router.push('/about');
    },
  },
  mounted() {
    this.emitter.on('change-menu-guest', () => {
      this.changeStateOfMenu();
    });
    this.$nextTick(() => {
      this.setupUserPictureElement();
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
  .longLine {
    width: calc(100% + 4vw);
    margin-left: calc(-5% - 2vw);
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

@media screen and (min-width: 850px) {
  .pages-menu, .spaceLine {
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


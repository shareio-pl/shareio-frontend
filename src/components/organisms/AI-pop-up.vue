<template>
  <div v-if="isShown" id="background">
    <div id="AI-pop-up">
      <FontAwesomeIcon :icon="XIcon" id="x" @click="close()"/>
      <h1>{{ title }}</h1>
      <textarea placeholder="Wpisz dodatkowe informacje (jeśli nie chcesz nic dodać, kliknij Generuj)" v-model="additionalData"/>
      <ButtonPrimary button-text="Generuj" @click="generateDescription" id="button"/>
    </div>
  </div>
</template>

<script>
import {COLORS, FONT_SIZES} from "../../../public/Consts";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import {faCircleXmark as XIcon} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
  name: "AI-pop-up",
  components: {FontAwesomeIcon, ButtonPrimary},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      title: 'Nasz model nie ma dostępu do zdjęć, jeśli chcesz, możesz podać mu wszelkie dodatkowe informacje, jakie uznasz za istotne',
      additionalData: '',
      isShown: false,
      XIcon: XIcon,
    }
  },
  methods:
      {
        generateDescription() {
          console.log('Additional data from user: ', this.additionalData);
          this.emitter.emit('addedData', {additionalData: this.additionalData});

          this.additionalData = '';
          this.isShown = false;
        },
        close() {
          this.additionalData = '';
          this.isShown = false;
        }
      },
  mounted() {
    this.emitter.on('generateDescription', () => {
      this.isShown = true;
    })
  }
}
</script>

<style scoped>
#background {
  width: 100%;
  height: 100%;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  top: 0;
  left: 0;
  z-index: 5;
}

#AI-pop-up {
  background-color: v-bind('COLORS.PRIMARY');
  position: fixed;
  display: flex;
  flex-direction: column;
  width: 60%;
  height: 72%;
  top: 10%;
  left: 20%;
  border-radius: 15px;
}

h1 {
  color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  margin-top: -1.5%;
}

textarea {
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  width: 90%;
  margin-left: 5%;
  border-radius: 15px;
  padding-bottom: 35%;
  padding-left: 1%;
}

textarea::placeholder {
  color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}

#button {
  margin-top: 1%;
  align-self: flex-end;
  margin-right: 5%;
}

#x {
  cursor: pointer;
  scale: 225%;
  align-self: flex-end;
  margin-top: 1.5%;
  margin-right: 1.5%;
}
</style>

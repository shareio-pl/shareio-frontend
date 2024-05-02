<template>
  <div id="option">
    <div class="checkbox-and-label" @click="toggleCheck">
      <div class="checkbox-circle">
        <div v-if="checked" class="checked"></div>
      </div>
      <span>{{ name }}</span>
    </div>
  </div>
</template>


<script>
import {COLORS, FONT_SIZES} from "../../../public/Consts";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Option",
  data() {
    return {
      checked: false,
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
    }
  },
  props:
      {
        name:
            {
              type: String,
              required: true,
            },
      },
  methods: {
    toggleCheck() {
      this.checked = !this.checked;
    }
  },
}
</script>

<style scoped>
#option {
  display: flex;
  flex-direction: column;
  width: 15%;
  background-color: v-bind('COLORS.MENU_WHITE');
  height: 50%;
  margin-left: 1%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.checkbox-and-label {
  display: flex;
  align-items: center;
}

.checkbox-circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 1px solid v-bind('COLORS.BORDER_BLACK');
  position: relative;
  cursor: pointer;
  margin-right: 4%;
  margin-left: 1%;
}

.checked {
  width: 70%;
  height: 70%;
  border-radius: 50%;
  background-color: v-bind('COLORS.BORDER_BLACK');
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

</style>
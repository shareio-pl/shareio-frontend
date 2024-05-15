<template>
  <div class="change-page">
    <div class="arrow-container">
      <font-awesome-icon v-if="page > 1" :icon="iconChevronLeft" class="arrow" @click="onLeftArrowClick"
        role="button" />
      <div v-else class="arrow-placeholder"></div>
    </div>
    <div class="page-info">
      <span class="page">{{ page }}</span>
      <span class="totalpages">({{ totalPages }})</span>
    </div>
    <div class="arrow-container">
      <font-awesome-icon v-if="page < totalPages" :icon="iconChevronRight" class="arrow" @click="onRightArrowClick"
        role="button" />
      <div v-else class="arrow-placeholder"></div>
    </div>
  </div>
</template>

<script>
import { COLORS, FONT_SIZES } from '../../../public/Consts';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faChevronRight, faChevronLeft } from '@fortawesome/free-solid-svg-icons';

export default {
  name: "ChangePage",
  components: {
    FontAwesomeIcon,
  },
  props: {
    leftArrow: {
      type: Boolean,
      default: false
    },
    rightArrow: {
      type: Boolean,
      default: false
    },
    page: {
      type: Number,
      required: true
    },
    totalPages: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      FontAwesomeIcon: FontAwesomeIcon,
      iconChevronRight: faChevronRight,
      iconChevronLeft: faChevronLeft,
    };
  },
  methods: {
    onLeftArrowClick() {
      this.emitter.emit('left-arrow-clicked');
    },
    onRightArrowClick() {
      this.emitter.emit('right-arrow-clicked');
    }
  }
}
</script>

<style scoped>
.change-page {
  display: inline-flex;
  align-items: center;
}

.page-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0;
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_PRIMARY');
}

.page {
  line-height: 0.8;
}

.totalpages {
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  line-height: 0.8;
}

.leftArrow {
  margin-top: -11px;
  margin-right: 20px;
}

.rightArrow {
  margin-top: -11px;
  margin-left: 20px;
}

.arrow-container {
  width: 40px;
}

.arrow-placeholder {
  width: 100%;
  height: 100%;
}
</style>
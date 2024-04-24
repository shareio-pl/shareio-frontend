<template>
  <div class="change-page">
    <font-awesome-icon v-if="leftArrow" :icon="iconChevronLeft" class="leftArrow" @click="onLeftArrowClick" role="button"/>
    <div class="page-info">
      <span class="page">{{ page }}</span>
      <span class="totalpages">({{ totalPages }})</span>
    </div>
    <font-awesome-icon v-if="rightArrow" :icon="iconChevronRight" class="rightArrow" @click="onRightArrowClick" role="button"/>
  </div>
</template>
<script>
import {COLORS, FONT_SIZES} from '../../../public/Consts';
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
      this.$emit('left-arrow-clicked');
    },
    onRightArrowClick() {
      this.$emit('right-arrow-clicked');
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
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
}

.page {
  line-height: 0.8;
}

.totalpages {
  font-size: v-bind('FONT_SIZES.SECONDARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  line-height: 0.8;
}

.leftArrow{
  margin-top: -11px;
  margin-right: 20px;
}

.rightArrow {
  margin-top: -11px;
  margin-left: 20px;
}

</style>
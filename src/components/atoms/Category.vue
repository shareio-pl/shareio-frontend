<template>
  <div class="category" :class="{ 'bold': selected }" @click="handleClick">
    <span class="dot"> &bull;</span>
    <span class="category-display"> {{ displayName }}</span>
    <span class="number">({{ numberOfOffers }})</span>
  </div>
</template>

<script>
import { FONT_SIZES } from "../../../public/Consts";
import { COLORS } from "../../../public/Consts";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Category",
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      selected: false,
    };
  },
  props: {
    displayName: {
      type: String,
      required: true,
    },
    categoryName: {
      type: String,
      required: true,
    },
    numberOfOffers: {
      type: Number,
      required: true,
    },
    clear: {
      type: Number,
      required: false,
    }
  },
  methods: {
    handleClick() {
      this.selected = !this.selected;
      this.emitter.emit('category-changed', { categoryName: this.categoryName, displayName: this.displayName, selected: this.selected });
    },
    clearCategory() {
      this.selected = false;
      this.emitter.emit('category-changed', { categoryName: this.categoryName, displayName: this.displayName, selected: this.selected });
    }
  },
  watch: {
    clear: function () {
      this.clearCategory();
    }
  },
};
</script>

<style scoped>
.category {
  display: flex;
  align-items: center;
  height: 30px;
  margin-left: min(5%, 10px);
  width: 100%;
  font-size: calc(9px + 0.7vw);
  color: v-bind('COLORS.TEXT_SECONDARY');
  overflow: hidden;
}

.category.bold {
  font-weight: bolder;
  color: v-bind('COLORS.BORDER_BLACK');
}

.category:hover {
  cursor: pointer;
  color: v-bind('COLORS.TEXT_HOVER');
}

.dot {
  margin-right: 10px;
}

.category-display {
  flex-grow: 1;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
}

.number {
  margin-left: auto;
  margin-right: min(10%, 20px);
}

@media screen and (max-width: 1000px) {
  .number {
  }
}
</style>
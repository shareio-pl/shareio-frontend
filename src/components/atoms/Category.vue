<template>
  <div class="category">
    <span>&bull; {{ category }}</span>
    <span class="number">({{ numberOfOffers }})</span>
  </div>
</template>

<script>
export default {
// eslint-disable-next-line vue/multi-word-component-names
  name: "Category",
  props: {
    category: {
      type: String,
      required: true,
    },
    numberOfOffers: {
      type: Number,
      required: true,
    },
  },
};
</script>

<style scoped>
.category {
  display: flex;
  justify-content: space-between;
  margin-left: min(5%, 10px);
}

.category span {
  margin-right: min(5%, 10px);
}
</style>
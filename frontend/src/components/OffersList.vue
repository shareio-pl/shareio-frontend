<template>
  <div class="offers-list">
    <OfferCard v-for="(offer, index) in offers" :key="index" :offer="offer" />
  </div>
</template>

<script>
import OfferCard from "./OfferCard.vue";

export default {
  name: "OffersList",
  components: {
    OfferCard,
  },
  props: {
    offers: {
      type: Array,
      required: true,
    },
  },
};
</script>

<template>
  <div class="offer-card">
    <div class="offer-image">
      <img :src="offer.image" alt="Offer image" />
    </div>
    <div class="offer-details">
      <h2>{{ offer.name }}</h2>
      <p>{{ offer.address }}</p>
    </div>
    <div class="offer-action">
      <div>
        <button @click="viewOffer">View Offer</button>
        <RatingDisplay :rating="offer.rating" :numRatings="offer.numRatings" />
      </div>
    </div>
  </div>
</template>

<script>
import RatingDisplay from "./RatingDisplay.vue";

export default {
  name: "OfferCard",
  components: {
    RatingDisplay,
  },
  props: {
    offer: {
      type: Object,
      required: true,
    },
  },
  methods: {
    viewOffer() {
      // TODO
    },
  },
};
</script>

<style scoped>
.offer-card {
  display: flex;
  justify-content: space-between;
  width: 90%;
  margin: 0 auto;
  border: 1px solid #ccc;
  padding: 20px;
  box-sizing: border-box;
}

.offer-image img {
  width: 100px;
  height: 100px;
}

.offer-details {
  flex-grow: 1;
  margin-left: 20px;
}

.offer-action {
  display: flex;
  align-items: center;
}

.offer-action div {
  display: flex;
  flex-direction: column;
}

.offer-action button {
  margin-bottom: 10px;
}

.rating-display {
  display: flex;
  align-items: center;
}

.num-ratings {
  margin-right: 5px;
}

.star {
  margin-right: 2px;
}
</style>

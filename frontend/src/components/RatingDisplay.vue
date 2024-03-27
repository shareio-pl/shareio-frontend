<template>
  <div class="rating-display">
    <span class="num-ratings">{{ numRatings }}</span>
    <div class="stars">
      <span v-for="n in 5" :key="n" class="star">
        {{ getStar(n) }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "RatingDisplay",
  props: {
    rating: {
      type: Number,
      required: true,
    },
    numRatings: {
      type: Number,
      required: true,
    },
  },
  methods: {
    getStar(n) {
      if (this.rating >= n) {
        return "★";
      } else if (this.rating >= n - 0.5) {
        return "✬"; /// Placeholder until I find better icon.
      } else {
        return "☆";
      }
    },
  },
};
</script>

<style scoped>
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

.fas {
  color: #ffc107;
}
</style>

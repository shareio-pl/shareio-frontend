<template>
  <div id="filtersComponent">
    <div id="categoryName">
      <p> Kategorie </p>
    </div>
    <div id="categories">
      <Category v-for="category in categories" :key="category.category" :category="category.category"
        :number-of-offers="category.numberOfOffers" style="width: 90%;" />
    </div>
    <div id="filterName">
      <p> Filtry </p>
    </div>
    <div id="filters">
      <FilterOpenDefault class="filter" name="Czas (dni)" placeholder="Do..." style="width: 90%;" identifier="time"
        numericInputOnly="true" />
      <FilterOpenDefault class="filter" name="Odległość (km)" placeholder="Do..." style="width: 90%;"
        identifier="distance" />
      <FilterOptions class="filter" name="Stan" />
      <FilterOpenRate class="filter" filterName="Ocena wystawiającego" />
    </div>
  </div>
</template>

<script>
import FilterOpenDefault from '../atoms/FilterOpenDefault.vue';
import FilterOptions from '../atoms/FilterOptions.vue';
import Category from '../atoms/Category.vue';
import FilterOpenRate from '../atoms/FilterOpenRate.vue';
import { COLORS, FONT_SIZES } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Filters",
  components: { FilterOpenDefault, FilterOpenRate, FilterOptions, Category },
  mounted() {
    this.emitter.on('filter-open-default', this.handleFilterOpenDefault);
    this.emitter.on('filter-stars', this.handleFilterStars);
    this.emitter.on('filter-options', this.handleFilterOptions);
  },
  props: {
    categories: {
      type: Array,
      required: true
    }
  },
  methods: {
    handleFilterOpenDefault(payload) {
      if (payload.identifier === 'time') {
        this.time_chosen = payload.input;
      } else if (payload.identifier === 'distance') {
        this.distance_chosen = payload.input;
      }
      this.sendFilters();
    },
    handleFilterStars(payload) {
      this.stars_chosen = payload.starsAmount;
      this.sendFilters();
    },
    handleFilterOptions(payload) {
      this.option_chosen = payload.optionName;
      this.sendFilters();
    },
    sendFilters() {
      this.emitter.emit('filter', {
        time_chosen: this.time_chosen,
        option_chosen: this.option_chosen,
        stars_chosen: this.stars_chosen,
        distance_chosen: this.distance_chosen
      });
    },
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      time_chosen: '',
      option_chosen: '',
      stars_chosen: '',
      distance_chosen: '',
    }
  }
}
</script>

<style scoped>
#filtersComponent {
  display: flex;
  flex-direction: column;
  border-radius: 0.5em;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  width: 100%;
  /*box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);*/
}

#filterName,
#categoryName {
  display: flex;
  justify-content: left;
  width: 90%;
  margin-left: 5%;
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.SECONDARY');
}

#categories,
#filters {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

#filters {
  margin-bottom: 4%;
}

.filter {
  margin-bottom: 2%;
  width: 90%;
  display: flex;
  justify-content: center;
}
</style>
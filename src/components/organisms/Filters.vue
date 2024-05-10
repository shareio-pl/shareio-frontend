<template>
  <div id="filtersComponent">
    <div id="categoryName">
      <p> Kategorie </p>
    </div>
    <div id="categories">
      <!-- TODO: change to endpoint call -->
      <Category category="Szopy" numberOfOffers="100" style="width: 90%;" />
      <Category category="Elektronika" numberOfOffers="5" style="width: 90%;" />
      <Category category="Kamienice" numberOfOffers="5" style="width: 90%;" />
      <Category category="Książki" numberOfOffers="4" style="width: 90%;" />
      <Category category="Mosty" numberOfOffers="5" style="width: 90%;" />
    </div>
    <div id="filterName">
      <p> Filtry </p>
    </div>
    <div id="filters">
      <FilterClosed class="filter" name="Czas" style="width: 90%;" />
      <FilterOpenDefault class=" filter" name="Odległość (km)" placeholder="Do..." style="width: 90%;" />
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
import FilterClosed from '../atoms/FilterClosed.vue';
import { COLORS, FONT_SIZES } from "../../../public/Consts";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Filters",
  components: { FilterOpenDefault, FilterOpenRate, FilterOptions, Category, FilterClosed },
  mounted() {
    this.emitter.on('filter-closed', this.handleFilter);
    this.emitter.on('filter-open-default', this.handleFilter);
    this.emitter.on('filter-stars', this.handleFilter);
    this.emitter.on('filter-options', this.handleFilter);
  },
  methods: {
    handleFilter(payload) {
      // TODO TOMORROW
      console.log('Received filter event with payload: ', payload);
    }
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
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
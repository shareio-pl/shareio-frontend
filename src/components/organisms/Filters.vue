<template>
  <div id="filtersComponent">
    <div id="categoryName">
      <div class="filter-header-left">
        <FontAwesomeIcon :icon="CategoryIcon" id="prop-icon" />
        <p> Kategorie </p>
        <FontAwesomeIcon :icon="XIcon" class="clear-icon" @click="onCategoriesClearClicked" />
      </div>
    </div>
    <div id="categories">
      <Category v-for="category in categories" :key="category.name" :displayName="category.displayName"
        :category-name="category.categoryName" :number-of-offers="category.numberOfOffers" :clear="clearCounterCategories"
        style="width: 90%;" />
    </div>
    <div id="filterName">
      <div class="filter-header-left">
        <FontAwesomeIcon :icon="FilterIcon" id="prop-icon" />
        <p> Filtry </p>
        <FontAwesomeIcon :icon="XIcon" class="clear-icon" @click="onFiltersClearClicked" />
      </div>
    </div>
    <div id="filters">
      <FilterOpenDefault class="filter" name="Czas (dni)" placeholder="Do..." style="width: 90%;" identifier="time"
        numericInputOnly="true" :prop-icon="ClockIcon" :clear="clearCounter" />
      <FilterOpenDefault class="filter" name="Odległość (km)" placeholder="Do..." style="width: 90%;"
        identifier="distance" :prop-icon="RoadIcon" :clear="clearCounter" />
      <FilterOptions class="filter" name="Stan" :prop-icon="StateIcon" :clear="clearCounter" />
      <FilterOpenRate class="filter" filterName="Ocena wystawiającego" :prop-icon="RatingIcon" :clear="clearCounter" />
    </div>
  </div>
</template>

<script>
import FilterOpenDefault from '../atoms/FilterOpenDefault.vue';
import FilterOptions from '../atoms/FilterOptions.vue';
import Category from '../atoms/Category.vue';
import FilterOpenRate from '../atoms/FilterOpenRate.vue';

import { COLORS, FONT_SIZES } from "../../../public/Consts";
import { faClock as ClockIcon } from '@fortawesome/free-solid-svg-icons';
import { faRoad as RoadIcon } from '@fortawesome/free-solid-svg-icons';
import { faTag as StateIcon } from '@fortawesome/free-solid-svg-icons';
import { faFaceSmileBeam as RatingIcon } from '@fortawesome/free-solid-svg-icons';
import { faFilter as FilterIcon } from '@fortawesome/free-solid-svg-icons';
import { faClipboardList as CategoryIcon } from '@fortawesome/free-solid-svg-icons';
import { faCircleXmark as XIcon } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Filters",
  components: { FilterOpenDefault, FilterOpenRate, FilterOptions, Category, FontAwesomeIcon },
  mounted() {
    this.emitter.on('filter-open-default', this.handleFilterOpenDefault);
    this.emitter.on('filter-stars', this.handleFilterStars);
    this.emitter.on('filter-options', this.handleFilterOptions);
    this.emitter.on('category-changed', this.handleCategoryChange);
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
      //this.sendFilters(); TODO: Send filters via button in different commit
    },
    handleCategoryChange(payload) {
      if (payload.selected) {
        this.categories_chosen.push(payload.categoryName);
      } else {
        this.categories_chosen = this.categories_chosen.filter(category => category !== payload.categoryName);
      }
      console.log('Categories chosen: ', this.categories_chosen);
    },
    onFiltersClearClicked() {
      this.time_chosen = '';
      this.option_chosen = '';
      this.stars_chosen = '';
      this.distance_chosen = '';
      this.clearCounter++;
    },
    onCategoriesClearClicked() {
      this.clearCounterCategories++;
    },
    handleFilterStars(payload) {
      this.stars_chosen = payload.starsAmount;
    },
    handleFilterOptions(payload) {
      this.option_chosen = payload.optionName;
    },
    sendFilters() {
      this.emitter.emit('filter', {
        time_chosen: this.time_chosen,
        option_chosen: this.option_chosen,
        stars_chosen: this.stars_chosen,
        distance_chosen: this.distance_chosen
      });
      console.log('Filters sent: ', this.time_chosen, this.option_chosen, this.stars_chosen, this.distance_chosen);
    },
  },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      ClockIcon: ClockIcon,
      RoadIcon: RoadIcon,
      StateIcon: StateIcon,
      RatingIcon: RatingIcon,
      FilterIcon: FilterIcon,
      CategoryIcon: CategoryIcon,
      XIcon: XIcon,
      time_chosen: '',
      option_chosen: '',
      stars_chosen: '',
      distance_chosen: '',
      categories_chosen: [],
      clearCounter: 0, // This needs some sort of explanation - it's used to tell the children that something has changed via props. 
      // It's int because boolean would have been... quite inelegant, although we don't care about this value - what matters is
      // that something changed.
      clearCounterCategories: 0,
      // See reasoning above
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
  margin-top: 4%;
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.SECONDARY');
}

.filter-header-left {
  display: flex;
  align-items: center;
  margin-bottom: 0.5em;
  justify-content: space-between;
  width: 98%;
}

p {
  margin-bottom: 0;
  text-align: left;
  flex-grow: 1;
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

.clear-icon {
  cursor: pointer;
}
</style>
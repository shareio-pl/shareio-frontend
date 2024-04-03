<template>
  <div class="search-input">
    <font-awesome-icon :icon="dropdownIcon" class="search-icon" />
    <input type="text" :placeholder="placeholder" v-model="searchTerm" @input="updateSearch" class="search-field" />
  </div>
</template>

<script>
import { faSearch as SearchIcon } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

export default {
  name: "SearchInput",
  components: {
    FontAwesomeIcon,
  },
  props: {
    placeholder: {
      type: String,
      required: true,
    },
    dropdownIcon: {
      type: Object,
      default: SearchIcon,
    }
  },
};
</script>

<style scoped>
.search-input {
  position: relative;
  display: inline-block;
  width: 90%;
}

.search-icon {
  position: absolute;
  left: min(3%, 14px);
  top: 50%;
  transform: translateY(-50%);
}

.search-field {
  padding-left: min(10%, 40px);
  width: 100%;
}
</style>
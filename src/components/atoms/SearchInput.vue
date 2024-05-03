<template>
  <div class="search-input">
    <font-awesome-icon :icon="SearchIcon" class="search-icon"/>
    <input type="text" :placeholder="placeholder" class="search-field" v-model="input" @keyup.enter="search"/>
  </div>
</template>

<script>
import {faSearch as SearchIcon} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';

export default {
  name: "SearchInput",
  components: {
    FontAwesomeIcon,
  },
  data() {
    return {
      SearchIcon: SearchIcon,
      input: '',
    };
  },
  props: {
    placeholder: {
      type: String,
      required: true,
    },
  },
  methods: {
    search() {
      console.log('User input: ', this.input);
      this.emitter.emit('search', {input: this.input});
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
  height: 45px;
}
</style>

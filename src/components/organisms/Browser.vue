<template>
  <div id="browser">
    <SearchInput @keydown.enter.prevent="onButtonClick" placeholder="Znajdź swój przedmiot..." />
    <SortDropdown />
    <ButtonSecondary button-text="Szukaj" @click="onButtonClick" />
  </div>
</template>

<script>
import SearchInput from "@/components/atoms/SearchInput.vue";
import SortDropdown from "@/components/atoms/SortDropdown.vue";
import ButtonSecondary from "@/components/atoms/ButtonSecondary.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Browser",
  data() {
    return {
      input: '',
      sorting: 'CLOSEST',
    }
  },
  methods: {
    onButtonClick() {
      console.log('Search button clicked!');
      this.emitter.emit('browser-change', { input: this.input, sorting: this.sorting });
    }
  },
  mounted() {
    this.emitter.on('search', (data) => {
      this.input = data.input;
    });

    this.emitter.on('sortChange', (data) => {
      console.log('Sort change event received', data);
      this.sorting = data.option;
    });
  },
  components: { ButtonSecondary, SortDropdown, SearchInput },
}
</script>

<style scoped>
#browser {
  display: flex;
  width: 45%;
  align-items: center;
}
</style>

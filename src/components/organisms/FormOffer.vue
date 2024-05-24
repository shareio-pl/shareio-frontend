<template>
  <form id="form-offer" @submit.prevent="submitForm">
    <div id="form-offer-title">
      <FieldInput v-model="offerTitle" placeholder="Tytuł" label="Tytuł"
        :error="{ active: v$.offerTitle.$error && v$.offerTitle.$dirty, message: offerTitleError }" />
    </div>
    <div class="form-offer-flex">
      <div id="form-offer-location">
        <FieldInput v-model="offerCity" placeholder="Miasto" label="Miasto"
          :error="{ active: v$.offerCity.$error && v$.offerCity.$dirty, message: offerCityError }" />
        <FieldInput v-model="offerStreet" placeholder="Ulica" label="Ulica"
          :error="{ active: v$.offerStreet.$error && v$.offerStreet.$dirty, message: offerStreetError }" />
        <FieldInput v-model="offerHomeNumber" placeholder="Numer domu" label="Numer domu"
          :error="{ active: v$.offerHomeNumber.$error && v$.offerHomeNumber.$dirty, message: offerHomeNumberError }" />
      </div>
      <div id="form-offer-options">
        <DropdownSelect :options="categories" type="Category" placeholder="Wybierz kategorię" />
        <DropdownSelect :options="states" type="State" placeholder="Wybierz stan" />
      </div>
    </div>
    <div id="form-offer-description">
      <FieldTextBox v-model="offerDescription" placeholder="Opis" label="Opis przedmiotu"
        :error="{ active: v$.offerDescription.$error && v$.offerDescription.$dirty, message: offerDescriptionError }" />
    </div>
  </form>
</template>

<script>
import { required, minLength, maxLength } from '@vuelidate/validators'

import { useVuelidate } from '@vuelidate/core'

import FieldInput from "../atoms/FieldInput.vue";
import FieldTextBox from "../atoms/FieldTextBox.vue";
import DropdownSelect from "../atoms/DropdownSelect.vue";

export default {
  name: 'OfferForm',
  components: {
    FieldInput,
    FieldTextBox,
    DropdownSelect,
  },
  props: {
    city: String,
    street: String,
    houseNumber: String,
    flatNumber: String,
    postCode: String,
    country: String,
    region: String,
    categories: {
      type: Array,
      required: true
    },
    states: {
      type: Array,
      required: true
    },
  },
  data() {
    return {
      offerTitle: '',
      offerTitleError: '',
      offerCity: this.city,
      offerCityError: '',
      offerStreet: this.street,
      offerStreetError: '',
      offerHomeNumber: this.houseNumber,
      offerHomeNumberError: '',
      offerDescription: '',
      offerDescriptionError: '',
      category: '',
      state: '',
    };
  },
  validations() {
    return {
      offerTitle: { required, minLength: minLength(5) },
      offerDescription: { required, minLength: minLength(20), maxLength: maxLength(300) },
      offerCity: { required, minLength: minLength(3) },
      offerStreet: { required, minLength: minLength(3) },
      offerHomeNumber: { required, minLength: minLength(1) },
    }
  },
  setup() {
    const v$ = useVuelidate()
    return { v$ }
  },
  methods: {
    submitForm() {
      this.v$.$validate()
      if (this.v$.$error) {
        return
      }
    },
    getFormData() {
      this.v$.$validate()
      if (this.v$.$error) {
        return null;
      }
      return {
        offerTitle: this.offerTitle,
        offerDescription: this.offerDescription,
        offerCountry: this.offerCountry,
        offerRegion: this.offerRegion,
        offerCity: this.offerCity,
        offerCategory: this.category,
        offerState: this.state,
      };
    },
    handleDropdownOptions(data) {
      if (data.type === 'Category') {
        this.category = data.option;
        console.log(this.category);
      }
      else if (data.type === 'State') {
        this.state = data.option;
        console.log(this.state);
      }
    },
  },
  mounted() {
    this.emitter.on('dropdown-change', this.handleDropdownOptions);
  },
  watch: {
    'v$.offerTitle.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerTitle.minLength.$model) {
          this.offerTitleError = `Tytuł musi mieć co najmniej ${this.v$.offerTitle.minLength.$params.min} znaków`;
        }
      } else {
        this.offerTitleError = '';
      }
    },
    'v$.offerCity.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerCity.minLength.$model) {
          this.offerCityError = `Miasto musi mieć co najmniej ${this.v$.offerCity.minLength.$params.min} znaków`;
        }
      } else {
        this.offerCityError = '';
      }
    },
    'v$.offerStreet.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerStreet.minLength.$model) {
          this.offerStreetError = `Ulica musi mieć co najmniej ${this.v$.offerStreet.minLength.$params.min} znaków`;
        }
      } else {
        this.offerStreetError = '';
      }
    },
    'v$.offerHomeNumber.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerHomeNumber.minLength.$model) {
          this.offerHomeNumberError = `Numer domu musi mieć co najmniej ${this.v$.offerHomeNumber.minLength.$params.min} znaków`;
        }
      } else {
        this.offerHomeNumberError = '';
      }
    },
    'v$.offerDescription.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerDescription.minLength.$model) {
          this.offerDescriptionError = `Opis musi mieć co najmniej ${this.v$.offerDescription.minLength.$params.min} znaków`;
        }
        else if (!this.v$.offerDescription.maxLength.$model) {
          this.offerDescriptionError = `Opis musi mieć co najwyżej ${this.v$.offerDescription.maxLength.$params.max} znaków`;
        }
      } else {
        this.offerDescriptionError = '';
      }
    },
  },
};
</script>

<style scoped>
#form-offer {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
}

#form-offer-title {
  margin-top: 1em;
  margin-bottom: 0;
}

#form-offer-location,
#form-offer-options {
  display: flex;
  flex-direction: column;
  width: 48%;
  margin-top: 1em;
  margin-bottom: 1em;
}

#form-offer-description {
  display: flex;
  flex-direction: column;
  height: 200%;
}



.form-offer-flex {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.options {
  margin-bottom: 1em;
}
</style>
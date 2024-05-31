<template>
  <form id="form-offer" @submit.prevent="submitForm">
    <AIPopUp/>
    <div id="form-offer-title">
      <FieldInput v-model="offerTitle" placeholder="Tytuł" label="Tytuł"
                  :error="{ active: v$.offerTitle.$error && v$.offerTitle.$dirty, message: '' }"/>
    </div>
    <div class="form-offer-flex">
      <div id="form-offer-location">
        <FieldInput v-model="offerCity" placeholder="Miasto" label="Miasto"
                    :error="{ active: v$.offerCity.$error && v$.offerCity.$dirty, message: '' }"/>
        <FieldInput v-model="offerStreet" placeholder="Ulica" label="Ulica"
                    :error="{ active: v$.offerStreet.$error && v$.offerStreet.$dirty, message: '' }"/>
        <FieldInput v-model="offerHomeNumber" placeholder="Numer domu" label="Numer domu"
                    :error="{ active: v$.offerHomeNumber.$error && v$.offerHomeNumber.$dirty, message: '' }"/>
      </div>
      <div id="form-offer-options">
        <DropdownSelect :options="categories" type="Category" placeholder="Wybierz kategorię"/>
        <DropdownSelect :options="states" type="State" placeholder="Wybierz stan"/>
      </div>
    </div>
    <div id="form-offer-description" class="position-relative">
      <FieldTextBox v-model="offerDescription" placeholder="Opis" label="Opis przedmiotu" style="width:200%;"
                    :disabled="isAIDescriptionLoading"
                    :error="{ active: v$.offerDescription.$error && v$.offerDescription.$dirty, message: '' }"/>
      <div class="spinner" v-if="isAIDescriptionLoading"></div>
    </div>
  </form>
</template>

<script>
import {GATEWAY_ADDRESS} from "../../../public/Consts";

import {required, minLength, maxLength} from '@vuelidate/validators'
import {useVuelidate} from '@vuelidate/core';

import axios from 'axios';

import FieldInput from "../atoms/FieldInput.vue";
import FieldTextBox from "../atoms/FieldTextBox.vue";
import DropdownSelect from "../atoms/DropdownSelect.vue";
import AIPopUp from "@/components/organisms/AI-pop-up.vue";

export default {
  name: 'OfferForm',
  components: {
    AIPopUp,
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
    generateDescription: {
      type: Number,
      required: false
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
      condition: '',
      categoryDisplayName: '',
      conditionDisplayName: '',
      additionalData: '',
      isAIDescriptionLoading: false,
    };
  },
  validations() {
    return {
      offerTitle: {required, minLength: minLength(5)},
      offerDescription: {required, minLength: minLength(20), maxLength: maxLength(425)},
      offerCity: {required, minLength: minLength(3)},
      offerStreet: {required, minLength: minLength(3)},
      offerHomeNumber: {required, minLength: minLength(1)},
      category: {required},
      condition: {required},
    }
  },
  setup() {
    const v$ = useVuelidate()
    return {v$}
  },
  methods: {
    submitForm() {
      this.v$.$validate();
      if (this.v$.$error) {
        return
      }
    },
    async checkIfCanGenerate() {
      if (this.offerTitle === '' || this.condition === '' || this.category === '') {
        console.log("Offer title, condition or category is empty");
        this.emitter.emit('error', {error: 'Tytuł, stan lub kategoria jest pusta!'});
      } else {
        this.emitter.emit('generateDescription');
      }
    },
    async getFormData() {
      this.v$.$validate();
      await this.$nextTick();
      if (this.v$.$error) {
        // TODO better message and change the error font
        let errorMessage = '';
        if (this.v$.offerTitle.$error) {
          errorMessage += this.offerTitleError + ', ';
        } else if (this.v$.offerCity.$error) {
          errorMessage += this.offerCityError + ', ';
        } else if (this.v$.offerStreet.$error) {
          errorMessage += this.offerStreetError + ', ';
        } else if (this.v$.offerHomeNumber.$error) {
          errorMessage += this.offerHomeNumberError + ', ';
        } else if (this.v$.offerDescription.$error) {
          if (this.offerDescription.length < this.v$.offerDescription.minLength.$params.min) {
            this.offerDescriptionError = `Opis musi mieć co najmniej ${this.v$.offerDescription.minLength.$params.min} znaków`;
          } else if (this.offerDescription.length > this.v$.offerDescription.maxLength.$params.max) {
            this.offerDescriptionError = `Opis musi mieć co najwyżej ${this.v$.offerDescription.maxLength.$params.max} znaków`;
          }
          errorMessage += this.offerDescriptionError + ', ';
        } else if (this.category === '') {
          errorMessage += 'Kategoria jest obowiązkowa, ';
        } else if (this.condition === '') {
          errorMessage += 'Stan jest obowiązkowy, ';
        }
        errorMessage = errorMessage.slice(0, -2);
        this.emitter.emit('error', {error: errorMessage});
        console.log("Error w walidacji: ", errorMessage);
        return null;
      } else {
        return {
          offerTitle: this.offerTitle,
          offerDescription: this.offerDescription,
          offerHouseNumber: this.offerHomeNumber,
          offerCity: this.offerCity,
          offerStreet: this.offerStreet,
          offerCategory: this.category,
          offerState: this.condition,
        };
      }
    },
    handleDropdownOptions(data) {
      console.log("Dropdown change: ", data);
      if (data.type === 'Category') {
        this.category = data.option;
        this.categoryDisplayName = data.displayName;
      } else if (data.type === 'State') {
        this.condition = data.option;
        this.conditionDisplayName = data.displayName;
      }
    },
    generateWithoutAdditionalData() {
      this.isAIDescriptionLoading = true;
      axios.get(GATEWAY_ADDRESS + `/offer/generateDescription`, {
        params: {
          title: this.offerTitle, condition: this.conditionDisplayName,
          category: this.categoryDisplayName
        }
      }).then((response) => {
        console.log("Description from AI: ", response.data);
        this.offerDescription = response.data;
        this.isAIDescriptionLoading = false;
      }).catch((error) => {
        console.log(error);
        this.emitter.emit('axiosError', {error: error.response.status});
        this.isAIDescriptionLoading = false;
      })
    },
    generateWithAdditionalData() {
      this.isAIDescriptionLoading = true;
      axios.get(GATEWAY_ADDRESS + `/offer/generateDescription`, {
        params: {
          title: this.offerTitle,
          condition: this.conditionDisplayName,
          category: this.categoryDisplayName,
          additionalData: this.additionalData
        }
      }).then((response) => {
        console.log("Description from AI: ", response.data);
        this.offerDescription = response.data;
        this.isAIDescriptionLoading = false;
      }).catch((error) => {
        console.log(error);
        this.emitter.emit('axiosError', {error: error.response.status});
        this.isAIDescriptionLoading = false;
      })
    },
  },
  mounted() {
    this.emitter.on('dropdown-change', this.handleDropdownOptions);

    this.emitter.on('addedData', (data) => {
      if (data.additionalData === '') {
        this.generateWithoutAdditionalData();
      } else {
        this.additionalData = data.additionalData;
        this.generateWithAdditionalData();
      }
    })
  },
  watch: {
    generateDescription: function () {
      this.checkIfCanGenerate();
    },
    'v$.offerTitle.$error'(newVal) {
      if (newVal) {
        if (!this.v$.offerTitle.minLength.$model) {
          this.offerTitleError = `Tytuł musi mieć co najmniej ${this.v$.offerTitle.minLength.$params.min} znaków`;
          console.log(this.offerTitleError);
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
      // TODO: Fix this, rn it doesn't seem to work with ifs.
      if (newVal) {
        if (!this.v$.offerDescription.minLength.$model) {
          this.offerDescriptionError = `Opis musi mieć co najmniej ${this.v$.offerDescription.minLength.$params.min} znaków`;
        } else if (!this.v$.offerDescription.maxLength.$model) {
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
  justify-content: center;
  align-items: center;
  position: relative;
  width: 100%;
}

.form-offer-flex {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.options {
  margin-bottom: 1em;
}

.position-relative {
  position: relative;
}

.spinner {
  border: 8px solid #f3f3f3;
  border-top: 8px solid #3498db;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  animation: spin 2s linear infinite;
  position: absolute;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}
</style>

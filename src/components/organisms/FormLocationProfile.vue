<template>
  <form id="change-location-user" @submit.prevent="submitForm">
    <div id="change-location-data">
      <div id="owner-header">
        <p> Zmień dane </p>
      </div>
      <div id="register-user-location">
        <DropdownSelect :options="COUNTRIES" type="country" placeholder="Polska" isForCountries=true />
        <FieldInput class="full-width" v-model="region" label="Województwo *" displayBlankSpaceBelow=true />
        <FieldInput class="full-width" v-model="city" label="Miasto *"
          :error="{ active: v$.city.$error && v$.city.$dirty, message: cityError }" displayBlankSpaceBelow=true />
        <FieldInput class="full-width" v-model="street" label="Ulica *" displayBlankSpaceBelow=true />

        <FieldInput class="full-width" v-model="postCode" label="Kod pocztowy *" displayBlankSpaceBelow=true />
        <div class="input-row">
          <FieldInput v-model="houseNumber" label="Nr. domu" displayBlankSpaceBelow=true />
          <FieldInput v-model="flatNumber" label="Mieszkanie" displayBlankSpaceBelow=true />
        </div>
        <ButtonPrimary buttonText="Zapisz zmiany" style="margin-left:0; width: 50%;" />
      </div>
    </div>
  </form>
</template>

<script>
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import { FONT_SIZES, COLORS, FONTS, COUNTRIES_LIST } from "../../../public/Consts";
import { required, minLength, maxLength } from '@vuelidate/validators';
import { useVuelidate } from '@vuelidate/core';

import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import DropdownSelect from "@/components/atoms/DropdownSelect.vue";

export default {
  name: "FormLocationProfile",
  components: {
    FieldInput,
    ButtonPrimary,
    DropdownSelect,
  },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      FONTS: FONTS,
      COUNTRIES: COUNTRIES_LIST,

      name: null,
      surname: null,
      dateOfBirth: null,

      country: "Polska",
      countryError: "",
      region: "",
      city: "",
      street: "",
      postCode: "",
      houseNumber: "",
      flatNumber: null,
    }
  },
  props: {
    id: {
      type: String,
      required: true
    }
  },
  validations() {
    return {
      city: { required, minLength: minLength(3), maxLength: maxLength(20) },
    }
  },
  setup() {
    const v$ = useVuelidate();
    return { v$ };
  },
  watch: {
    'v$.country.$model'() {
      if (!this.v$.country.required.$model) {
        this.countryError = "Kraj jest wymagany";
      }
      else {
        this.countryError = '';
      }
    },
  },
  methods: {
    submitForm() {
      this.v$.$validate();

      let data = {
        name: this.name,
        surname: this.surname,
        dateOfBirth: this.dateOfBirth,

        country: this.country,
        region: this.region,
        city: this.city,
        street: this.street,
        postCode: this.postCode,
        houseNumber: this.houseNumber,
        flatNumber: this.flatNumber,
      }

      console.log(data);

      axios.put(GATEWAY_ADDRESS + '/user/modify/' + this.id, data, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token')
        }
      })
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.error(error);
        });
    },
    async getOwnerData() {
      axios.get(GATEWAY_ADDRESS + `/user/get/${this.id}`).then((response) => {
        console.log(response.data);
        this.name = response.data.name;
        this.surname = response.data.surname;
        this.dateOfBirth = response.data.dateOfBirth;
        this.ownerImageId = response.data.photoId.id;
        this.addressId = response.data.address.id;
      })
        .then(() => {
          this.getAddressData(this.addressId);
        })
        .catch(error => {
          console.error('ERROR: ', error);

          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    async getAddressData(addressId) {
      return axios.get(GATEWAY_ADDRESS + '/address/get/' + addressId)
        .then((response) => {
          console.log('Address data: ', response.data);
          this.city = response.data.city;
          this.street = response.data.street;
          this.houseNumber = response.data.houseNumber;
          this.flatNumber = response.data.flatNumber;
          this.region = response.data.region;
          this.country = response.data.country;
          this.postCode = response.data.postCode;
        })
        .catch((error) => {
          console.error('Error getting address data: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    }
  },

  mounted() {
    this.emitter.on('dropdown-change', (data) => {
      console.log(data);
      this.country = data.option;
      console.log(this.country);
    });
    this.getOwnerData();

  }
}
</script>

<style>
#owner-header {
  width: 100%;
  height: 25%;
  margin-bottom: 0px;
  color: v-bind('COLORS.TEXT_PRIMARY');
  background-color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
}

#register-user-data {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

#register-user-location {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}

.input-row {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
  width: 100%;
}

.input-row>* {
  width: 48%;
}

.full-width {
  width: 100%;
}

.info {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: start;
  margin-left: 25%;
}

.dp__theme_light {
  width: 40%;
  height: 150%;
  --dp-text-color: gray;
  --dp-font-size: v-bind("FONT_SIZES.PRIMARY");
  --dp-background-color: v-bind("COLORS.OFFER_BACKGROUND");
  --dp-font-family: v-bind("FONTS.PRIMARY");
  --dp-common-padding: 0.75rem;
}
</style>
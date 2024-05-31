<template>
  <form id="register-user" @submit.prevent="submitForm">
    <div id="register-user-data">
      <div class="input-row">
        <FieldInput v-model="email" label="Email *"
          :error="{ active: v$.email.$error && v$.email.$dirty, message: emailError }" displayBlankSpaceBelow=true />
        <FieldInput v-model="name" label="Imię *" :error="{ active: v$.name.$error, message: nameError }"
          displayBlankSpaceBelow=true />
      </div>
      <div class="input-row">
        <FieldInput v-model="surname" label="Nazwisko *"
          :error="{ active: v$.surname.$error && v$.surname.$dirty, message: surnameError }"
          displayBlankSpaceBelow=true />
        <div class="date-picker-wrapper">
          <DatePicker class="datepicker" placeholder="Data urodzenia *"
            :error="{ active: v$.dateOfBirth.$error && v$.dateOfBirth.$dirty, message: dateOfBirthError }" />
        </div>
      </div>
      <div class="input-row">
        <FieldInput v-model="password" label="Hasło *" type="password"
          :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }"
          displayBlankSpaceBelow=true />
        <FieldInput v-model="passwordRepeat" label="Powtórz hasło *" type="password"
          :error="{ active: v$.passwordRepeat.$error && v$.passwordRepeat.$dirty, message: passwordRepeatError }"
          displayBlankSpaceBelow=true />
      </div>
    </div>
    <div id="register-user-location">
      <div class="input-row">
        <FieldInput v-model="country" label="Kraj *"
          :error="{ active: v$.country.$error && v$.country.$dirty, message: countryError }"
          displayBlankSpaceBelow=true />
        <FieldInput v-model="region" label="Województwo *"
          :error="{ active: v$.region.$error && v$.region.$dirty, message: regionError }" displayBlankSpaceBelow=true />
      </div>
      <div class="input-row">
        <FieldInput v-model="city" label="Miasto *"
          :error="{ active: v$.city.$error && v$.city.$dirty, message: cityError }" displayBlankSpaceBelow=true />
        <FieldInput v-model="street" label="Ulica *"
          :error="{ active: v$.street.$error && v$.street.$dirty, message: streetError }" displayBlankSpaceBelow=true />
      </div>
      <div class="input-row">
        <FieldInput v-model="postCode" label="Kod pocztowy *"
          :error="{ active: v$.postCode.$error && v$.postCode.$dirty, message: postCodeError }"
          displayBlankSpaceBelow=true />
        <div class="input-row">
          <FieldInput v-model="houseNumber" label="Nr. domu" displayBlankSpaceBelow=true />
          <FieldInput v-model="flatNumber" label="Mieszkanie" displayBlankSpaceBelow=true />
        </div>
      </div>
    </div>
    <p :style="{ color: COLORS.PRIMARY }">'*' - pole wymagane </p>
    <ButtonPrimary buttonText="Zarejestruj" style="margin-left:0;" />
  </form>
</template>

<script>
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import { FONT_SIZES, COLORS, FONTS } from "../../../public/Consts";
import { required, minLength, maxLength, email, sameAs } from '@vuelidate/validators';
import { useVuelidate } from '@vuelidate/core';
import axios from 'axios';

import DatePicker from "@/components/atoms/DatePicker.vue";
import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

export default {
  name: "FormRegister",
  components: { FieldInput, DatePicker, ButtonPrimary },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      FONTS: FONTS,

      name: null,
      surname: null,
      password: null,
      passwordRepeat: null,
      email: null,
      dateOfBirth: null,

      country: null,
      region: null,
      city: null,
      street: null,
      postCode: null,
      houseNumber: null,
      flatNumber: null,

      emailError: "",
      nameError: "",
      surnameError: "",
      dateOfBirthError: "",
      passwordError: "",
      passwordRepeatError: "",

      countryError: "",
      regionError: "",
      cityError: "",
      streetError: "",
      postCodeError: "",

    };
  },
  validations() {
    return {
      email: { required, email },
      name: { required, minLength: minLength(3), maxLength: maxLength(20) },
      surname: { required, minLength: minLength(3), maxLength: maxLength(20) },
      dateOfBirth: { required },
      password: { required, minLength: minLength(8), maxLength: maxLength(20) },
      passwordRepeat: { required, sameAsPassword: sameAs(this.password) },
      country: { required },
      region: { required },
      street: { required },
      postCode: { required },
      city: { required, minLength: minLength(3), maxLength: maxLength(20) },
    }
  },
  setup() {
    const v$ = useVuelidate();
    return { v$ };
  },
  methods: {
    prepareDataToSend() {
      let formData = {
        name: this.name,
        surname: this.surname,
        email: this.email,
        password: this.password,
        dateOfBirth: this.dateOfBirth,

        country: this.country,
        region: this.region,
        city: this.city,
        street: this.street,
        houseNumber: this.houseNumber,
        flatNumber: this.flatNumber,
        postCode: this.postCode,

      };
      console.log(formData);
      return formData
    },
    async submitForm() {
      console.log("submitForm");
      this.v$.$validate();
      if (this.v$.$error) {
        return null;
      }
      let data = this.prepareDataToSend();
      await axios.post(GATEWAY_ADDRESS + '/user/add', data)
        .then(() => {
          this.emitter.emit('success', { message: 'Twoje konto zostało utworzone!' });
          this.$router.push('/login');
        })
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
  },

  watch: {
    'v$.email.$model'() {
      if (!this.v$.email.email.$model) {
        this.emailError = "Niepoprawny email";
      }
      else {
        this.emailError = '';
      }
    },
    'v$.name.$model'() {
      if (!this.v$.name.required.$model || this.v$.name.minLength.$model || this.v$.name.maxLength.$model) {
        this.nameError = "Imię musi mieć od 3 do 20 znaków";
      }
      else {
        this.nameError = '';
      }
    },
    'v$.surname.$model'() {
      if (!this.v$.surname.required.$model || this.v$.surname.minLength.$model || this.v$.surname.maxLength.$model) {
        this.surnameError = "Nazwisko musi mieć od 3 do 20 znaków";
      }
      else {
        this.surnameError = '';
      }
    },
    'v$.dateOfBirth.$model'() {
      if (!this.v$.dateOfBirth.required.$model) {
        this.dateOfBirthError = "Data urodzenia jest wymagana";
      }
      else {
        this.dateOfBirthError = '';
      }
    },
    'v$.password.$model'() {
      if (!this.v$.password.required.$model || this.v$.password.minLength.$model || this.v$.password.maxLength.$model) {
        this.passwordError = "Hasło musi mieć od 8 do 20 znaków";
      }
      else {
        this.passwordError = '';
      }
    },
    'v$.passwordRepeat.$model'() {
      if (!this.v$.passwordRepeat.required.$model || this.v$.passwordRepeat.sameAsPassword.$model) {
        this.passwordRepeatError = "Hasła muszą się zgadzać";
      }
      else {
        this.passwordRepeatError = '';
      }
    },
    'v$.city.$model'() {
      if (!this.v$.city.required.$model || this.v$.city.minLength.$model || this.v$.city.maxLength.$model) {
        this.cityError = "Miasto musi mieć od 3 do 20 znaków";
      }
      else {
        this.cityError = '';
      }
    },
    'v$.country.$model'() {
      if (!this.v$.country.required.$model) {
        this.countryError = "Kraj jest wymagany";
      }
      else {
        this.countryError = '';
      }
    },
    'v$.region.$model'() {
      if (!this.v$.region.required.$model) {
        this.regionError = "Województwo jest wymagane";
      }
      else {
        this.regionError = '';
      }
    },
    'v$.street.$model'() {
      if (!this.v$.street.required.$model) {
        this.streetError = "Ulica jest wymagana";
      }
      else {
        this.streetError = '';
      }
    },
    'v$.postCode.$model'() {
      if (!this.v$.postCode.required.$model) {
        this.postCodeError = "Kod pocztowy jest wymagany";
      }
      else {
        this.postCodeError = '';
      }
    },
  },
  mounted() {
    this.emitter.on('date', (data) => {
      console.log(data);
      this.dateOfBirth = data.date;
    });
    console.log("birth date: ", this.dateOfBirth);
  }
}

</script>

<style>
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
  width: 50%;
}

.input-row>* {
  width: 48%;
}

.dp__theme_light {
  width: 40%;
  height: 150%;
  --dp-text-color: gray;
  --dp-font-size: v-bind("FONT_SIZES.PRIMARY");
  --dp-background-color: v-bind("COLORS.OFFER_BACKGROUND");
  --dp-font-family: v-bind("FONTS.PRIMARY");
  --dp-common-padding: 0.75rem;
}</style>
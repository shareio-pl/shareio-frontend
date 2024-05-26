<template>
  <form id="register-user" @submit.prevent="submitForm">
    <div id="register-user-data">
      <div class="input-row">
        <FieldInput v-model="email" placeholder="Email" label="Email"
          :error="{ active: v$.email.$error, message: emailError }" />
        <FieldInput v-model="name" placeholder="Imię" label="Imię"
          :error="{ active: v$.name.$error, message: nameError }" />
      </div>
      <div class="input-row">
        <FieldInput v-model="surname" placeholder="Nazwisko" label="Nazwisko"
          :error="{ active: v$.surname.$error && v$.surname.$dirty, message: surnameError }" />
        <DatePicker class="datepicker" v-model="date" placeholder="Data urodzenia" />
      </div>
      <div class="input-row">
        <FieldInput v-model="password" placeholder="Hasło" label="Hasło" type="password"
          :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }" />
        <FieldInput v-model="passwordRepeat" placeholder="Powtórz hasło" label="Powtórz hasło" type="password"
          :error="{ active: v$.passwordRepeat.$error && v$.passwordRepeat.$dirty, message: passwordRepeatError }" />
      </div>
    </div>
    <div id="register-user-location">
      <div class="input-row">
        <FieldInput v-model="country" placeholder="Miasto" label="Miasto" />
        <FieldInput v-model="region" placeholder="Województwo" label="Województwo" />
      </div>
      <div class="input-row">
        <FieldInput v-model="city" placeholder="Miasto" label="Miasto"
          :error="{ active: v$.city.$error && v$.city.$dirty, message: cityError }" />
        <FieldInput v-model="street" placeholder="Ulica" label="Ulica" />
      </div>
      <div class="input-row">
        <FieldInput v-model="postCode" placeholder="Kod pocztowy" label="Kod pocztowy" />
        <div class="input-row">
          <FieldInput v-model="houseNumber" placeholder="Numer domu" label="Numer domu" />
          <FieldInput v-model="flatNumber" placeholder="Numer mieszkania" label="Numer mieszkania" />
        </div>
      </div>
    </div>
    <p> '*' - pole wymagane </p>
    <button type="submit" onclick="this.submitForm">Zarejestruj</button>
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

export default {
  name: "FormRegister",
  components: { FieldInput, DatePicker },
  data() {
    return {
      FONT_SIZES: FONT_SIZES,
      COLORS: COLORS,
      FONTS: FONTS,
      email: "",
      name: "",
      surname: "",
      dateOfBirth: "",
      password: "",
      passwordRepeat: "",
      country: "",
      region: "",
      city: "",
      street: "",
      postCode: "",
      houseNumber: "",
      flatNumber: "",
      emailError: "",
      nameError: "",
      surnameError: "",
      dateOfBirthError: "",
      passwordError: "",
      passwordRepeatError: "",
      cityError: "",
    };
  },
  validations() {
    return {
      email: { required, email },
      name: { required, minLength: minLength(3), maxLength: maxLength(20) },
      surname: { required, minLength: minLength(3), maxLength: maxLength(20) },
      dateOfBirth: { required },
      password: { required, minLength: minLength(8), maxLength: maxLength(20) },
      passwordRepeat: { required, sameAsPassword: sameAs('password') },
      city: { required, minLength: minLength(3), maxLength: maxLength(20) },
    }
  },
  // you might want to move it into main.js like router, I think
  // just haven't checked it yet
  setup() {
    const v$ = useVuelidate();
    return { v$ };
  },
  methods: {
    prepareDataToSend() {
      let formData = {
        email: this.email,
        name: this.name,
        surname: this.surname,
        dateOfBirth: "2000-12-31T12:00:00", // temporary, since there's no date picker.
        password: this.password, // TODO: yay, we're sending password as a plaintext!...
        // fix this.
        addressSaveDto: {
          country: this.country,
          region: this.region,
          city: this.city,
          street: this.street,
          postCode: this.postCode,
          houseNumber: this.houseNumber,
          flatNumber: this.flatNumber,
        }
      };
      console.log(formData);
      return formData
    },
    async submitForm() {
      this.v$.$validate();
      if (this.v$.$error) {
        return null;
      }
      let data = this.prepareDataToSend();
      try {
        const response = await axios.post(GATEWAY_ADDRESS + '/user/add', data);
        console.log('User added: ', response.data);
      } catch (error) {
        console.error('ERROR: ', error);
        this.emitter.emit('axiosError', { error: error.response.status });
      }
    },
  },

  watch: {
    'v$.email.$model'(newVal) {
      if (newVal) {
        if (!this.v$.email.email.$model) {
          this.emailError = "Niepoprawny email";
        }
      } else {
        this.emailError = '';
      }
    },
    'v$.name.$model'(newVal) {
      if (newVal) {
        if (!this.v$.name.required.$model || this.v$.name.minLength.$model || this.v$.name.maxLength.$model) {
          this.nameError = "Imię musi mieć od 3 do 20 znaków";
        }
      } else {
        this.nameError = '';
      }
    },
    'v$.surname.$model'(newVal) {
      if (newVal) {
        if (!this.v$.surname.required.$model || this.v$.surname.minLength.$model || this.v$.surname.maxLength.$model) {
          this.surnameError = "Nazwisko musi mieć od 3 do 20 znaków";
        }
      } else {
        this.surnameError = '';
      }
    },
    'v$.dateOfBirth.$model'(newVal) {
      if (newVal) {
        if (!this.v$.dateOfBirth.required.$model) {
          this.dateOfBirthError = "Data urodzenia jest wymagana";
        }
      } else {
        this.dateOfBirthError = '';
      }
    },
    'v$.password.$model'(newVal) {
      if (newVal) {
        if (!this.v$.password.required.$model || this.v$.password.minLength.$model || this.v$.password.maxLength.$model) {
          this.passwordError = "Hasło musi mieć od 8 do 20 znaków";
        }
      } else {
        this.passwordError = '';
      }
    },
    'v$.passwordRepeat.$model'(newVal) {
      if (newVal) {
        if (!this.v$.passwordRepeat.required.$model || this.v$.passwordRepeat.sameAsPassword.$model) {
          this.passwordRepeatError = "Hasła muszą się zgadzać";
        }
      } else {
        this.passwordRepeatError = '';
      }
    },
    'v$.city.$model'(newVal) {
      if (newVal) {
        if (!this.v$.city.required.$model || this.v$.city.minLength.$model || this.v$.city.maxLength.$model) {
          this.cityError = "Miasto musi mieć od 3 do 20 znaków";
        }
      } else {
        this.cityError = '';
      }
    },
  }
}
</script>

<style>
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
  justify-content: center;
  align-items: center;
  width: 40%;
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
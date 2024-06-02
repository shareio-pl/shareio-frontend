<template>
  <form @submit.prevent="submitForm">
    <div class="input-row">
      <FieldInput v-model="email" placeholder="Email" label="Email" type="email"
        :error="{ active: v$.email.$error && v$.email.$dirty, message: emailError }" />
    </div>
    <div class="input-row">
      <FieldInput v-model="password" placeholder="Hasło" label="Hasło" type="password"
        :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }" />
    </div>
    <div class="login-submit">
      <ButtonPrimary class="login-submit" type="submit" :buttonText="text" />
    </div>
  </form>
</template>

<script>
import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

import { GATEWAY_ADDRESS } from "../../../public/Consts";

import { required, minLength, maxLength } from '@vuelidate/validators';
import { useVuelidate } from "@vuelidate/core";

export default {
  name: "FormLogin",
  components: { FieldInput, ButtonPrimary },
  data() {
    return {
      email: "",
      password: "",
      emailError: "",
      passwordError: "",
      text: "Zaloguj się"
    }
  },
  setup() {
    const v$ = useVuelidate();
    return { v$ };
  },
  validations() {
    return {
      email: { required, email: { required: true, maxLength: maxLength(100) } },
      password: { required, minLength: minLength(6), maxLength: maxLength(20) },
    }
  },
  methods: {
    async submitForm() {
      this.v$.$validate();
      if (this.v$.$error) {
        return null;
      }
      axios.post(GATEWAY_ADDRESS + '/login', { email: this.email, password: this.password })
        .then(response => {
          console.log(response);
          localStorage.setItem('token', response.data);
          this.$router.push('/').then(() => {
            window.location.reload();
          })
        .catch(error => {
            console.error(error);
          })
        })
        .catch(error => {
          console.error('ERROR: ', error);
          this.emailError = 'Invalid email or password';
          this.passwordError = 'Invalid email or password';
        });
    }
  },
  watch: {
    'v$.email.$model'() {
      if (!this.v$.email.required.$model || this.v$.email.email.$model) {
        this.emailError = "Nieprawidłowy adres email";
      } else {
        this.emailError = '';
      }
    },
    'v$.password.$model'() {
      if (!this.v$.password.required.$model || this.v$.password.minLength.$model || this.v$.password.maxLength.$model) {
        this.passwordError = "Hasło musi mieć od 8 do 20 znaków";
      } else {
        this.passwordError = '';
      }
    }
  }
}
</script>

<style>
.input-row input[type="email"],
.input-row input[type="password"] {
  width: 25%;
  padding: 0.5%;
  margin-bottom: 1%;
}

.login-submit {
  margin-top: 1%;
  margin-right: 1%;
}
</style>
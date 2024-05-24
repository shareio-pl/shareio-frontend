<template>
  <form @submit.prevent="submitForm">
    <div class="input-row">
      <FieldInput v-model="email" placeholder="Email" label="Email" type="email" />
      <FieldInput v-model="password" placeholder="Hasło" label="Hasło" type="password" />
    </div>
    <div class="login-submit">
      <ButtonPrimary class="login-submit" type="submit" :buttonText="text"/>
    </div>
  </form>
</template>

<script>
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import {useVuelidate} from "@vuelidate/core";


export default {
  name: "FormLogin",
  components: { FieldInput, ButtonPrimary },
  data() {
    return {
      email: "",
      password: "",
      error: "",
      text: "Zaloguj się"
    }
  },
  // here also might want to move it
  setup() {
    const v$ = useVuelidate();
    return { v$ };
  },
  methods: {
    async submitForm() {
      this.v$.$validate();
      if (this.v$.$error) {
        return null;
      }
      try {
        const response = await axios.post(GATEWAY_ADDRESS + '/login', { email: this.email, password: this.password });
        console.log('User logged in: ', response.data);
        // TODO where to redirect
      } catch (error) {
        console.error('ERROR: ', error);
        this.error = 'Invalid email or password';
      }
    }
  },
  watch: {
    'v$.email.$model'(newVal) {
      if (!newVal) {
        this.error = 'Email is required';
      } else {
        this.error = '';
      }
    },
    'v$.password.$model'(newVal) {
      if (!newVal) {
        this.error = 'Password is required';
      } else {
        this.error = '';
      }
    }
  }
}
</script>

<style>
.input-row input[type="email"], .input-row input[type="password"] {
  width: 25%;
  padding: 0.5%;
  margin: 1%;
}

.login-submit {
  margin-top: 1%;
  margin-right: 1%;
}
</style>
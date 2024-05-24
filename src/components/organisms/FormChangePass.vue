<template>
  <form @submit.prevent="submitForm">
    <div class="input-row">
      <FieldInput v-model="oldPassword" placeholder="Stare hasło" label="Stare hasło" type="password"
                  :error="{ active: v$.oldPassword.$error && v$.oldPassword.$dirty, message: oldPasswordError }" />
      <FieldInput v-model="password" placeholder="Hasło" label="Nowe hasło" type="password"
                  :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }" />
      <FieldInput v-model="passwordRepeat" placeholder="Powtórz hasło" label="Powtórz nowe hasło" type="password"
                  :error="{ active: v$.passwordRepeat.$error && v$.passwordRepeat.$dirty, message: passwordRepeatError }" />
    </div>
    <div class="password-submit">
      <ButtonPrimary class="password-submit" type="submit" :buttonText="text"/>
    </div>
  </form>
</template>

<script>
import { GATEWAY_ADDRESS } from "../../../public/Consts";
import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import { required, minLength, maxLength, sameAs } from '@vuelidate/validators';
import {useVuelidate} from "@vuelidate/core";


export default {
  name: "FormChangePass",
  components: { FieldInput, ButtonPrimary },
  data() {
    return {
      oldPassword: "",
      password: "",
      passwordRepeat: "",
      oldPasswordError: "",
      passwordError: "",
      passwordRepeatError: "",
      text: "Zmień hasło"
    }
  },
  validations() {
    return {
      oldPassword: { required, minLength: minLength(8), maxLength: maxLength(20) },
      password: { required, minLength: minLength(8), maxLength: maxLength(20) },
      passwordRepeat: { required, sameAsPassword: sameAs(this.password) },
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
      // TODO not sure if it's correct endpoint
      try {
        const response = await axios.post(GATEWAY_ADDRESS + '/user/modify', { oldPassword: this.oldPassword, newPassword: this.password });
        console.log('User password modified: ', response.data);
        // TODO where to redirect
      } catch (error) {
        console.error('ERROR: ', error);
        if (error.response.data.error === 'Invalid old password') {
          this.oldPasswordError = 'Invalid old password';
        } else {
          this.emitter.emit('axiosError', { error: error.response.status });
        }
      }
    }
  },
  watch: {
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
  }
}
</script>

<style>
.input-row input[type="password"] {
  width: 25%;
  padding: 0.5%;
  margin: 1%;
}

.password-submit {
  margin-top: 1%;
  margin-right: 1%;
}
</style>
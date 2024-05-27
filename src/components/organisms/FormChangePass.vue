<template>
  <form @submit.prevent="submitForm">
    <div class="input-row">
      <FieldInput v-model="oldPassword" placeholder="Stare hasło" label="Stare hasło" type="password"
                  :error="{ active: v$.oldPassword.$error && v$.oldPassword.$dirty, message: oldPasswordError }"/>
    </div>
    <div class="input-row">
      <FieldInput v-model="password" placeholder="Hasło" label="Nowe hasło" type="password"
                  :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }"/>
    </div>
    <div class="input-row">
      <FieldInput v-model="passwordRepeat" placeholder="Powtórz hasło" label="Powtórz nowe hasło" type="password"
                  :error="{ active: v$.passwordRepeat.$error && v$.passwordRepeat.$dirty, message: passwordRepeatError }"/>
    </div>
    <div class="password-submit">
      <ButtonPrimary class="password-submit" type="submit" :buttonText="text"/>
    </div>
  </form>
</template>

<script>
import {GATEWAY_ADDRESS} from "../../../public/Consts";
import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import {required, minLength, maxLength, sameAs} from '@vuelidate/validators';
import {useVuelidate} from "@vuelidate/core";


export default {
  name: "FormChangePass",
  components: {FieldInput, ButtonPrimary},
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
      oldPassword: {required, minLength: minLength(8), maxLength: maxLength(20)},
      password: {required, minLength: minLength(8), maxLength: maxLength(20)},
      passwordRepeat: {required, sameAsPassword: sameAs(this.password)},
    }
  },
  setup() {
    const v$ = useVuelidate();
    return {v$};
  },
  methods: {
    async submitForm() {
      this.v$.$validate();
      if (this.v$.$error) {
        return null;
      }
      // TODO confirm endpoint correctness
      await axios.post(GATEWAY_ADDRESS + '/user/modify', {oldPassword: this.oldPassword, newPassword: this.password})
          .catch(get => {
            console.error('ERROR: ', get);
            if (get.response.data.error === 'Invalid old password') {
              this.oldPasswordError = 'Invalid old password';
            } else {
              this.emitter.emit('axiosError', {error: get.response.status});
            }
          })
          .then(response => {
            console.log('User password modified: ', response.data);
            this.$router.push('/');
          });
    }
  },
  watch: {
    'v$.oldPassword.$model'() {
      if (!this.v$.oldPassword.required.$model || this.v$.oldPassword.minLength.$model || this.v$.oldPassword.maxLength.$model) {
        this.oldPasswordError = "Hasło musi mieć od 8 do 20 znaków";
      } else {
        this.oldPasswordError = '';
      }
    },
    'v$.password.$model'() {
      if (!this.v$.password.required.$model || this.v$.password.minLength.$model || this.v$.password.maxLength.$model) {
        this.passwordError = "Hasło musi mieć od 8 do 20 znaków";
      } else {
        this.passwordError = '';
      }
    },
    'v$.passwordRepeat.$model'() {
      if (!this.v$.passwordRepeat.required.$model || this.v$.passwordRepeat.sameAsPassword.$model) {
        this.passwordRepeatError = "Hasła muszą się zgadzać";
      } else {
        this.passwordRepeatError = '';
      }
    },
  },
}
</script>

<style>
.input-row input[type="password"] {
  width: 25%;
  padding: 0.5%;
  margin-bottom: 1%;
}
.input-row .error {
  color: #ff0000 !important;
}

.password-submit {
  margin-top: 1%;
  margin-right: 1%;
}
</style>
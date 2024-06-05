<template>
  <form id="login-user" @submit.prevent="submitForm">
    <div id="login-user-data">
      <div class="input-row">
        <FieldInput v-model="email" placeholder="Email" label="Email" type="email"
          :error="{ active: v$.email.$error && v$.email.$dirty, message: emailError }" displayBlankSpaceBelow=true />
      </div>
      <div class="input-row">
        <FieldInput v-model="password" placeholder="Hasło" label="Hasło" type="password"
          :error="{ active: v$.password.$error && v$.password.$dirty, message: passwordError }"
          displayBlankSpaceBelow=true />
      </div>
    </div>
    <div v-if="isloading">
      <div class="loading-spinner">
        <FontAwesomeIcon :icon="iconLoading" spin style="font-size: 24px; color: #666;" />
      </div>
    </div>
    <div v-else>
      <ButtonPrimary type="submit" :buttonText="text" style="width: calc(30px + 14vw);  margin-left:0;" />
    </div>
    <span v-if="loginError">Nieprawidłowy email lub hasło!</span>
  </form>
</template>

<script>
import axios from 'axios';

import FieldInput from "@/components/atoms/FieldInput.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {faSpinner} from '@fortawesome/free-solid-svg-icons';

import {COLORS, GATEWAY_ADDRESS} from "../../../public/Consts";

import { required, minLength, maxLength } from '@vuelidate/validators';
import { useVuelidate } from "@vuelidate/core";

export default {
  name: "FormLogin",
  components: { FieldInput, ButtonPrimary, FontAwesomeIcon },
  data() {
    return {
      COLORS: COLORS,
      email: "",
      password: "",
      emailError: "",
      passwordError: "",
      text: "Zaloguj się",
      isloading: false,
      iconLoading: faSpinner,
      loginError: "",
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
      this.isloading = true;
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
          this.isloading = false;
          this.loginError = 'Invalid email or password';
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
        this.passwordError = "Hasło musi mieć od 6 do 20 znaków";
      } else {
        this.passwordError = '';
      }
    }
  }
}
</script>

<style scoped>
#login-user {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

#login-user-data {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.input-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

}

span {
  font-size: calc(7px + 1.1vw);
  color: v-bind('COLORS.TEXT_PRIMARY');
  margin-top: 1%;
}

@media only screen and (max-width: 800px) {
  .input-row {
    width: 100%;
  }
}

</style>
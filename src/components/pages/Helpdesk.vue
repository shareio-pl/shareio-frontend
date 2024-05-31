<template>
  <div id="Helpdesk">
    <Header/>
    <DefaultSuccess/>
    <h1>{{ title }}</h1>
    <div id="email">
      <input type="text" id="mailTopic" placeholder="Temat..." v-model="mailTopic">
      <textarea id="mailText" placeholder="Treść..." v-model="mailText"/>
      <ButtonPrimary button-text="Wyślij" id="button" @click="sendMail"/>
    </div>
    <p>FAQ</p>
    <div id="faq">
      <FAQDropdown :title="question1" :message="answer1" id="firstDropdown"/>
      <FAQDropdown :title="question2" :message="answer2" id="dropdown"/>
      <FAQDropdown :title="question3" :message="answer3" id="dropdown"/>
      <FAQDropdown :title="question4" :message="answer4" id="dropdown"/>
      <FAQDropdown :title="question5" :message="answer5" id="dropdown"/>
    </div>
  </div>
</template>

<script>
import {COLORS, FONT_SIZES, GATEWAY_ADDRESS} from "../../../public/Consts";
import FAQDropdown from "@/components/atoms/FAQDropdown.vue";
import Header from "@/components/organisms/Header.vue";
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import axios from "axios";
import DefaultSuccess from "@/components/pages/DefaultSuccess.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Helpdesk",
  components: {DefaultSuccess, ButtonPrimary, Header, FAQDropdown},
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      title: 'Opisz swój problem, a przyjdzie on na naszą skrzynkę mailową!',
      question1: '1. Ile czasu mam na odebranie przedmiotu?',
      answer1: 'Na odebranie przedmiotu użytkownik ma 24 godziny.',
      question2: '2. Czy muszę używać AI do generowania opisów?',
      answer2: 'Nie, nasz model językowy ma jedynie to ułatwiać, nic nie stoi na przeszkodzie aby samemu uzupełnić opis.',
      question3: '3. Czy mogę usunąć swoje konto?',
      answer3: 'Jak najbardziej, swoje konto możesz usunąć klikająć odpowiedni przycisk na stronie Moje konto.',
      question4: '4. Nie podałem dokładnego adresu przy zakładaniu konta, gdzie mogę to zmienić?',
      answer4: 'Dane odnośnie swojego adresu można zmienić na stronie Moje konto, w sekcji poświęconej adresowi.',
      question5: '5. Na podstawie czego wyliczana jest moja pozycja w rankingu?',
      answer5: 'Pozycja użytkownika w rankingu wyliczana jest na podstawie liczby jego wystawionych ofert oraz jego oceny.',
      mailTopic: '',
      mailText: '',
    }
  },
  methods: {
    sendMail() {
      console.log('Topic: ', this.mailTopic);
      console.log('Text: ', this.mailText);

      //TODO: change the authorization token to the one from local storage
      axios.post(GATEWAY_ADDRESS + '/email/send', {"messageTitle": this.mailTopic, "messageBody": this.mailText},
          {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjcyMDQ1OGI5LWM5N2QtNDU5Ny1hNzg5LWVlZTQ5YTRlN2U0ZCIsInJvbGUiOiJVU0VSIiwic3ViIjoiU0hBUkVJTyIsImlhdCI6MTcxNzA3MDY4MCwiZXhwIjoxNzE3MDcxNTgwfQ.QB1GYjL2uvlbpIwW6BVvsN30WtLOBQ3CnDFJtUilWZI'
            }
          }).then((response) => {
        console.log('Sent mail: ', response.data);
        this.emitter.emit('success', {message: 'Twoja wiadomość została wysłana!'});
      }).catch((error) => {
        console.error('Error sending mail: ', error);
        this.emitter.emit('axiosError', {error: error.response.status});
      })
    },
  },
}
</script>

<style scoped>
#Helpdesk {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
}

h1 {
  font-size: v-bind('FONT_SIZES.TITLE');
  color: v-bind('COLORS.SECONDARY');
  margin-top: 1%;
}

#email {
  display: flex;
  flex-direction: column;
  width: 80%;
  margin-left: 10%;
  margin-right: 10%;
  margin-top: 1%;
}

input, textarea{
  background-color: v-bind('COLORS.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  border-color: v-bind('COLORS.SECONDARY');
  padding-left: 5px;
}

input::placeholder {
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: v-bind('FONT_SIZES.IMPORTANT');
}

textarea::placeholder{
  color: v-bind('COLORS.TEXT_SECONDARY');
  font-size: v-bind('FONT_SIZES.IMPORTANT');
}

#mailTopic {
  border-radius: 10px 10px 0 0;
  height: 50px;
}

#mailText {
  border-radius: 0 0 10px 10px;
  height: 125px;
  padding-bottom: 90px;
}

#button {
  align-self: flex-end;
}

p {
  color: v-bind('COLORS.SECONDARY');
  font-size: v-bind('FONT_SIZES.TITLE');
  text-align: start;
  margin-left: 10%;
  margin-top: 1%;
}

#faq {
  width: 80%;
  margin-left: 10%;
  margin-right: 10%;
}

#firstDropdown {
  margin-top: -0.5%;
}

#dropdown {
  margin-top: 0.5%;
}
</style>

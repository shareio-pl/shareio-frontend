<template>
  <div class="add-offer-card">
    <div class="add-offer-left">
      <div id="add-offer-image">
        <ImageAdder />
      </div>
      <p class="add-offer-left-giver"> Oddająca osoba: </p>
      <div class="add-offer-left-data">
        <UserData class="add-offer-user" :userFirstName="userFirstName" :userSurname="userSurname"
          :userImage="userImage" />
        <Stars class="add-offer-stars" :filledStars="amountOfStars" :ratingsAmount="amountOfRatings" />
      </div>
    </div>
    <div class="add-offer-content">
      <FormOffer v-if="addressDataLoaded" ref="formOffer" :city="city" :street="street" :houseNumber="houseNumber"
        :flatNumber="flatNumber" :region="region" :country="country" :post-code="postCode" :categories="categories"
        :states="states" />
    </div>
    <div class="add-offer-right">
      <div class="add-offer-right-map">
        <MapPreview zoom=16 :address="fullAddress" />
      </div>
      <ButtonPrimary class="add-offer-right-button" buttonText="Zarezerwuj" @click="submitOffer" />
    </div>
  </div>
  <div>
    <ButtonPrimary class="buttonAI" buttonText="Użyj AI do wygenerowania opisu" />
  </div>
</template>


<script>
import ButtonPrimary from "@/components/atoms/ButtonPrimary.vue";
import MapPreview from "@/components/atoms/MapPreview.vue";
import ImageAdder from "@/components/atoms/ImageSelector.vue";
import FormOffer from "@/components/organisms/FormOffer.vue";
import UserData from "@/components/atoms/UserData.vue";
import Stars from "@/components/atoms/Stars.vue";

import axios from 'axios'
import "leaflet/dist/leaflet.css"
import { COLORS } from "../../../public/Consts";
import { FONT_SIZES } from "../../../public/Consts";
import { DEFAULT_OFFER_MAP_IMAGE } from "../../../public/Consts";
import { GATEWAY_ADDRESS } from "../../../public/Consts";

export default {
  name: "AddOffer",
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      zoom: 16,
      center: [0, 0],
      mapDataLoaded: false,
      offerImage: '',
      offerMapImage: DEFAULT_OFFER_MAP_IMAGE,
      offerData: '',
      GATEWAY_ADDRESS: GATEWAY_ADDRESS,
      userFirstName: '',
      userSurname: '',
      userImage: '',

      city: '',
      street: '',
      houseNumber: '',
      flatNumber: '',
      region: '',
      country: '',
      postCode: '',
      addressDataLoaded: false,

      categories: [],
      states: []
    }
  },
  computed: {
    fullAddress() {
      return `${this.city}, ${this.street}, ${this.houseNumber}`;
    },
  },
  components: {
    ButtonPrimary,
    MapPreview,
    ImageAdder,
    FormOffer,
    UserData,
    Stars,
  },
  props: {
    ownerId: {
      type: String,
      required: true,
    },
  },
  methods: {
    getCategories() {
      axios.get(GATEWAY_ADDRESS + `/offer/getCategories`)
        .then(response => {
          console.log('Categories response: ', response.data);
          this.categories = response.data.categories.map(category => ({
            displayName: category.displayName,
            category: category.category,
          }));
          console.log('Categories: ', this.categories);
        })
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    getConditions() {
      axios.get(GATEWAY_ADDRESS + `/offer/getConditions`).then((response) => {
        console.log('Received conditions: ', response.data);
        this.states = response.data.conditions.map(condition => ({ displayName: condition.displayName, category: condition.condition }));
      });
    },
    async submitOffer() {
      let formData = this.$refs.formOffer.getFormData();

      if (!formData) {
        return;
      }

      formData.state = "ALMOST_NEW";
      formData.image = this.offerImage;

      let dataToSend = {
        ownerId: this.ownerId,
        title: formData.offerTitle,
        condition: formData.offerState,
        description: formData.offerDescription,
        category: formData.offerCategory,
        creationDate: new Date().toISOString(),
        addressSaveDto: {
          city: "Łódź",
          street: "Wólczańska",
          houseNumber: "215",
          flatNumber: "1",
          region: "Łódzkie",
          country: "Polska",
          postCode: "91-001",
        }
      };

      console.log('Data to send: ', dataToSend);

      let jsonDataArray = dataToSend;
      let jsonBlob = new Blob([JSON.stringify(jsonDataArray)], { type: 'application/json' });

      let form = new FormData();
      form.append("json", jsonBlob);
      form.append("file", ('image', this.offerImage));
      console.log("Test", this.offerImage);
      console.log(form);

      try {
        let response = await axios.post(GATEWAY_ADDRESS + '/offer/add', form, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('Offer added: ', response.data);
      } catch (error) {
        console.error('ERROR: ', error);
      }
    }

  },
  mounted() {
    this.emitter.on('image-uploaded', (data) => {
      console.log('Image uploaded:', data);
      this.offerImage = data;
    });
    console.log('Owner id: ', this.ownerId);
    axios.get(GATEWAY_ADDRESS + '/user/get/' + this.ownerId).then((response) => {
      console.log('User data: ', response.data);
      this.userFirstName = response.data.name;
      this.userSurname = response.data.surname;
      this.userImage = response.data.image;

      axios.get(GATEWAY_ADDRESS + '/address/get/' + response.data.address.id).then((response) => {
        console.log('Address data: ', response.data);
        this.city = response.data.city;
        this.street = response.data.street;
        this.houseNumber = response.data.houseNumber;
        this.flatNumber = response.data.flatNumber;
        this.region = response.data.region;
        this.country = response.data.country;
        this.postCode = response.data.postCode;
        this.addressDataLoaded = true;
        console.log("Address: ", this.city, this.street, this.houseNumber, this.flatNumber, this.region, this.country);
      });
    });
    this.getCategories();
    this.getConditions();
  },
}
</script>
<style scoped>
.add-offer-card {
  display: flex;
  width: 90%;
  height: 55%;
  margin: 0 auto;
  background-color: v-bind('COLORS.OFFER_FOREGROUND');
  border-radius: 25px 25px 25px 25px;
}

.add-offer-left {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  flex: 0 0 30%;
  box-sizing: border-box;
}

#add-offer-image {
  border-top-left-radius: 20%;
  border-bottom-right-radius: 20%;
  aspect-ratio: 1/1;
  overflow: hidden;
}

#add-offer-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.add-offer-left-data {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3%;
  margin-right: 8%;
}

.add-offer-left-giver {
  font-size: v-bind('FONT_SIZES.IMPORTANT');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.add-offer-stars {
  margin-left: 4%;
}

.add-offer-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-left: 2%;
  padding-right: 2%;
  flex: 0 0 45%;
}

.add-offer-content-title {
  padding-top: 3%;
  text-align: start;
  font-size: v-bind('FONT_SIZES.TITLE');
  color: v-bind('COLORS.TEXT_SECONDARY');
}

.add-offer-content-metadata {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_SECONDARY');
  width: 100%;
  margin-top: 7%;
}

textarea {
  width: 100%;
  height: 250%;
  resize: none;
  color: v-bind('COLORS.TEXT_SECONDARY');
  background-color: v-bind('COLORS.PRIMARY');
}

.add-offer-content-metadata p {
  margin-bottom: 0.1em;
  text-align: left;
}

.add-offer-content-metadata-desc {
  margin-top: 10%;
  font-size: v-bind('FONT_SIZES.PRIMARY');
  width: 100%;
  height: 100%;
}

.add-offer-right {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 0 0 25%;
  box-sizing: border-box;
  justify-content: space-between;
}

.add-offer-right-map {
  width: 100%;
  height: 50%;
  object-fit: cover;
  margin-left: auto;
  border-top-right-radius: 25px;
  border-bottom-left-radius: 25px;
  overflow: hidden;
}

.add-offer-right-button {
  width: 75%;
  height: 15%;
  margin-bottom: 15%;
}

.buttonAI {
  background-color: green;
  width: 90%;
  height: 4em;
  margin-left: 0;
}
</style>
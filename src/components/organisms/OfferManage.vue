<template>
  <transition name="fade">
    <div class="offer-manage" v-if="!isDeleted">
      <div class="offer-manage-row">
        <span class="offer-manage-review">
          <OfferPreview :id="id" style="width: 100%;" />
        </span>
        <span class="offer-manage-options">
          <ButtonPrimary :button-text="editText" style="width: 60%;" />
          <ButtonPrimary :button-text="closeText" @click="cancelOffer" style="width: 60%;" />
        </span>
      </div>
    </div>
  </transition>
</template>

<script>
import { FONTS, COLORS, FONT_SIZES } from "../../../public/Consts";
import { GATEWAY_ADDRESS } from "../../../public/Consts";

import axios from "axios";
import { jwtDecode } from "jwt-decode";

import OfferPreview from "@/components/organisms/OfferPreview.vue";
import ButtonPrimary from "../atoms/ButtonPrimary.vue";

export default {
  name: "OfferManage",
  components: {
    OfferPreview,
    ButtonPrimary
  },
  data() {
    return {
      FONTS: FONTS,
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      editText: 'Edytuj',
      closeText: 'Zakończ',
      isDeleted: false,
    };
  },
  props: {
    id: {
      type: Number,
      required: true
    },
  },
  methods: {
    cancelOffer() {
      this.closeText = 'Trwa zamykanie...';
      console.log(jwtDecode(localStorage.getItem("token")).id);
      axios.post(GATEWAY_ADDRESS + "/offer/cancel", {
        offerId: this.id,
        userId: jwtDecode(localStorage.getItem("token")).id
      }, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem("token")
        }
      })
        .then(response => {
          this.isDeleted = true;
          this.closeText = 'Zakończono!';
          console.log(response);
        })
        .catch(error => {
          console.error(error);
        });
    },
    editOffer() {
      this.$router.push('/editOffer/' + this.id);
    }
  },
}

</script>

<style scoped>
.offer-manage-row {
  display: flex;
  align-items: center;
}

.offer-manage-review {
  width: 80%
}

.offer-manage-options {
  width: 20%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.fade-leave-active {
  transition: opacity 2s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
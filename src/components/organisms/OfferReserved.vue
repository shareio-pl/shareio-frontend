<template>
  <transition name="fade">
    <div class="offer-reserve" v-if="!isDeleted">
      <div class="offer-reserve-row">
        <span class="offer-reserve-review">
          <OfferPreview :id="id" style="width: 100%;" />
        </span>
        <span class="offer-reserve-options">
          <ButtonPrimary :button-text="confirmText" @click="confirmPicked" style="width: 60%; min-width: 100px;" />
        </span>
      </div>
    </div>
  </transition>
</template>

<script>
import { FONTS, COLORS, FONT_SIZES, GATEWAY_ADDRESS } from "../../../public/Consts";

import OfferPreview from "@/components/organisms/OfferPreview.vue";
import ButtonPrimary from "../atoms/ButtonPrimary.vue";

import axios from "axios";
import { jwtDecode } from "jwt-decode";

export default {
  name: "OfferReserved",
  components: {
    OfferPreview,
    ButtonPrimary
  },
  data() {
    return {
      FONTS: FONTS,
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      confirmText: 'Potwierdź odbiór',
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
    confirmPicked() {
      this.confirmText = 'Trwa potwierdzanie...';
      console.log(jwtDecode(localStorage.getItem("token")).id);
      axios.post(GATEWAY_ADDRESS + "/offer/finish", {
        offerId: this.id,
        userId: jwtDecode(localStorage.getItem("token")).id
      }, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem("token")
        }
      })
        .then(() => {
          this.isDeleted = true;
          this.emitter.emit('offer-picked', { id: this.id });
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
}

</script>

<style scoped>
.offer-reserve-row {
  display: flex;
  align-items: center;
}

.offer-reserve-review {
  width: 80%;
}

.offer-reserve-options {
  width: 20%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.fade-leave-active {
  transition: opacity 1s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

/* Media queries for responsiveness */
@media (max-width: 1000px) {
  .offer-reserve-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .offer-reserve-review,
  .offer-reserve-options {
    width: 100%;
  }

  .offer-reserve-options {
    margin-top: 1em;
    flex-direction: row;
    justify-content: center;
  }

  .offer-reserve-options button {
    width: 100%;
  }
}
</style>

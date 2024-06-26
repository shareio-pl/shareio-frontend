<template>
  <div id="offers-page">
    <Header />
    <div id="offers-page-main">
      <div id="offers-page-filters">
        <Filters :categories="categories" />
      </div>
      <div id="offers-page-content">
        <div id="offers-page-content-browser">
          <Browser style="width:90%;" />
        </div>
        <div id="offers-page-content-offers" v-if="isMounted">
          <OfferPreview v-for="id in paginatedOffers" :key="id" :id=id style="width:90%;" />
        </div>
        <div id="offers-page-content-pagechange">
          <ChangePage :page="currentPage" :totalPages="totalPages" rightArrow="true" @changePage="changePage" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/organisms/Header.vue";
import Filters from "@/components/organisms/Filters.vue";
import OfferPreview from "@/components/organisms/OfferPreview.vue";
import ChangePage from "@/components/atoms/ChangePage.vue";
import { COLORS, FONT_SIZES, GATEWAY_ADDRESS } from "../../../public/Consts";
import axios from "axios";
import Browser from "../organisms/Browser.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Offers",
  components: { OfferPreview, Header, Filters, Browser, ChangePage },
  data() {
    return {
      COLORS: COLORS,
      FONT_SIZES: FONT_SIZES,
      NUMBER_OF_OFFERS_PER_PAGE: 5,
      offersIds: [],
      offers: [],
      currentPage: 1,
      time_from_youngest: true,
      time_chosen: null, //new Date('2026-01-01').toISOString().slice(0, 10),
      option_chosen: '',
      stars_chosen: '',
      distance_chosen: '',
      categories_chosen: '',
      sorting: 'CLOSEST',
      isMounted: false,
      categories: [],
      searched_item: ''
    }
  },
  methods: {
    setBrowserEmitter() {
      this.emitter.on('browser-change', (data) => {
        console.log('Received browser change: ', data);
        this.searched_item = data.input;
        this.sorting = data.sorting;
        this.getOffersDataByNameAndSorting();
      });
    },
    setPagesEmitter() {
      this.emitter.on('left-arrow-clicked', () => {
        if (this.currentPage > 1) {
          this.currentPage--;
          this.changePage(this.currentPage);
        }
      });
      this.emitter.on('right-arrow-clicked', () => {
        if (this.currentPage < this.totalPages) {
          console.log('Current page: ', this.currentPage);
          this.currentPage++;
          this.changePage(this.currentPage);
        }
      });
    },
    setFiltersEmitter() {
      this.emitter.on('filter', (data) => {
        this.option_chosen = data.option_chosen;
        this.stars_chosen = data.stars_chosen;
        this.distance_chosen = data.distance_chosen;
        this.categories_chosen = data.categories_chosen;

        console.log('Received filter change: ', data);

        let date = new Date();
        if (data.time_chosen) {
          date.setDate(date.getDate() + data.time_chosen);
          this.time_chosen = date.toISOString().slice(0, 10);
          console.log('Time chosen: ', this.time_chosen);
        }

        this.getOffersDataViaSearch();
      });
    },
    changePage(page) {
      this.currentPage = page;
    },
    getCategories() {
      axios.get(GATEWAY_ADDRESS + `/offer/getCategories`)
        .then(response => {
          this.categories = response.data.categories.map(category => ({
            displayName: category.displayName,
            categoryName: category.category,
            numberOfOffers: 0
          }));
        })
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    getOffersData() {
      let token = localStorage.getItem('token');
      if (token !== null) {
        axios.get(GATEWAY_ADDRESS + '/offer/getAllOffers', {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
          }
        })
          .then(
            response => {
              console.log('Offers: ', response.data);
              this.offersIds = response.data;
              let promises = this.offersIds.map(offerId =>
                axios.get(GATEWAY_ADDRESS + `/offer/get/${offerId}`)
                  .then(response => {
                    let category = this.categories.find(category => category.displayName === response.data.category);
                    if (category) {
                      category.numberOfOffers++;
                    }
                  })
              );
              return Promise.all(promises);
            }
          )
          .catch(error => {
            console.error('ERROR: ', error);
            this.emitter.emit('axiosError', { error: error.response.status });
          });
      }
      else {
        axios.get(GATEWAY_ADDRESS + '/offer/getAllOffers')
          .then(
            response => {
              console.log('Offers: ', response.data);
              this.offersIds = response.data;
              let promises = this.offersIds.map(offerId =>
                axios.get(GATEWAY_ADDRESS + `/offer/get/${offerId}`)
                  .then(response => {
                    let category = this.categories.find(category => category.displayName === response.data.category);
                    if (category) {
                      category.numberOfOffers++;
                    }
                  })
              );
              return Promise.all(promises);
            }
          )
          .catch(error => {
            console.error('ERROR: ', error);
            this.emitter.emit('axiosError', { error: error.response.status });
          });
      }

    },
    getOffersDataByNameAndSorting() {
      this.categories.forEach(category => category.numberOfOffers = 0);
      axios.get(GATEWAY_ADDRESS + '/offer/search', {
        params: {
          title: this.searched_item,
          sortType: this.sorting
        },
        headers: {
          'Authorization': `Bearer ` + localStorage.getItem('token'),
          'Content-Type': 'application/json'
        }
      })
        .then(
          response => {
            this.offersIds = response.data;
            let promises = this.offersIds.map(offerId =>
              axios.get(GATEWAY_ADDRESS + `/offer/get/${offerId}`)
                .then(response => {
                  let category = this.categories.find(category => category.displayName === response.data.category);
                  if (category) {
                    category.numberOfOffers++;
                  }
                })
            );
            return Promise.all(promises);
          }
        )
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    },
    getOffersDataViaSearch() {
      this.categories.forEach(category => category.numberOfOffers = 0);
      this.offersIds = [];

      let makeRequest = (category) => {

        return axios.get(GATEWAY_ADDRESS + '/offer/search', {
          params: {
            title: this.searched_item,
            category: category,
            condition: this.option_chosen,
            distance: this.distance_chosen,
            score: this.stars_chosen,
            creationDate: this.time_chosen,
            sortType: this.sorting
          },
          headers: {
            'Authorization': `Bearer ` + localStorage.getItem('token'),
            'Content-Type': 'application/json'
          }
        })
          .then(response => {
            console.log('Received offers by name: ', response.data);
            this.offersIds = this.offersIds.concat(response.data);
            let offerPromises = response.data.map(offerId =>
              axios.get(GATEWAY_ADDRESS + `/offer/get/${offerId}`)
                .then(response => {
                  let category = this.categories.find(category => category.displayName === response.data.category);
                  if (category) {
                    category.numberOfOffers++;
                  }
                })
            );
            return Promise.all(offerPromises);
          });
      };

      let promises;
      if (this.categories_chosen.length > 0) {
        promises = this.categories_chosen.map(makeRequest);
      } else {
        promises = [makeRequest(null)];
      }

      Promise.all(promises)
        .catch(error => {
          console.error('ERROR: ', error);
          this.emitter.emit('axiosError', { error: error.response.status });
        });
    }
  },
  computed: {
    paginatedOffers() {
      if (!this.isMounted) {
        return [];
      }
      const startIndex = (this.currentPage - 1) * this.NUMBER_OF_OFFERS_PER_PAGE;
      return this.offersIds.slice(startIndex, startIndex + this.NUMBER_OF_OFFERS_PER_PAGE);
    },
    totalPages() {
      return Math.ceil(this.offersIds.length / this.NUMBER_OF_OFFERS_PER_PAGE);
    },
  },
  mounted() {
    this.getCategories();
    this.getOffersData();
    this.setBrowserEmitter();
    this.setPagesEmitter();
    this.setFiltersEmitter();
    this.isMounted = true;
  }
}
</script>

<style scoped>
#offers-page {
  background: linear-gradient(to bottom, #F0FBFF, #0D2149);
  /*light-blue to COLORS.BUTTON_PRIMARY_NORMAL*/
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 100vh;
  padding-bottom: 5em;
}

#offers-page-main {
  margin-top: 0;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  width: 90%;
  margin: 0 auto;
}

#offers-page-filters {
  margin-top: 1em;
  width: 30%;
  align-self: flex-start;
}

#offers-page-content {
  margin-top: 1em;
  width: 70%;
  align-self: flex-start;
}

#offers-page-content-offers,
#offers-page-content-browser,
#offers-page-content-pagechange {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#offers-page-content-offers {
  margin-top: 1em;
}

#offers-page-content-offers ::v-deep .offer-preview-action {
  min-width: 200px;
}

#offers-page-content-offers ::v-deep .offer-preview-title,
#offers-page-content-offers ::v-deep .offer-preview-location {
  font-size: calc(13px + 0.5vw);
  justify-content: left;
  align-items: flex-start;
  flex-grow: 1;
  text-overflow: ellipsis;
}

#offers-page-content-offers ::v-deep .offer-preview-location {
  font-size: calc(11px + 0.5vw);

}

@media only screen and (max-width: 1050px) {
  #offers-page-content-offers ::v-deep .offer-preview-action {
    display: none;
  }

  #offers-page-content-offers ::v-deep .offer-preview-title,
  #offers-page-content-offers ::v-deep .offer-preview-location {
    text-align: left;
  }
}

@media screen and (max-width: 600px) {
  #offers-page-content-offers ::v-deep .offer-preview-image {
    aspect-ratio: 1/2;
  }
}

#offers-page-content-pagechange {
  margin-top: 1em;
  color: v-bind('COLORS.TEXT_PRIMARY');
}

p {
  background-color: v-bind('COLORS.NOTIFICATION_PRIMARY');
  font-size: v-bind('FONT_SIZES.PRIMARY');
  color: v-bind('COLORS.TEXT_PRIMARY');
  width: 18%;
  margin: -1% 5%;
  border-radius: 25px;
  padding: 10px;
  z-index: 2;
  position: absolute;
}
</style>
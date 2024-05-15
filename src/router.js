import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";
import Map from "@/components/pages/Map.vue";
import SingleOffer from "@/components/pages/SingleOffer.vue";
import Offers from "@/components/pages/Offers.vue";

const routes = [
  {
    path: "/",
    component: Main,
  },
  {
    path: "/map",
    component: Map,
  },
  {
    path: "/offer/:id",
    component: SingleOffer,
  },
  {
    path: "/offers",
    component: Offers,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

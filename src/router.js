import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";
import Map from "@/components/pages/Map.vue";
import SingleOffer from "@/components/pages/SingleOffer.vue";

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
    path: "/offer/get/:id",
    component: SingleOffer,
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

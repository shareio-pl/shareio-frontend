import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";
import Map from "@/components/pages/Map.vue";
import SingleOffer from "@/components/pages/SingleOffer.vue";

const routes = [
  {
    path: "/",
    component: Main,
    name: "ShareIO"
  },
  {
    path: "/map",
    component: Map,
    name: "Mapa - ShareIO"
  },
  {
    path: "/offer/:id",
    component: SingleOffer,
    name: "Oferta - ShareIO"
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

router.beforeEach((to, from, next) => {
  document.title = to.name;
  next();
});

export default router;

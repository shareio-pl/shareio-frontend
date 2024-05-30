import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";
import Map from "@/components/pages/Map.vue";
import SingleOffer from "@/components/pages/SingleOffer.vue";
import Offers from "@/components/pages/Offers.vue";
import Login from "@/components/pages/Login.vue";
import NewOffer from "@/components/pages/NewOffer.vue";
import AboutUs from "@/components/pages/AboutUs.vue";
import Scoreboard from "@/components/pages/Scoreboard.vue";

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
    path: "/offers",
    component: Offers,
    name: "Lista ofert - ShareIO"
  },
  {
    path: "/login",
    component: Login,
    name: "Login - ShareIO"
  },
  {
    path: "/newOffer",
    component: NewOffer,
    name: "Nowa oferta - ShareIO"
  },
  {
    path: "/about",
    component: AboutUs,
    name: "O nas - ShareIO"
  },
  {
    path: "/scoreboard",
    component: Scoreboard,
    name: "Ranking - ShareIO"
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


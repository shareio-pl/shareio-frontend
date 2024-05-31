import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";
import Map from "@/components/pages/Map.vue";
import SingleOffer from "@/components/pages/SingleOffer.vue";
import Offers from "@/components/pages/Offers.vue";
import ChangePass from "@/components/pages/ChangePass.vue";
import Login from "@/components/pages/Login.vue";
import NewOffer from "@/components/pages/NewOffer.vue";
import AboutUs from "@/components/pages/AboutUs.vue";
import Helpdesk from "@/components/pages/Helpdesk.vue";
import MyAccount from "@/components/pages/MyAccount.vue";

const routes = [
  {
    path: "/",
    component: Main,
    name: "ShareIO"
  },
  {
    path: "/map",
    component: Map,
    name: "Mapa - ShareIO",
    meta: { requiresAuth: true }
  },
  {
    path: "/offer/:id",
    component: SingleOffer,
    name: "Oferta - ShareIO"
  },
  {
    path: "/offers",
    component: Offers,
    name: "Lista ofert - ShareIO",
    meta: { requiresAuth: true }
  },
  {
    path: "/login",
    component: Login,
    name: "Login - ShareIO"
  },
  {
    path: "/newOffer",
    component: NewOffer,
    name: "Nowa oferta - ShareIO",
    meta: { requiresAuth: true },
  },
  {
    path: "/about",
    component: AboutUs,
    name: "O nas - ShareIO"
  },
  {
    path: "/help",
    component: Helpdesk,
    name: "Pomoc - ShareIO"
  },
  {
    path: "/changePassword",
    component: ChangePass,
    name: "Zmiana hasła - ShareIO",
    meta: { requiresAuth: true },
  },
  {
    path: "/myAccount",
    component: MyAccount,
    name: "Moje konto - ShareIO",
    meta: { requiresAuth: true },
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

  if (to.matched.some(record => record.meta.requiresAuth) && !localStorage.getItem('token')) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    });
  } else {
    next();
  }
});

export default router;


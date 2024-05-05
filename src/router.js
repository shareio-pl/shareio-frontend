import { createWebHistory, createRouter } from "vue-router";

import Main from "./components/pages/Main.vue";

const routes = [
  {
    path: "/",
    component: Main,
  },
  {
    path: "/map",
    component: () => import("./components/pages/Map.vue"),
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
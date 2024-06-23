import {createWebHistory, createRouter} from "vue-router";
import AuthRouter from "@/router/auth/AuthRouter";
import AdminRouter from "@/router/admin/AdminRouter";
import FrontRouter from "@/router/front/FrontRouter";

const routes = [
    ...AuthRouter,
    ...AdminRouter,
    ...FrontRouter
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
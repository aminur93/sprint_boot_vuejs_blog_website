import {createWebHistory, createRouter} from "vue-router";
import AuthRouter from "@/router/auth/AuthRouter";
import AdminRouter from "@/router/admin/AdminRouter";
import BlogRouter from "@/router/blog/BlogRouter";

const routes = [
    ...AuthRouter,
    ...AdminRouter,
    ...BlogRouter
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
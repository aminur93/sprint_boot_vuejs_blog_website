import Master from "@/views/admin/Master.vue";
import Dashboard from "@/views/admin/Dashboard.vue";
import store from "@/store";
import PermissionRouter from "@/router/admin/PermissionRouter";
import CategoryRouter from "@/router/admin/CategoryRouter";
import CommonRouter from "@/router/admin/CommonRouter";
import SubCategoryRouter from "@/router/admin/SubCategoryRouter";
import TagRouter from "@/router/admin/TagRouter";
import BlogRouter from "@/router/admin/BlogRouter";

export default [
    {
        path: '/dashboard',
        component: Master,
        children: [
            {
                path: '',
                name: 'Dashboard',
                component: Dashboard
            },

            ...PermissionRouter,
            ...CategoryRouter,
            ...SubCategoryRouter,
            ...TagRouter,
            ...BlogRouter,
            ...CommonRouter
        ],

        beforeEnter(to, from, next){
            if (!store.getters['AuthToken'])
            {
                next({name: 'Login'});
            }else {
                next();
            }
        }
    }
];
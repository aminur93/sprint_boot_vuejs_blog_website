import Master from "@/views/admin/Master.vue";
import Dashboard from "@/views/admin/Dashboard.vue";
import store from "@/store";
import PermissionRouter from "@/router/admin/PermissionRouter";

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

            ...PermissionRouter
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
import Index from "@/views/admin/settings/menu/Index.vue";
import Create from "@/views/admin/settings/menu/Create.vue";
import Edit from "@/views/admin/settings/menu/Edit.vue";

export default [

    {
        path: '/menu',
        name: 'Menu',
        component: Index
    },

    {
        path: '/add-menu',
        name: 'AddMenu',
        component: Create
    },

    {
        path: '/edit-menu/:id',
        name: 'EditMenu',
        component: Edit
    }
];
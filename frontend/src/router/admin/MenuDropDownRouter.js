import Index from "@/views/admin/settings/menu_dropdown/Index.vue";
import Create from "@/views/admin/settings/menu_dropdown/Create.vue";
import Edit from "@/views/admin/settings/menu_dropdown/Edit.vue";

export default [

    {
        path: '/menu-dropdown',
        name: 'MenuDropDown',
        component: Index
    },

    {
        path: '/add-menu-dropdown',
        name: 'AddMenuDropDown',
        component: Create
    },

    {
        path: '/edit-menu-dropdown/:id',
        name: 'EditMenuDropDown',
        component: Edit
    }
];
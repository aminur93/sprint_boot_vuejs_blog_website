import Index from "@/views/admin/tag/Index.vue";
import Create from "@/views/admin/tag/Create.vue";
import Edit from "@/views/admin/tag/Edit.vue";

export default [

    {
        path: '/tag',
        name: 'Tag',
        component: Index
    },

    {
        path: '/add-tag',
        name: 'AddTag',
        component: Create
    },

    {
        path: '/edit-tag/:id',
        name: 'EditTag',
        component: Edit
    }
];
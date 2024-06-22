import Index from "@/views/admin/category/Index.vue";
import Create from "@/views/admin/category/Create.vue";
import Edit from "@/views/admin/category/Edit.vue";

export default [

    {
        path: '/category',
        name: 'Category',
        component: Index
    },

    {
        path: '/add-category',
        name: 'AddCategory',
        component: Create
    },

    {
        path: '/edit-category/:id',
        name: 'EditCategory',
        component: Edit
    }
];
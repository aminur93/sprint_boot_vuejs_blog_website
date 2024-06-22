import Index from "@/views/admin/sub_category/Index.vue";
import Create from "@/views/admin/sub_category/Create.vue";
import Edit from "@/views/admin/sub_category/Edit.vue";

export default [

    {
        path: '/sub-category',
        name: 'SubCategory',
        component: Index
    },

    {
        path: '/add-sub-category',
        name: 'AddSubCategory',
        component: Create
    },

    {
        path: '/edit-sub-category/:id',
        name: 'EditSubCategory',
        component: Edit
    }
];
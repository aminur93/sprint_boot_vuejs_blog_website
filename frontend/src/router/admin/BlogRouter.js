import Index from "@/views/admin/blog/Index.vue";
import Create from "@/views/admin/blog/Create.vue";
import Edit from "@/views/admin/blog/Edit.vue";

export default [

    {
        path: '/blog',
        name: 'Blog',
        component: Index
    },

    {
        path: '/add-blog',
        name: 'AddBlog',
        component: Create
    },

    {
        path: '/edit-blog/:id',
        name: 'EditBlog',
        component: Edit
    }
];
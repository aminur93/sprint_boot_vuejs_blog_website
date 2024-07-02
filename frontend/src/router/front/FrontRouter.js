import Blog from "@/views/front/Blog.vue";
import Master from "@/views/front/Master.vue";
import BlogDetails from "@/views/front/BlogDetails.vue";

export default [
    {
        path: '/',
        component: Master,
        children: [
            {
                path: '',
                name: 'FrontBlog',
                component: Blog
            },

            {
                path: '/blog-details/:id',
                name: 'BlogDetails',
                component: BlogDetails
            }
        ]
    }
];
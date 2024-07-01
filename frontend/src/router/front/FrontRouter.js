import Blog from "@/views/blog/Blog.vue";
import Master from "@/views/blog/Master.vue";
import BlogDetails from "@/views/blog/BlogDetails.vue";

export default [
    {
        path: '/',
        component: Master,
        children: [
            {
                path: '',
                name: 'Blog',
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
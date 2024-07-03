import Blog from "@/views/front/Blog.vue";
import Master from "@/views/front/Master.vue";
import BlogDetails from "@/views/front/BlogDetails.vue";
import About from "@/views/front/About.vue";
import ContactUs from "@/views/front/ContactUs.vue";
import CategoryBlog from "@/views/front/CategoryBlog.vue";
import SubCategoryBlog from "@/views/front/SubCategoryBlog.vue";
import TagBlog from "@/views/front/TagBlog.vue";

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
            },

            {
                path: '/category-blog/:id',
                name: 'CategoryBlog',
                component: CategoryBlog
            },

            {
                path: '/sub-category-blog/:id',
                name: 'SubCategoryBlog',
                component: SubCategoryBlog
            },

            {
                path: '/tag-blog/:id',
                name: 'TagBlog',
                component: TagBlog
            },

            {
                path: '/about',
                name: 'About',
                component: About
            },

            {
                path: '/contact-us',
                name: 'ContactUs',
                component: ContactUs
            }
        ]
    }
];
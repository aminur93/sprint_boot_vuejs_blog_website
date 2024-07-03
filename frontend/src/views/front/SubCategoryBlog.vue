<script>
import axios from "axios";
import {http} from "@/service/HttpService";

export default {
  name: "FrontSubCategoryBlog",

  data(){
    return{
      blogs: [],
      currentPage: 1,
      lastPage: 1,
      loading: false,

      subCategory: {}
    }
  },

  computed: {
    hasMore() {
      return this.currentPage <= this.lastPage;
    }
  },

  methods: {
    async loadMore() {
      if (this.loading || !this.hasMore) return;
      this.loading = true;
      try {
        let id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8080/api/v1/public/category-blog/${id}`, {
          params: {
            page: this.currentPage,
            perPage: 10
          }
        });
        const data = response.data;
        this.blogs = [...this.blogs, ...data.data];
        this.currentPage = data.meta.currentPage + 1;
        this.lastPage = data.meta.lastPage;
      } catch (error) {
        console.error('Failed to load more blogs:', error);
      } finally {
        this.loading = false;
      }
    },

    getImageSrc(image) {
      const src = `${this.$store.state.serverPath}/images/${image}`;
      return src;
    },

    formattedTime(dateString) {
      if (!dateString) return '';
      const dateObj = new Date(dateString);
      if (isNaN(dateObj.getTime())) return '';
      return dateObj.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });
    },

    getAllSubCategories: async function(){
      try {
        await http().get('http://localhost:8080/api/v1/public/sub-category').then(res => {
          this.subCategories = res.data.data;
        })
      }catch (e) {
        console.error('Failed to load more sub-categories:', e);
      }
    },
  },

  async created() {
    await this.loadMore();
  }
}
</script>

<template>
  <div id="sub_category_blog">
    <div class="category_blog_main">
      <div class="category_blog_container">

        <div class="blog" v-if="blogs.length > 0">
          <h2 class="h2">Latest Blog By {{category.name}}</h2>
          <div class="blog-card-group">
            <div class="category_blog_card" v-for="blog in blogs" :key="blog.id">
              <div class="blog-card-banner">
                <img :src="getImageSrc(blog.image)"
                     alt="Building microservices with Dropwizard, MongoDB & Docker" width="250"
                     class="blog-banner-img">
              </div>
              <div class="blog-content-wrapper">
                <button class="blog-topic text-tiny">{{blog.category.name}}</button>
                <h3><router-link :to="`/blog-details/${blog.id}`" class="h3">{{ blog.title }}</router-link></h3>
                <p class="blog-text">
                  {{ blog.description }}
                </p>
                <div class="wrapper-flex">
                  <div class="profile-wrapper">
                    <img :src="require('@/assets/img/author-1.png')" alt="Julia Walker" width="50">
                  </div>
                  <div class="wrapper">
                    <a href="#" class="h4">{{blog.author}}</a>
                    <p class="text-sm">
                      <time datetime="2022-01-17">{{blog.date}}</time>
                      <span class="separator"></span>
                      <ion-icon name="time-outline"></ion-icon>
                      <time>{{ this.formattedTime(blog.createdAt) }}</time>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <button @click="loadMore" class="btn load-more" :disabled="loading || !hasMore">Load More</button>
        </div>
        <div class="blog" v-else>
          <div class="blog-card-group">
            <h1 style="margin: 0 auto;">No data found</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.category_blog_main {
  padding: 20px;
  background-color: #f0f0f0;
}

.category_blog_container {
  max-width: 1500px;
  margin: 0 auto;
}

.blog {
  margin-top: 20px;
}

.blog-card-group {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.category_blog_card {
  background: var(--background-primary);
  padding: 10px;
  margin-bottom: 1rem;
  border-radius: 10px;
  box-shadow: 0 10px 10px hsla(0, 0%, 0%, 0.05);
  transition: 0.25s ease;
  width: calc(25% - 15px); /* Adjust width as needed */
}

.category_blog_card:hover {
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

.blog-card-banner img {
  width: 100%;
  height: auto;
  display: block;
}

.blog-content-wrapper {
  padding: 20px;
}

.blog-topic {
  padding: 5px 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 20px;
  font-size: 12px;
  text-transform: uppercase;
  margin-bottom: 10px;
  display: inline-block;
}

.h3 {
  font-size: 18px;
  margin: 10px 0;
  text-decoration: none;
  color: #333;
  display: block;
  transition: color 0.3s ease;
}

.h3:hover {
  color: #007bff;
}

.blog-text {
  color: #666;
  line-height: 1.6;
}

.wrapper-flex {
  display: flex;
  align-items: center;
  margin-top: 15px;
}

.profile-wrapper img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 10px;
}

.wrapper {
  flex: 1;
}

.h4 {
  font-size: 14px;
  margin-bottom: 5px;
  color: #333;
  text-decoration: none;
  transition: color 0.3s ease;
}

.h4:hover {
  color: #007bff;
}

.text-sm {
  font-size: 12px;
  color: #999;
  display: flex;
  align-items: center;
}

.text-sm ion-icon {
  font-size: 14px;
  margin-right: 5px;
}

.separator {
  margin: 0 5px;
}

@media screen and (max-width: 992px) {
  .category_blog_card {
    width: calc(50% - 15px); /* 2 cards per row on smaller screens */
  }
}

@media screen and (max-width: 768px) {
  .category_blog_card {
    width: 100%; /* 1 card per row on mobile screens */
  }
}
</style>
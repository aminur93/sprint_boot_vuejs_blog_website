<script>
import axios from "axios";
import {http} from "@/service/HttpService";

export default {
  name: "FrontBlog",

  data(){
    return{
      blogs: [],
      currentPage: 1,
      lastPage: 1,
      loading: false,

      categories: [],
      subCategories: [],
      tags: []
    }
  },

  computed: {
    hasMore() {
      return this.currentPage <= this.lastPage;
    }
  },

  mounted() {
    this.getAllCategories();
    this.getAllSubCategories();
    this.getAllTags();
  },

  methods: {

    async loadMore() {
      if (this.loading || !this.hasMore) return;
      this.loading = true;
      try {
        const response = await axios.get('http://localhost:8080/api/v1/public/blog', {
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

    getAllCategories: async function(){
      try {
        await http().get('http://localhost:8080/api/v1/public/category').then(res => {
          this.categories = res.data.data;
        })
      }catch (e) {
        console.error('Failed to load more categories:', e);
      }
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

    getAllTags: async function(){
      try {
        await http().get('http://localhost:8080/api/v1/public/tag').then(res => {
          this.tags = res.data.data;
        })
      }catch (e) {
        console.error('Failed to load more tags:', e);
      }
    }
  },

  async created() {
    await this.loadMore();
  }
}
</script>

<template>
    <div class="light-theme">

      <main>
        <div class="main">

          <div class="container">


            <div class="blog">

              <h2 class="h2">Latest Blog Post</h2>

              <div class="blog-card-group">

                <div class="blog-card" v-for="blog in blogs" :key="blog.id">

                  <div class="blog-card-banner">
                    <img :src="getImageSrc(blog.image)" alt="Building microservices with Dropwizard, MongoDB & Docker"
                         width="250" class="blog-banner-img">
                  </div>

                  <div class="blog-content-wrapper">

                    <button class="blog-topic text-tiny">{{blog.category.name}}</button>

                    <h3>
                      <a href="#" class="h3">
                        {{ blog.title }}
                      </a>
                    </h3>

                    <p class="blog-text">
                      {{blog.description}}
                    </p>

                    <div class="wrapper-flex">

                      <div class="profile-wrapper">
                        <img :src="require('@/assets/img/author-1.png')" alt="Julia Walker" width="50">
                      </div>

                      <div class="wrapper">
                        <a href="#" class="h4">{{ blog.author}}</a>

                        <p class="text-sm">
                          <time datetime="2022-01-17">{{ blog.date }}</time>
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


            <div class="aside">

              <div class="topics">

                <h2 class="h2">Categories</h2>

                <a href="#" class="topic-btn" v-for="category in categories" :key="category.id">
                  <div class="icon-box">
                    <ion-icon name="arrow-forward-outline"></ion-icon>
                  </div>

                  <p>{{ category.name }}</p>
                </a>
              </div>

              <div class="topics">

                <h2 class="h2">Sub-Categories</h2>

                <a href="#" class="topic-btn" v-for="subCategory in subCategories" :key="subCategory.id">
                  <div class="icon-box">
                    <ion-icon name="arrow-forward-outline"></ion-icon>
                  </div>

                  <p>{{ subCategory.name }}</p>
                </a>

              </div>

              <div class="tags">

                <h2 class="h2">Tags</h2>

                <div class="wrapper">
                  <button class="hashtag" v-for="tag in tags" :key="tag.id">#{{tag.name}}</button>
                </div>

              </div>

              <div class="contact">

                <h2 class="h2">Let's Talk</h2>

                <div class="wrapper">

                  <p>
                    Do you want to learn more about how I can help your company overcome problems? Let us have a
                    conversation.
                  </p>

                  <ul class="social-link">

                    <li>
                      <a href="#" class="icon-box discord">
                        <ion-icon name="logo-discord"></ion-icon>
                      </a>
                    </li>

                    <li>
                      <a href="#" class="icon-box twitter">
                        <ion-icon name="logo-twitter"></ion-icon>
                      </a>
                    </li>

                    <li>
                      <a href="#" class="icon-box facebook">
                        <ion-icon name="logo-facebook"></ion-icon>
                      </a>
                    </li>

                  </ul>

                </div>

              </div>

              <div class="newsletter">

                <h2 class="h2">Newsletter</h2>

                <div class="wrapper">

                  <p>
                    Subscribe to our newsletter to be among the first to keep up with the latest updates.
                  </p>

                  <form action="#">
                    <input type="email" name="email" placeholder="Email Address" required>

                    <button type="submit" class="btn btn-primary">Subscribe</button>
                  </form>

                </div>

              </div>

            </div>

          </div>

        </div>
      </main>

    </div>
</template>

<style scoped>

</style>
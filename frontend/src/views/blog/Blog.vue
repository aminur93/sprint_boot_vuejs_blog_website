<script>
import axios from "axios";

export default {
  name: "FrontBlog",

  data(){
    return{
      blogs: [],
      currentPage: 1,
      lastPage: 1,
      loading: false,
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
        const response = await axios.get('http://localhost:8080/api/v1/public/blog', {
          params: {
            page: this.currentPage,
            perPage: 1
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

            <!--
              - BLOG SECTION
            -->

            <div class="blog">

              <h2 class="h2">Latest Blog Post</h2>

              <div class="blog-card-group">

                <div class="blog-card" v-for="blog in blogs" :key="blog.id">

                  <div class="blog-card-banner">
                    <img :src="require('@/assets/img/blog-1.png')" alt="Building microservices with Dropwizard, MongoDB & Docker"
                         width="250" class="blog-banner-img">
                  </div>

                  <div class="blog-content-wrapper">

                    <button class="blog-topic text-tiny">Database</button>

                    <h3>
                      <a href="#" class="h3">
                        Building microservices with Dropwizard, MongoDB & Docker
                      </a>
                    </h3>

                    <p class="blog-text">
                      This NoSQL database oriented to documents (by documents like JSON) combines some of the features from
                      relational
                      databases, easy to use and the multi-platform is the best option for scale up and have fault
                      tolerance, load balancing,
                      map reduce, etc.
                    </p>

                    <div class="wrapper-flex">

                      <div class="profile-wrapper">
                        <img :src="require('@/assets/img/author.png')" alt="Julia Walker" width="50">
                      </div>

                      <div class="wrapper">
                        <a href="#" class="h4">Julia Walker</a>

                        <p class="text-sm">
                          <time datetime="2022-01-17">Jan 17, 2022</time>
                          <span class="separator"></span>
                          <ion-icon name="time-outline"></ion-icon>
                          <time datetime="PT3M">3 min</time>
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

                <h2 class="h2">Topics</h2>

                <a href="#" class="topic-btn">
                  <div class="icon-box">
                    <ion-icon name="server-outline"></ion-icon>
                  </div>

                  <p>Database</p>
                </a>

                <a href="#" class="topic-btn">
                  <div class="icon-box">
                    <ion-icon name="accessibility-outline"></ion-icon>
                  </div>

                  <p>Accessibility</p>
                </a>

                <a href="#" class="topic-btn">
                  <div class="icon-box">
                    <ion-icon name="rocket-outline"></ion-icon>
                  </div>

                  <p>Web Performance</p>
                </a>

              </div>

              <div class="tags">

                <h2 class="h2">Tags</h2>

                <div class="wrapper">

                  <button class="hashtag">#mongodb</button>
                  <button class="hashtag">#nodejs</button>
                  <button class="hashtag">#a11y</button>
                  <button class="hashtag">#mobility</button>
                  <button class="hashtag">#inclusion</button>
                  <button class="hashtag">#webperf</button>
                  <button class="hashtag">#optimize</button>
                  <button class="hashtag">#performance</button>

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
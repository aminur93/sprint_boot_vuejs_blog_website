<script>

import Header from "@/components/front/layouts/Header.vue";
import Footer from "@/components/front/layouts/Footer.vue";

export default {
  name: "FrontMaster",
  components: {
    Header: Header,
    Footer: Footer
  }
}
</script>

<template>
  <v-app class="light-theme">

    <Header />

    <v-main>
      <div class="hero">

        <div class="container">

          <div class="left">

            <h6 class="h1" style="font-size: 2rem;">
              A leader and a follower in <b>Web Development</b>.
            </h6>

            <div class="btn-group">
              <a href="#" class="btn btn-primary">Contact Me</a>
              <a href="#" class="btn btn-secondary">About Me</a>
            </div>

          </div>

          <div class="right">

            <div class="pattern-bg"></div>
            <div class="img-box">
              <img :src="require('@/assets/img/blogs.png')" alt="Julia Walker" class="hero-img">
              <div class="shape shape-1"></div>
              <div class="shape shape-2"></div>
            </div>

          </div>

        </div>

      </div>

      <router-view></router-view>

    </v-main>

    <Footer />
  </v-app>
</template>

<style>
@import "../../assets/css/style.css";

.copyright {
  color: var(--foreground-secondary);
  font-size: var(--fs-4);
  text-align: center;
  padding: 1rem;
  border-top: 1px solid hsl(214, 32%, 91%);
}
</style>
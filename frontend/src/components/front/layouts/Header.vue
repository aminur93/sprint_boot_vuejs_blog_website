<script>
export default {
  name: "FrontHeader"
}
</script>

<template>
  <div id="header">
    <header>

      <div class="container">

        <nav class="navbar">

          <router-link to="/">
            <img :src="require('@/assets/img/logo-light.svg')" alt="Devblog's logo" width="150" class="logo-light">
            <img :src="require('@/assets/img/logo-dark.svg')" alt="Devblog's logo" width="150" class="logo-dark">
          </router-link>

          <div class="btn-group">

            <button class="nav-menu-btn">
              <ion-icon name="menu-outline"></ion-icon>
            </button>

          </div>

          <div class="flex-wrapper">

            <ul class="desktop-nav">

              <li>
                <router-link to="/" class="nav-link">Home</router-link>
              </li>

              <li>
                <router-link to="/about" class="nav-link">About Me</router-link>
              </li>

              <li>
                <router-link to="/contact-us" class="nav-link">Contact</router-link>
              </li>

            </ul>
          </div>

          <div class="mobile-nav">

            <button class="nav-close-btn">
              <ion-icon name="close-outline"></ion-icon>
            </button>

            <div class="wrapper">

              <p class="h3 nav-title">Main Menu</p>

              <ul>
                <li class="nav-item">
                  <router-link to="/" class="nav-link">Home</router-link>
                </li>

                <li class="nav-item">
                  <router-link to="/about" class="nav-link">About Me</router-link>
                </li>

                <li class="nav-item">
                  <router-link to="/contact-us" class="nav-link">Contact</router-link>
                </li>
              </ul>

            </div>

            <div>

              <p class="h3 nav-title">Topics</p>

              <ul>
                <li class="nav-item">
                  <a href="#" class="nav-link">Database</a>
                </li>

                <li class="nav-item">
                  <a href="#" class="nav-link">Accessibility</a>
                </li>

                <li class="nav-item">
                  <a href="#" class="nav-link">Web Performance</a>
                </li>
              </ul>

            </div>

          </div>

        </nav>

      </div>

    </header>
  </div>
</template>

<style scoped>

</style>
<script>
export default {
  name: "FrontFooter"
}
</script>

<template>
    <div id="footer">
      <footer>

        <div class="container">

          <div class="wrapper">

            <a href="#" class="footer-logo">
              <img :src="require('@/assets/img/logo-light.svg')" alt="DevBlog's Logo" width="150" class="logo-light">
              <img :src="require('@/assets/img/logo-dark.svg')" alt="DevBlog's Logo" width="150" class="logo-dark">
            </a>

            <p class="footer-text">
              Learn about Web accessibility, Web performance, and Database management.
            </p>

          </div>

          <div class="wrapper">

            <p class="footer-title">Quick Links</p>

            <ul>

              <li>
                <a href="#" class="footer-link">Advertise with us</a>
              </li>

              <li>
                <a href="#" class="footer-link">About Us</a>
              </li>

              <li>
                <a href="#" class="footer-link">Contact Us</a>
              </li>

            </ul>

          </div>

          <div class="wrapper">

            <p class="footer-title">Legal Stuff</p>

            <ul>

              <li>
                <a href="#" class="footer-link">Privacy Notice</a>
              </li>

              <li>
                <a href="#" class="footer-link">Cookie Policy</a>
              </li>

              <li>
                <a href="#" class="footer-link">Terms Of Use</a>
              </li>

            </ul>

          </div>

        </div>

        <p class="copyright">
          &copy; Copyright 2024 <a href="#">RangerTech</a>
        </p>

      </footer>
    </div>
</template>

<style scoped>

</style>
<script>
export default {
  name: "FrontAbout"
}
</script>

<template>
  <div id="about">
    <div class="about_blog_main">
      <div class="about_blog_container">

        <div class="blog">
          <h2 class="h2">About Us</h2>
          <div class="blog-card-group">
            <div class="category_blog_card">
              <div class="blog-content-wrapper">
                <h3>About</h3>
                <hr>
                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>

                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to
                  using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>

                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to
                  using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>
              </div>
            </div>

            <div class="category_blog_card">
              <div class="blog-content-wrapper">
                <h3>Our History</h3><hr>
                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to
                  using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>

                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to
                  using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>

                <p class="blog-text">
                  It is a long established fact that a reader will be distracted by the readable content of a page when looking at its
                  layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to
                  using
                  'Content here, content here', making it look like readable English. Many desktop publishing packages and web page
                  editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites
                  still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose
                  (injected humour and the like).
                </p>
              </div>
            </div>
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<style scoped>
.about_blog_main {
  background: var(--background-secondary);
  padding: var(--py) 0;
}

.about_blog_container {
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
  width: calc(50% - 15px);
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
</style>
<script>
export default {
  name: "BlogDetails"
}
</script>

<template>
  <div id="blog_details">
    <main>
      <div class="blog_main">
        <div class="blog_container">

          <div class="blog-details-card">
            <div class="blog-image">
              <img :src="require('@/assets/img/blog-1.png')" alt="Building microservices with Dropwizard, MongoDB & Docker"
                   class="blog-banner-img">
            </div>

            <h1 class="blog-title">Building microservices with Dropwizard, MongoDB & Docker</h1>

            <h2 class="blog-subtitle">A Comprehensive Guide</h2>

            <div class="blog-description">
              <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard
                dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen
                book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially
                unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more
                recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
              </p>

              <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                standard
                dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type
                specimen
                book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially
                unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and
                more
                recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
              </p>

              <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                standard
                dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type
                specimen
                book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially
                unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and
                more
                recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
              </p>
            </div>

            <div class="wrapper-flex">
              <div class="profile-wrapper">
                <img :src="require('@/assets/img/author.png')" alt="Julia Walker" width="50">
              </div>

              <div class="wrapper">
                <a href="#" class="h4">Julia Walker</a>

                <p class="text-sm">
                  <time datetime="2022-01-17">Jan 17, 2022</time>
                  <span class="separator"></span>
                  <ion-icon name="time-outline" role="img" class="md hydrated" aria-label="time outline"></ion-icon>
                  <time datetime="PT3M">3 min</time>
                </p>
              </div>

              <div class="blog_category">
                <h5 class="h5" style="margin-left: 5px;">Category : </h5>
                <button class="hashtag">Database</button>
              </div>

              <div class="blog_category">
                <h5 class="h5" style="margin-left: 5px;">Sub-Category : </h5>
                <button class="hashtag">NoSql</button>
              </div>
            </div>

            <div class="tags">
              <h5 class="h5">Tags</h5>
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

            <div class="comments-section">
              <h3>Comments</h3>

              <div class="comment-card">
                <div class="comment-author">
                  <img :src="require('@/assets/img/author.png')" alt="John Doe">
                  <p><strong>John Doe</strong></p>
                </div>
                <div class="comment-content">
                  <p>This is a very informative blog. Thanks for sharing!</p>
                </div>
                <div class="reply-form">
                  <textarea placeholder="Reply to this comment"></textarea>
                  <button>Reply</button>
                </div>
              </div>

              <div class="comment-card">
                <div class="comment-author">
                  <img :src="require('@/assets/img/author.png')" alt="Jane Smith">
                  <p><strong>Jane Smith</strong></p>
                </div>
                <div class="comment-content">
                  <p>Great insights! Can you also cover some advanced topics in the future?</p>
                </div>

                <div class="reply-card">
                  <div class="reply-author">
                    <img :src="require('@/assets/img/author.png')" alt="Julia Walker">
                    <p><strong>Julia Walker</strong></p>
                  </div>
                  <div class="reply-content">
                    <p>Thank you, Jane! Sure, I'll cover some advanced topics in upcoming blogs.</p>
                  </div>
                  <div class="reply-form">
                    <textarea placeholder="Reply to this comment"></textarea>
                    <button>Reply</button>
                  </div>
                </div>
              </div>

              <div class="comment-form comment-card">
                <form action="">
                  <input type="text" name="name" placeholder="Your Name">
                  <input type="email" name="email" placeholder="Your Email">
                  <textarea name="comment" placeholder="Add a comment"></textarea>
                  <button type="submit">Submit</button>
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
.h4 {
  color: var(--foreground-secondary);
}

.text-sm {
  display: flex;
  align-items: center;
  gap: 5px;
  color: var(--foreground-tertiary);
}

.h5 {
  color: var(--foreground-secondary);
  margin-bottom: 0.5rem;
  line-height: 1.3;
  margin-top: 15px;
}

a{
  font: inherit;
  border: none;
  background: none;
  cursor: pointer;
}


.wrapper-flex {
  display: flex;
  justify-content: start;
  align-items: center;
  gap: 10px;
}

.blog_main{
  background: var(--background-secondary);
  padding: var(--py) 0;
}

.blog_container{
  max-width: 1200px;
  margin-inline: auto;
  padding: 0 15px;
  margin: auto;
  display: grid;
  grid-template-columns: 1fr;
  gap: 30px;
}

.blog-details-card {
  width: 100%;
  background: var(--background-primary);
  padding: 10px;
  margin-bottom: 1rem;
  border-radius: 10px;
  box-shadow: 0 10px 10px hsla(0, 0%, 0%, 0.05);
  transition: 0.25s ease;
}

.blog-image img {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
}

.blog-title {
  color: #000;
  font-size: 24px;
  margin: 10px 0;
}

.blog-subtitle {
  font-size: 18px;
  color: var(--foreground-primary);
  margin-bottom: 20px;
}

.blog-description {
  color: var(--foreground-secondary);
  font-size: var(--fs-4);
  line-height: 1.4rem;
  margin-bottom: 1rem;
}

.blog_category{
  display: flex;
  flex-direction: row;
  gap: 1rem;
  margin-left: 20px;
  margin-bottom: -20px;
}

.blog_category .hashtag{

  background: var(--action-primary);
  color: var(--foreground-secondary);
  padding: 5px 10px;
  font-size: var(--fs-5);
  font-weight: 700;
  border-radius: 5px;
}

.blog_category .hashtag:hover {
  background: var(--foreground-secondary);
  color: var(--action-primary);
}

.comments-section {
  margin-top: 30px;
}

.comment-card {
  background-color: #fff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.comment-author {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.comment-author img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-content {
  margin-bottom: 10px;
}

.reply-card {
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  margin-left: 40px;
  margin-bottom: 10px;
}

.reply-author {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.reply-author img {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  margin-right: 10px;
}

.reply-content {
  margin-bottom: 5px;
}

.comment-form,
.reply-form {
  margin-top: 10px;
}

.comment-form textarea,
.reply-form textarea {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}

.comment-form {
  margin-top: 20px;
}

.comment-form form {
  display: flex;
  flex-direction: column;
}

.comment-form input[type="text"], input[type="email"],
.comment-form textarea {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.comment-form textarea {
  resize: vertical;
  min-height: 100px;
}

.comment-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  cursor: pointer;
  font-size: 16px;
}

.comment-form button:hover {
  background-color: #0056b3;
}
</style>
<script>
import {http} from "@/service/HttpService";

export default {
  name: "BlogDetails",

  data(){
    return{
      blog: {},
      comments: [],

      add_comment: {
        name: '',
        email: '',
        comment: ''
      },

      add_reply: {}
    }
  },

  mounted() {
    this.getSingleBlog(this.$route.params.id);
    console.log(this.add_reply);
  },

  methods: {
    getSingleBlog: async function(){
      try {
        let id = this.$route.params.id;

        await http().get(`http://localhost:8080/api/v1/public/blog-details/${id}`).then(res => {

          this.blog = res.data.data;
          this.comments = res.data.data.comments;

          // Initialize add_reply for each comment
          this.comments.forEach(comment => {
            this.add_reply[comment.id] = { name: '', reply: '' };
          });
        })
      }catch (e) {
        console.error(e);
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

    addComment: async function(){
      try {

        let formData = new FormData();

        formData.append('blog_id', this.blog.id);
        formData.append('name', this.add_comment.name);
        formData.append('email', this.add_comment.email);
        formData.append('comment', this.add_comment.comment);

        await http().post('http://localhost:8080/api/v1/public/comment', formData).then(res => {

          if (res.data.status === 201)
          {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: res.data.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.add_comment = {};

            this.getSingleBlog(this.$route.params.id);
          }
        })
      }catch (e) {
        console.log(e);
      }
    },

    AddReply: async function(comment_id){
      try {

        if (!this.add_reply[comment_id]) {
          this.$set(this.add_reply, comment_id, { name: '', reply: '' });
        }

        let formData = new FormData();

        formData.append('comment_id', comment_id);
        formData.append('name', this.add_reply[comment_id].name);
        formData.append('reply', this.add_reply[comment_id].reply);

        await http().post('http://localhost:8080/api/v1/public/reply', formData).then(res => {

          if (res.data.status === 201)
          {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: res.data.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.getSingleBlog(this.$route.params.id);
          }
        })
      }catch (e) {
        console.log(e);
      }
    }
  }
}
</script>

<template>
  <div id="blog_details">
    <main>
      <div class="blog_main">
        <div class="blog_container">

          <div class="blog-details-card">
            <div class="blog-image">
              <img :src="getImageSrc(blog.image)" alt="blog image" class="blog-banner-img">
            </div>

            <h1 class="blog-title">{{blog.title}}</h1>

            <h2 class="blog-subtitle">{{blog.slogan}}</h2>

            <div class="blog-description">
              <p>
                {{blog.description}}
              </p>
            </div>

            <div class="wrapper-flex">
              <div class="profile-wrapper">
                <img :src="require('@/assets/img/author-1.png')" alt="Julia Walker" width="50">
              </div>

              <div class="wrapper">
                <a href="#" class="h4">{{blog.author}}</a>

                <p class="text-sm">
                  <time datetime="2022-01-17">{{blog.date}}</time>
                  <span class="separator"></span>
                  <ion-icon name="time-outline" role="img" class="md hydrated" aria-label="time outline"></ion-icon>
                  <time>{{ this.formattedTime(blog.createdAt) }}</time>
                </p>
              </div>

              <div class="blog_category">
                <h5 class="h5" style="margin-left: 5px;">Category : </h5>
                <button class="hashtag" v-if="blog.category">{{blog.category.name}}</button>
              </div>

              <div class="blog_category">
                <h5 class="h5" style="margin-left: 5px;">Sub-Category : </h5>
                <button class="hashtag" v-if="blog.subCategory">{{blog.subCategory.name}}</button>
              </div>
            </div>

            <div class="tags">
              <h5 class="h5">Tags</h5>
              <div class="wrapper">
                <button class="hashtag" v-for="tag in blog.tags" :key="tag.id">#{{tag.name}}</button>
              </div>
            </div>

            <div class="comments-section">
              <h3>Comments</h3>

              <div class="comment-card" v-for="(comment,index) in comments" :key="index">
                <div class="comment-author">
                  <img :src="require('@/assets/img/comment.png')" alt="John Doe">
                  <p><strong>{{comment.name}}</strong></p>
                </div>
                <div class="comment-content">
                  <p>{{ comment.comment }}</p>
                </div>

                <div v-if="comment.replys.length > 0">
                  <div class="reply-card" v-for="reply in comment.replys" :key="reply.id">
                    <div class="reply-author">
                      <img :src="require('@/assets/img/comment.png')" alt="Julia Walker">
                      <p><strong>{{reply.name}}</strong></p>
                    </div>
                    <div class="reply-content">
                      <p>{{reply.reply}}</p>
                    </div>
                    <div class="reply-form">
                      <form v-on:submit.prevent="AddReply(comment.id)">
                        <input type="text" v-model="add_reply[comment.id].name" placeholder="Name">
                        <textarea v-model="add_reply[comment.id].reply" placeholder="Reply to this comment"></textarea>
                        <button type="submit">Reply</button>
                      </form>
                    </div>
                  </div>
                </div>

<!--                <div class="reply-form" v-else>-->
<!--                  <form v-on:submit.prevent="AddReply(comment.id)">-->
<!--                    <input type="text" v-model="add_reply.name" name="" id="">-->
<!--                    <textarea placeholder="Reply to this comment" v-model="add_reply.reply"></textarea>-->
<!--                    <button type="submit">Reply</button>-->
<!--                  </form>-->
<!--                </div>-->
              </div>

              <div class="comment-form comment-card">
                <form v-on:submit.prevent="addComment">
                  <input type="text" v-model="add_comment.name" name="name" placeholder="Your Name">
                  <textarea name="comment" v-model="add_comment.comment" placeholder="Add a comment"></textarea>
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

.comment-form textarea, input[type="text"],
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
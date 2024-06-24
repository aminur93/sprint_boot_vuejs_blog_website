<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "BlogEdit",

  data(){
    return{
      image: '',
      imagePreview: '',
    }
  },

  computed: {
    ...mapState({
      editBlog: state => state.blog.blog,
      tags: state => state.tag.tags,
      subCategories: state => state.sub_category.subCategories,
      categories: state => state.category.categories,
      message: state => state.blog.success_message,
      errors: state => state.blog.errors,
      success_status: state => state.blog.success_status,
      error_status: state => state.blog.error_status
    }),

    filteredSubCategories() {
      return this.subCategories.filter(subCategory => subCategory.category.id === this.editBlog.category);
    }
  },

  mounted() {
    this.getEditBlog(this.$route.params.id);
    this.getAllCategory();
    this.getAllSubCategory();
    this.getAllTag();
  },

  methods: {
    ...mapActions({
      getEditBlog: "blog/GetSingleBlog",
      getAllCategory: "category/GetAllCategory",
      getAllSubCategory: "sub_category/GetAllSubCategory",
      getAllTag: "tag/GetAllTag"
    }),

    ResetSubCategory(blog){
      blog.subCategory = '';
    },

    getImageSrc(image) {
      const src = `${this.$store.state.serverPath}/images/${image}`;
      return src;
    },

    previewNIDImage() {
      const file = this.image;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imagePreview = e.target.result;
        };
        reader.readAsDataURL(file);
      } else {
        this.imagePreview = '';
      }
    },

    updateBlog: async function(){
      try {
        let id = this.$route.params.id;
        let formData = new FormData();

        formData.append('category_id', this.editBlog.category);
        formData.append('sub_category_id', this.editBlog.subCategory);
        formData.append('author', this.editBlog.author);
        formData.append('title', this.editBlog.title);
        formData.append('slogan', this.editBlog.slogan);
        formData.append('description', this.editBlog.description);
        formData.append('date', this.editBlog.date);
        formData.append('image', this.image);
        formData.append('status', this.editBlog.status);

        this.editBlog.tags.forEach(item => formData.append('tag_ids[]', item));

        await this.$store.dispatch('blog/UpdateBlog', {id:id, data:formData}).then(() => {

          if (this.success_status === 200)
          {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: this.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.getEditBlog(id);
            this.imagePreview = null;

            setTimeout(function () {
              router.push({path: '/blog'});
            },2000)
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
  <div id="edit">
    <v-row class="mx-5 mt-5">
      <v-col cols="12">
        <v-row>
          <v-col cols="12" md="12" sm="12" lg="12">
            <v-card>
              <v-card-title><h3>Edit Blog</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="updateBlog">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="editBlog.category"
                                :items="categories"
                                item-title="name"
                                item-value="id"
                                label="select Category"
                                @click="ResetSubCategory(editBlog)"
                            ></v-select>
                            <p v-if="errors.category_id" class="error custom_error">{{errors.category_id}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="editBlog.subCategory"
                                :items="filteredSubCategories"
                                item-title="name"
                                item-value="id"
                                label="select Sub Category"
                            ></v-select>
                            <p v-if="errors.sub_category_id" class="error custom_error">{{errors.sub_category_id}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="editBlog.author"
                            label="Author"
                            persistent-hint
                            variant="outlined"
                        ></v-text-field>
                        <p v-if="errors.author" class="error custom_error">{{errors.author}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-select
                            variant="outlined"
                            v-model="editBlog.tags"
                            :items="tags"
                            item-title="name"
                            item-value="id"
                            label="select Tags"
                            multiple
                            chips
                        ></v-select>
                        <p v-if="errors.tag_ids" class="error custom_error">{{errors.tag_ids}}</p>
                      </v-col>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="editBlog.title"
                                label="Title"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.title" class="error custom_error">{{errors.title}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="editBlog.slogan"
                                label="Slogan"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.slogan" class="error custom_error">{{errors.slogan}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-textarea
                            v-model="editBlog.description"
                            label="Description"
                            variant="outlined"
                        ></v-textarea>
                        <p v-if="errors.description" class="error custom_error">{{errors.description}}</p>
                      </v-col>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="date"
                                v-model="editBlog.date"
                                label="Date"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.date" class="error custom_error">{{errors.date}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-file-input
                                v-model="image"
                                persistent-hint
                                label="File input"
                                placeholder="Select your files"
                                prepend-icon="mdi-paperclip"
                                variant="outlined"
                                counter
                                @change="previewNIDImage"
                            ></v-file-input>

                            <div v-if="imagePreview">
                              <img :src="imagePreview" alt="Image Preview" style="width: 100%; height: 300px" />
                            </div>
                            <div v-else>
                              <img :src="getImageSrc(editBlog.image)" alt="" width="100%" height="300px">
                            </div>

                            <p v-if="errors.image" class="error custom_error">{{errors.image}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-checkbox
                            v-model="editBlog.status"
                            label="Status"
                        ></v-checkbox>
                        <p v-if="errors.status" class="error custom_error">{{errors.status}}</p>
                      </v-col>

                      <v-row wrap>
                        <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                          <v-btn
                              flat
                              color="primary"
                              class="custom-btn mr-2"
                              router
                              to="/blog"
                          >
                            Back
                          </v-btn>
                          <v-btn
                              flat
                              color="success"
                              type="submit"
                              class="custom-btn mr-2"
                          >
                            Submit
                          </v-btn>
                        </v-col>
                      </v-row>

                    </v-row>
                  </v-col>
                </v-form>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>

</style>
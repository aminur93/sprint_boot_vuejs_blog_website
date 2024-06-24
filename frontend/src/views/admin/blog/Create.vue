<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "BlogCreate",
  data(){
    return{
      add_new_blog: {
        category_id: null,
        sub_category_id: null,
        author: '',
        title: '',
        slogan: '',
        description: '',
        date: null,
        image: null,
        status: '',
        tag_ids: []
      },
      imagePreview: '',
    }
  },

  computed: {
    ...mapState({
      tags: state => state.tag.tags,
      subCategories: state => state.sub_category.subCategories,
      categories: state => state.category.categories,
      message: state => state.blog.success_message,
      errors: state => state.blog.errors,
      success_status: state => state.blog.success_status,
      error_status: state => state.blog.error_status
    }),

    filteredSubCategories() {
      return this.subCategories.filter(subCategory => subCategory.category.id === this.add_new_blog.category_id);
    }
  },

  mounted() {
    this.getAllCategory();
    this.getAllSubCategory();
    this.getAllTag();
  },

  methods: {
    ...mapActions({
      getAllCategory: "category/GetAllCategory",
      getAllSubCategory: "sub_category/GetAllSubCategory",
      getAllTag: "tag/GetAllTag"
    }),

    previewNIDImage() {
      const file = this.add_new_blog.image;
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

    addBlog: async function(){
      try {
        let formData = new FormData();

        formData.append('category_id', this.add_new_blog.category_id);
        formData.append('sub_category_id', this.add_new_blog.sub_category_id);
        formData.append('author', this.add_new_blog.author);
        formData.append('title', this.add_new_blog.title);
        formData.append('slogan', this.add_new_blog.slogan);
        formData.append('description', this.add_new_blog.description);
        formData.append('date', this.add_new_blog.date);
        formData.append('image', this.add_new_blog.image);
        formData.append('status', this.add_new_blog.status);

        this.add_new_blog.tag_ids.forEach(item => formData.append('tag_ids[]', item));

        await this.$store.dispatch('blog/StoreBlog', formData).then(() => {

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

            this.add_new_blog = {};
            this.imagePreview = null;

            setTimeout(function () {
              router.push({path: '/blog'});
            },2000)
          }
        })
      }catch (e) {
        if (this.error_status === 422)
        {
          console.log('error');
        }else {
          this.$swal.fire({
            icon: 'error',
            text: 'Oops',
            title: 'Something wen wrong!!!',
          });
        }
      }
    }
  }
}
</script>

<template>
  <div id="create">
    <v-row class="mx-5 mt-5">
      <v-col cols="12">
        <v-row>
          <v-col cols="12" md="12" sm="12" lg="12">
            <v-card>
              <v-card-title><h3>Add Blog</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="addBlog">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="add_new_blog.category_id"
                                :items="categories"
                                item-title="name"
                                item-value="id"
                                label="select Category"
                            ></v-select>
                            <p v-if="errors.category_id" class="error custom_error">{{errors.category_id}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="add_new_blog.sub_category_id"
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
                            v-model="add_new_blog.author"
                            label="Author"
                            persistent-hint
                            variant="outlined"
                        ></v-text-field>
                        <p v-if="errors.author" class="error custom_error">{{errors.author}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-select
                            variant="outlined"
                            v-model="add_new_blog.tag_ids"
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
                                v-model="add_new_blog.title"
                                label="Title"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.title" class="error custom_error">{{errors.title}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="add_new_blog.slogan"
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
                            v-model="add_new_blog.description"
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
                                v-model="add_new_blog.date"
                                label="Date"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.date" class="error custom_error">{{errors.date}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-file-input
                                v-model="add_new_blog.image"
                                label="Image"
                                persistent-hint
                                variant="outlined"
                                @change="previewNIDImage"
                            ></v-file-input>
                            <img v-if="imagePreview" :src="imagePreview" alt="Image Preview" style="width: 100%" />
                            <p v-if="errors.image" class="error custom_error">{{errors.image}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-checkbox
                            v-model="add_new_blog.status"
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
.error{
  color: red;
}
</style>
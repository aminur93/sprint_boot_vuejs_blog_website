<script>
import PermissionMixins from "@/mixins/PermissionMixins";
import {mapState} from "vuex";
import {http} from "@/service/HttpService";

export default {
  name: "BlogIndex",
  mixins: [PermissionMixins],

  data(){
    return{
      totalBlogs: 0,
      blogs: [],
      loading: true,
      options: {},
      search: '',
      headers: [
        { title: 'ID', key: 'id', sortable: false },
        { title: 'Image', key: 'image' },
        { title: 'Category', key: 'category_id' },
        { title: 'Sub-Category', key: 'sub_category_id' },
        { title: 'Author', key: 'author' },
        { title: 'Title', key: 'title' },
        { title: 'Slogan', key: 'slogan' },
        { title: 'Date', key: 'date' },
        { title: 'Tag', key: 'tag' },
        { title: 'Status', key: 'status' },
        { title: 'Actions', key: 'actions', align: 'center', sortable: false },
      ],
    }
  },

  computed: {
    startIndex() {
      let currentPage = this.options.page;
      let itemsPerPage = this.options.itemsPerPage;

      return (currentPage - 1) * itemsPerPage + 1;
    },

    hasDeletePermission() {
      return this.checkPermission('blog-delete');
    },

    ...mapState({
      message: state => state.blog.success_message,
      errors: state => state.blog.errors,
      success_status: state => state.blog.success_status,
      error_status: state => state.blog.error_status
    })
  },

  watch: {
    options: {
      handler () {
        this.getAllBlogs()
      },
      deep: true,
    },

    search: {
      handler () {
        this.getAllBlogs()
      },
    },
  },

  mounted() {
    this.getAllBlogs()
  },

  methods: {
    getAllBlogs(){
      this.loading = true

      const { sortBy, sortDesc, page, itemsPerPage } = this.options

      http().get('http://localhost:8080/api/v1/admin/blog', {
        params: {
          sortBy: sortBy[0],
          sortDesc: sortDesc,
          page: page,
          itemsPerPage: itemsPerPage,
          search: this.search
        }
      }).then((result) => {
        this.blogs = result.data.data;
        this.totalBlogs = result.data.meta.total;
        this.loading = false;
      }).catch((err) => {
        console.log(err);
      })
    },

    calculateIndex(item) {
      return this.startIndex + item;
    },

    deleteBlog: async function(id) {
      try {
        await this.$store.dispatch("blog/DeleteBlog", id).then(() => {
          if (this.success_status === 200) {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: this.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.getAllBlogs();
          }
        })
      } catch (e) {
        if (this.error_status === 403) {
          this.$swal.fire({
            icon: 'error',
            text: 'Permission denied',
          });
        } else {
          this.$swal.fire({
            icon: 'error',
            text: 'Oops',
            title: 'Something wen wrong!!!',
          });
        }
      }
    },

    getImageSrc(image) {
      const src = `${this.$store.state.serverPath}/images/${image}`;
      return src;
    }
  }
}
</script>

<template>
  <div id="index">
    <v-row class="mx-5">

      <v-col cols="12" class="pa-6">

        <v-row wrap>
          <v-col cols="6">
            <h1 :class="['text-subtitle-2', 'text-grey', 'mt-5']">Blog</h1>
          </v-col>
        </v-row>

        <v-row wrap>
          <v-col cols="12">
            <v-card elevation="8">
              <v-row>
                <v-col col="6">
                  <v-card-title :class="['text-subtitle-1']">All Blog Lists</v-card-title>
                </v-col>

                <v-col cols="6">
                  <v-card-actions class="justify-end">
                    <v-btn color="success" @click="navigateWithPermission('blog-create', '/add-blog')">
                      <v-icon small left>mdi-plus</v-icon>
                      <span>Add New</span>
                    </v-btn>
                  </v-card-actions>
                </v-col>
              </v-row>

              <v-divider></v-divider>

              <v-card-text>
                <v-card-title class="d-flex align-center pe-2" style="justify-content: space-between">
                  <h1 :class="['text-subtitle-1', 'text-black']">Blog</h1>

                  <v-spacer></v-spacer>

                  <v-text-field
                      v-model="search"
                      density="compact"
                      label="Search"
                      prepend-inner-icon="mdi-magnify"
                      variant="solo-filled"
                      flat
                      hide-details
                      single-line
                  ></v-text-field>
                </v-card-title>


                <v-data-table-server
                    :headers="headers"
                    :items="blogs"
                    :search="search"
                    v-model:options="options"
                    :items-length="totalBlogs"
                    :loading="loading"
                    item-value="name"
                    class="elevation-4"
                    fixed-header
                >
                  <template v-slot:[`item.id`]="{ index }">
                    <td>{{ calculateIndex(index) }}</td>
                  </template>

                  <template v-slot:[`item.image`] = "{item}">
                    <img :src="getImageSrc(item.image)" alt="" style="width: 100px;height: 100px">
                  </template>

                  <template v-slot:[`item.category_id`] = "{item}">
                    <v-chip color="cyan" label>
                      {{ item.category.name }}
                    </v-chip>
                  </template>

                  <template v-slot:[`item.sub_category_id`] = "{item}">
                    <v-chip color="cyan" label>
                      {{ item.subCategory.name }}
                    </v-chip>
                  </template>

                  <template v-slot:[`item.tag`] = "{item}">
                    <v-chip color="cyan" label v-for="tag in item.tags" :key="tag.id" style="margin-left: 5px">
                      {{ tag.name }}
                    </v-chip>
                  </template>

                  <template v-slot:[`item.status`] = "{item}">
                    <v-chip color="pink" label v-if="item.status == 0">
                      Inactive
                    </v-chip>

                    <v-chip color="cyan" label v-else>
                      Active
                    </v-chip>
                  </template>


                  <template v-slot:[`item.actions`]="{ item }">
                    <v-row align="center" justify="center">
                      <td :class="['mx-2']">
                        <v-btn @click="navigateWithPermission('blog-edit', `/edit-blog/${item.id}`)" color="warning" icon="mdi-pencil" size="x-small"></v-btn>
                      </td>

                      <td v-if="hasDeletePermission">
                        <v-btn color="red" icon="mdi-delete" size="x-small" @click="deleteBlog(item.id)"></v-btn>
                      </td>
                    </v-row>
                  </template>
                </v-data-table-server>

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
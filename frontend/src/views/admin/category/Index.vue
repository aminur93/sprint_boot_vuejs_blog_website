<script>
import PermissionMixins from "@/mixins/PermissionMixins";
import {mapState} from "vuex";
import {http} from "@/service/HttpService";

export default {
  name: "CategoryIndex",
  mixins: [PermissionMixins],

  data(){
    return{
      totalCategories: 0,
      categories: [],
      loading: true,
      options: {},
      search: '',
      headers: [
        { title: 'ID', key: 'id', sortable: false },
        { title: 'Name', key: 'name' },
        { title: 'Description', key: 'description' },
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
      return this.checkPermission('category-delete');
    },

    ...mapState({
      message: state => state.category.success_message,
      errors: state => state.category.errors,
      success_status: state => state.category.success_status,
      error_status: state => state.category.error_status
    })
  },

  watch: {
    options: {
      handler () {
        this.getAllCategories()
      },
      deep: true,
    },

    search: {
      handler () {
        this.getAllCategories()
      },
    },
  },

  mounted() {
    this.getAllCategories();
  },

  methods: {
    getAllCategories(){
      this.loading = true

      const { sortBy, sortDesc, page, itemsPerPage } = this.options

      http().get('http://localhost:8080/api/v1/admin/category', {
        params: {
          sortBy: sortBy[0],
          sortDesc: sortDesc,
          page: page,
          itemsPerPage: itemsPerPage,
          search: this.search
        }
      }).then((result) => {
        this.categories = result.data.data;
        this.totalCategories = result.data.meta.total;
        this.loading = false;
      }).catch((err) => {
        console.log(err);
      })
    },

    calculateIndex(item) {
      return this.startIndex + item;
    },

    deleteCategory: async function(id) {
      try {
        await this.$store.dispatch("category/DeleteCategory", id).then(() => {
          if (this.success_status === 200) {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: this.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.getAllCategories();
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
    }
  }
}
</script>

<template>
    <div id="category_list">
      <v-row class="mx-5">

        <v-col cols="12" class="pa-6">

          <v-row wrap>
            <v-col cols="6">
              <h1 :class="['text-subtitle-2', 'text-grey', 'mt-5']">Category</h1>
            </v-col>
          </v-row>

          <v-row wrap>
            <v-col cols="12">
              <v-card elevation="8">
                <v-row>
                  <v-col col="6">
                    <v-card-title :class="['text-subtitle-1']">All Category Lists</v-card-title>
                  </v-col>

                  <v-col cols="6">
                    <v-card-actions class="justify-end">
                      <v-btn color="success" @click="navigateWithPermission('category-create', '/add-category')">
                        <v-icon small left>mdi-plus</v-icon>
                        <span>Add New</span>
                      </v-btn>
                    </v-card-actions>
                  </v-col>
                </v-row>

                <v-divider></v-divider>

                <v-card-text>
                  <v-card-title class="d-flex align-center pe-2" style="justify-content: space-between">
                    <h1 :class="['text-subtitle-1', 'text-black']">Category</h1>

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
                      :items="categories"
                      :search="search"
                      v-model:options="options"
                      :items-length="totalCategories"
                      :loading="loading"
                      item-value="name"
                      class="elevation-4"
                      fixed-header
                  >
                    <template v-slot:[`item.id`]="{ index }">
                      <td>{{ calculateIndex(index) }}</td>
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
                          <v-btn @click="navigateWithPermission('category-edit', `/edit-category/${item.id}`)" color="warning" icon="mdi-pencil" size="x-small"></v-btn>
                        </td>

                        <td v-if="hasDeletePermission">
                          <v-btn color="red" icon="mdi-delete" size="x-small" @click="deleteCategory(item.id)"></v-btn>
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
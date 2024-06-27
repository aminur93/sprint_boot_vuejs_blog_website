<script>
import PermissionMixins from "@/mixins/PermissionMixins";
import {mapState} from "vuex";
import {http} from "@/service/HttpService";

export default {
  name: "MenuIndex",
  mixins: [PermissionMixins],

  data(){
    return{
      totalMenus: 0,
      menus: [],
      loading: true,
      options: {},
      search: '',
      headers: [
        { title: 'ID', key: 'id', sortable: false },
        { title: 'Permission', key: 'permission_id' },
        { title: 'Title', key: 'title' },
        { title: 'Icon', key: 'icon' },
        { title: 'Route', key: 'route' },
        { title: 'Header Menu', key: 'header_menu' },
        { title: 'Sidebar Menu', key: 'sidebar_menu' },
        { title: 'DropDown', key: 'dropdown' },
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

    hasDeleteMenu() {
      return this.checkPermission('menu-delete');
    },

    ...mapState({
      message: state => state.menu.success_message,
      errors: state => state.menu.errors,
      success_status: state => state.menu.success_status,
      error_status: state => state.menu.error_status
    })
  },

  watch: {
    options: {
      handler () {
        this.getAllMenus()
      },
      deep: true,
    },

    search: {
      handler () {
        this.getAllMenus()
      },
    },
  },

  mounted() {
    this.getAllMenus();
  },

  methods: {
    getAllMenus(){
      this.loading = true

      const { sortBy, sortDesc, page, itemsPerPage } = this.options

      http().get('http://localhost:8080/api/v1/admin/menu', {
        params: {
          sortBy: sortBy[0],
          sortDesc: sortDesc,
          page: page,
          itemsPerPage: itemsPerPage,
          search: this.search
        }
      }).then((result) => {
        this.menus = result.data.data;
        this.totalMenus = result.data.meta.total;
        this.loading = false;
      }).catch((err) => {
        console.log(err);
      })
    },

    calculateIndex(item) {
      return this.startIndex + item;
    },

    deleteMenu: async function(id) {
      try {
        await this.$store.dispatch("menu/DeleteMenu", id).then(() => {
          if (this.success_status === 200) {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: this.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.getAllMenus();
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
  <div id="index">
    <v-row class="mx-5">

      <v-col cols="12" class="pa-6">

        <v-row wrap>
          <v-col cols="6">
            <h1 :class="['text-subtitle-2', 'text-grey', 'mt-5']">Menu</h1>
          </v-col>
        </v-row>

        <v-row wrap>
          <v-col cols="12">
            <v-card elevation="8">
              <v-row>
                <v-col col="6">
                  <v-card-title :class="['text-subtitle-1']">All Menu Lists</v-card-title>
                </v-col>

                <v-col cols="6">
                  <v-card-actions class="justify-end">
                    <v-btn color="success" @click="navigateWithPermission('menu-create', '/add-menu')">
                      <v-icon small left>mdi-plus</v-icon>
                      <span>Add New</span>
                    </v-btn>
                  </v-card-actions>
                </v-col>
              </v-row>

              <v-divider></v-divider>

              <v-card-text>
                <v-card-title class="d-flex align-center pe-2" style="justify-content: space-between">
                  <h1 :class="['text-subtitle-1', 'text-black']">Menu</h1>

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
                    :items="menus"
                    :search="search"
                    v-model:options="options"
                    :items-length="totalMenus"
                    :loading="loading"
                    item-value="name"
                    class="elevation-4"
                    fixed-header
                >
                  <template v-slot:[`item.id`]="{ index }">
                    <td>{{ calculateIndex(index) }}</td>
                  </template>

                  <template v-slot:[`item.permission_id`]="{ item }">
                    <td v-if="item.permission !== null">
                      {{ item.permission.name }}
                    </td>
                    <td v-else>
                      <span style="color: #39ac31">No Permission for Parent</span>
                    </td>
                  </template>

                  <template v-slot:[`item.route`]="{ item }">
                    <div v-if="item.route !== null">{{ item.route }}</div>
                    <div v-else style="color: #39ac31">No Route For Parent</div>
                  </template>

                  <template v-slot:[`item.header_menu`]="{ item }">
                    <td>
                      <v-chip color="cyan" label>
                        {{ item.headerMenu}}
                      </v-chip>
                    </td>
                  </template>

                  <template v-slot:[`item.sidebar_menu`]="{ item }">
                    <td>
                      <v-chip color="cyan" label>
                        {{ item.sidebarMenu}}
                      </v-chip>
                    </td>
                  </template>

                  <template v-slot:[`item.dropdown`]="{ item }">
                    <td>
                      <v-chip color="cyan" label>
                        {{ item.dropdown}}
                      </v-chip>
                    </td>
                  </template>


                  <template v-slot:[`item.actions`]="{ item }">
                    <v-row align="center" justify="center">
                      <td :class="['mx-2']">
                        <v-btn @click="navigateWithPermission('menu-edit', `/edit-menu/${item.id}`)" color="warning" icon="mdi-pencil" size="x-small"></v-btn>
                      </td>

                      <td v-if="hasDeleteMenu">
                        <v-btn color="red" icon="mdi-delete" size="x-small" @click="deleteMenu(item.id)"></v-btn>
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
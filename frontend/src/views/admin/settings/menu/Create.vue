<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "MenuCreate",

  data(){
    return{
      add_menu: {
        permission_id: null,
        title: '',
        icon: '',
        route: '',
        header_menu: '',
        sidebar_menu: '',
        dropdown: ''
      }
    }
  },

  computed: {
    ...mapState({
      permissions: state => state.permission.permissions,
      message: state => state.menu.success_message,
      errors: state => state.menu.errors,
      success_status: state => state.menu.success_status,
      error_status: state => state.menu.error_status
    }),

    SortedPermission() {
      return this.permissions
          .filter(item => item.name.includes('list'))
          .sort((a, b) => a.name.localeCompare(b.name))
          .map(item => item.name);
    }
  },

  mounted() {
    this.getAllPermission();
  },

  methods: {
    ...mapActions({
      getAllPermission: "permission/GetAllPermission"
    }),

    addMenu: async function(){
      try {
        let formData = new FormData();

        formData.append('permission_id', this.add_menu.permission_id);
        formData.append('title', this.add_menu.title);
        formData.append('icon', this.add_menu.icon);
        formData.append('route', this.add_menu.route);
        formData.append('header_menu', this.add_menu.header_menu);
        formData.append('sidebar_menu', this.add_menu.sidebar_menu);
        formData.append('dropdown', this.add_menu.dropdown);

        await this.$store.dispatch('menu/StoreMenu', formData).then(() => {

          if (this.success_status === 201)
          {
            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: this.message,
              showConfirmButton: false,
              timer: 1500
            });

            this.add_menu = {};

            setTimeout(function () {
              router.push({path: '/menu'});
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
              <v-card-title><h3>Add Menu</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="addMenu">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="add_menu.permission_id"
                                :items="SortedPermission"
                                item-title="name"
                                item-value="id"
                                label="select Category"
                            ></v-select>
                            <p v-if="errors.permission_id" class="error custom_error">{{errors.permission_id}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="add_menu.title"
                                label="Title"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.title" class="error custom_error">{{errors.title}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="add_menu.icon"
                                label="Icon"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.icon" class="error custom_error">{{errors.icon}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="add_menu.route"
                                label="Route"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.route" class="error custom_error">{{errors.route}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>

                          <v-col cols="12" md="4" sm="4" lg="4">
                            <v-checkbox
                                v-model="add_menu.header_menu"
                                label="Header Menu"
                            ></v-checkbox>
                            <p v-if="errors.header_menu" class="error custom_error">{{errors.header_menu}}</p>
                          </v-col>

                          <v-col cols="12" md="4" sm="4" lg="4">
                            <v-checkbox
                                v-model="add_menu.sidebar_menu"
                                label="Sidebar Menu"
                            ></v-checkbox>
                            <p v-if="errors.sidebar_menu" class="error custom_error">{{errors.sidebar_menu}}</p>
                          </v-col>

                          <v-col cols="12" md="4" sm="4" lg="4">
                            <v-checkbox
                                v-model="add_menu.dropdown"
                                label="DropDown"
                            ></v-checkbox>
                            <p v-if="errors.dropdown" class="error custom_error">{{errors.dropdown}}</p>
                          </v-col>

                        </v-row>
                      </v-col>

                      <v-row wrap>
                        <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                          <v-btn
                              flat
                              color="primary"
                              class="custom-btn mr-2"
                              router
                              to="/menu"
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
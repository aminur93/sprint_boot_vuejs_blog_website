<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "MenuEdit",
  data(){
    return{

    }
  },

  computed: {
    ...mapState({
      editMenu: state => state.menu.menu,
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
    this.getEditMenu(this.$route.params.id);
  },

  methods: {
    ...mapActions({
      getAllPermission: "permission/GetAllPermission",
      getEditMenu: "menu/GetSingleMenu"
    }),

    updateMenu: async function(){
      try {
        let id = this.$route.params.id;
        let formData = new FormData();

        //If permission has been updated and has a valid id
        if (this.editMenu.permission && this.editMenu.permission.id) {

          formData.append('permission_id', this.editMenu.permission.id);

        } else if (this.editMenu.permission) {
          // If no permission update but existing permission_id is available
          formData.append('permission_id', "null");

        } else if (this.editMenu.permission_id) {

          formData.append('permission_id', this.editMenu.permission_id);

        }else {
          // Handle the case where permission_id is not available
          this.errors.permission_id = ['Permission is required'];
        }

        //formData.append('permission_id', "null");
        formData.append('title', this.editMenu.title);
        formData.append('icon', this.editMenu.icon);
        formData.append('route', this.editMenu.route);
        formData.append('header_menu', this.editMenu.headerMenu);
        formData.append('sidebar_menu', this.editMenu.sidebarMenu);
        formData.append('dropdown', this.editMenu.dropdown);

        await this.$store.dispatch('menu/UpdateMenu', {id:id, data:formData}).then(() => {

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

            this.getEditMenu(id);

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
  <div id="edit">
    <v-row class="mx-5 mt-5">
      <v-col cols="12">
        <v-row>
          <v-col cols="12" md="12" sm="12" lg="12">
            <v-card>
              <v-card-title><h3>Edit Menu</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="updateMenu">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="editMenu.permission"
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
                                v-model="editMenu.title"
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
                                v-model="editMenu.icon"
                                label="Icon"
                                persistent-hint
                                variant="outlined"
                            ></v-text-field>
                            <p v-if="errors.icon" class="error custom_error">{{errors.icon}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-text-field
                                type="text"
                                v-model="editMenu.route"
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
                                v-model="editMenu.headerMenu"
                                label="Header Menu"
                            ></v-checkbox>
                            <p v-if="errors.headerMenu" class="error custom_error">{{errors.headerMenu}}</p>
                          </v-col>

                          <v-col cols="12" md="4" sm="4" lg="4">
                            <v-checkbox
                                v-model="editMenu.sidebarMenu"
                                label="Sidebar Menu"
                            ></v-checkbox>
                            <p v-if="errors.sidebarMenu" class="error custom_error">{{errors.sidebarMenu}}</p>
                          </v-col>

                          <v-col cols="12" md="4" sm="4" lg="4">
                            <v-checkbox
                                v-model="editMenu.dropdown"
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
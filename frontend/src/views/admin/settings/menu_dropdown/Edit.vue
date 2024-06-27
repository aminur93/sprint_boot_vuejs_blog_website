<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "MenuDropDownEdit",

  data(){
    return{

    }
  },

  computed: {
    ...mapState({
      editMenu: state => state.menu_dropdown.menuDopDown,
      menus: state => state.menu.menus,
      permissions: state => state.permission.permissions,
      message: state => state.menu_dropdown.success_message,
      errors: state => state.menu_dropdown.errors,
      success_status: state => state.menu_dropdown.success_status,
      error_status: state => state.menu_dropdown.error_status
    }),

    SortedPermission() {
      return this.permissions
          .filter(item => item.name.includes('list'))
          .sort((a, b) => a.name.localeCompare(b.name))
          .map(item => ({ name: item.name, id: item.id }));
    },

    SortedMenu(){
      return this.menus
          .filter(item => item.dropdown === true)
          .map(item => ({ title: item.title, id: item.id }));
    }
  },

  mounted() {
    this.getAllPermission();
    this.getAllMenu();
    this.getEditMenu(this.$route.params.id);
  },

  methods: {
    ...mapActions({
      getEditMenu: "menu_dropdown/GetSingleMenuDropDown",
      getAllMenu: "menu/GetAllMenu",
      getAllPermission: "permission/GetAllPermission"
    }),

    updateMenuDropDown: async function(){
      try {
        let id = this.$route.params.id;
        let formData = new FormData();

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

        if (this.editMenu.menu && this.editMenu.menu.id) {

          formData.append('menu_id', this.editMenu.menu.id);

        } else if (this.editMenu.menu) {
          // If no permission update but existing permission_id is available
          formData.append('menu_id', "null");

        } else if (this.editMenu.menu_id) {

          formData.append('menu_id', this.editMenu.menu_id);

        }else {
          // Handle the case where permission_id is not available
          this.errors.menu_id = ['Menu is required'];
        }


        formData.append('title', this.editMenu.title);
        formData.append('icon', this.editMenu.icon);
        formData.append('route', this.editMenu.route);

        await this.$store.dispatch('menu_dropdown/UpdateMenuDropDown', {id:id, data:formData}).then(() => {

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
              router.push({path: '/menu-dropdown'});
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
                <v-form v-on:submit.prevent="updateMenuDropDown">
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
                                label="select Permission"
                            ></v-select>
                            <p v-if="errors.permission_id" class="error custom_error">{{errors.permission_id}}</p>
                          </v-col>

                          <v-col cols="12" md="6" sm="6" lg="6">
                            <v-select
                                variant="outlined"
                                v-model="editMenu.menu"
                                :items="SortedMenu"
                                item-title="title"
                                item-value="id"
                                label="select Menu"
                            ></v-select>
                            <p v-if="errors.menu_id" class="error custom_error">{{errors.menu_id}}</p>
                          </v-col>
                        </v-row>
                      </v-col>

                      <v-col cols="12" class="d-flex">
                        <v-row wrap>
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
                        </v-row>
                      </v-col>

                      <v-col cols="12" md="12" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="editMenu.route"
                            label="Route"
                            persistent-hint
                            variant="outlined"
                        ></v-text-field>
                        <p v-if="errors.route" class="error custom_error">{{errors.route}}</p>
                      </v-col>

                      <v-row wrap>
                        <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                          <v-btn
                              flat
                              color="primary"
                              class="custom-btn mr-2"
                              router
                              to="/menu-dropdown"
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
<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "UserEdit",

  data(){
    return{
      password: ''
    }
  },

  computed: {
    ...mapState({
      user: state => state.user.user,
      roles: state => state.role.roles,
      message: state => state.user.success_message,
      errors: state => state.user.errors,
      success_status: state => state.user.success_status,
      error_status: state => state.user.error_status
    })
  },

  mounted() {
    this.getAllRole();
    this.getEditUser(this.$route.params.id);
  },

  methods: {
    ...mapActions({
      getAllRole: "role/GetAllRole",
      getEditUser: "user/GetSingleUser"
    }),

    updateUser: async function(){
      try {
        let id = this.$route.params.id;
        let formData = new FormData();

        formData.append('name', this.user.name);
        formData.append('email', this.user.email);
        formData.append('password', this.password);

        // If category has been updated and has a valid id
        if (this.user.roles && this.user.roles.id) {
          formData.append('role', this.user.roles.id);
        } else if (this.user.role) {
          // If no category update but existing category_id is available
          formData.append('role', this.user.role);
        } else {
          // Handle the case where category_id is not available
          this.errors.role = ['role is required'];
        }

        //formData.append('role', this.user.roles.id);

        await this.$store.dispatch("user/UpdateUser", {id:id, data:formData}).then(() => {
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

            this.getEditUser(id);

            setTimeout(function () {
              router.push({path: '/user'});
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
              <v-card-title><h3>Edit User</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="updateUser">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="user.name"
                            label="Name"
                            persistent-hint
                            variant="outlined"
                            required
                        ></v-text-field>
                        <p v-if="errors.name" class="error custom_error">{{errors.name}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="user.email"
                            label="Email"
                            persistent-hint
                            variant="outlined"
                            required
                        ></v-text-field>
                        <p v-if="errors.email" class="error custom_error">{{errors.email}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="password"
                            v-model="password"
                            label="Password"
                            persistent-hint
                            variant="outlined"
                            required
                        ></v-text-field>
                        <p v-if="errors.password" class="error custom_error">{{errors.password}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-select
                            variant="outlined"
                            v-model="user.roles"
                            :items="roles"
                            item-title="name"
                            item-value="id"
                            label="select Role"
                        ></v-select>
                        <p v-if="errors.role" class="error custom_error">{{errors.role}}</p>
                      </v-col>

                      <v-row wrap>
                        <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                          <v-btn
                              flat
                              color="primary"
                              class="custom-btn mr-2"
                              router
                              to="/user"
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
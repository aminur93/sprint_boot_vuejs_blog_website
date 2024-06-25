<script>
import { mapActions, mapState } from "vuex";
import router from "@/router";

export default {
  name: "RoleEdit",
  data() {
    return {
      selectedPermissionIds: [] // Initialize selected permissions as an empty array
    };
  },
  computed: {
    ...mapState({
      editSingleRole: state => state.role.role,
      permissions: state => state.permission.permissions,
      message: state => state.role.success_message,
      errors: state => state.role.errors,
      success_status: state => state.role.success_status,
      error_status: state => state.role.error_status
    }),

    // Computed property to map selectedPermissionIds to match checkboxes' v-model
    permissionIds: {
      get() {
        return this.selectedPermissionIds;
      },
      set(value) {
        this.selectedPermissionIds = value;
      }
    }
  },

  watch: {
    // Watch for changes in editSingleRole to update selectedPermissionIds if needed
    editSingleRole: {
      handler(newVal) {
        // Update selectedPermissionIds based on newVal.permissions if needed
        this.selectedPermissionIds = newVal.permissions.map(permission => permission.id);
        // Adjust based on your actual data structure and requirements
      },
      deep: true // Enable deep watching for nested objects if necessary
    }
  },

  mounted() {
    // Fetch initial data
    this.getAllPermission();
    this.getEditRole(this.$route.params.id);
  },

  methods: {
    ...mapActions({
      getEditRole: "role/GetSingleRole",
      getAllPermission: "permission/GetAllPermission",
      updateRoleInStore: "role/UpdateRole" // Example action name, adjust as needed
    }),

    async updateRole() {
      try {
        const id = this.$route.params.id;
        const formData = new FormData();

        formData.append("name", this.editSingleRole.name);
        this.selectedPermissionIds.forEach(item => formData.append("permissions[]", item));

        await this.updateRoleInStore({ id, data: formData });

        // Handle success feedback to the user
        this.$swal.fire({
          toast: true,
          position: 'top-end',
          icon: 'success',
          title: this.message,
          showConfirmButton: false,
          timer: 1500
        });

        // Optionally fetch updated role data after submission
        await this.getEditRole(id);

        // Redirect to role list page after a delay
        setTimeout(() => {
          router.push({ path: '/role' });
        }, 2000);
      } catch (error) {
        console.error("Error updating role:", error);
      }
    }
  }
};
</script>

<template>
  <div id="edit">
    <v-row class="mx-5 mt-5">
      <v-col cols="12">
        <v-row>
          <v-col cols="12" md="12" sm="12" lg="12">
            <v-card>
              <v-card-title><h3>Edit Role</h3></v-card-title>
              <v-divider></v-divider>
              <v-card-text>
                <v-form v-on:submit.prevent="updateRole">
                  <v-col cols="12">
                    <v-row wrap>
                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="editSingleRole.name"
                            label="Name"
                            persistent-hint
                            variant="outlined"
                        ></v-text-field>
                        <p v-if="errors.name" class="error custom_error">{{ errors.name }}</p>
                      </v-col>

                      <strong :class="['ml-3']">Permission: </strong>
                      <v-divider :class="['mt-5', 'ml-3', 'mr-2']"></v-divider>

                      <v-row>
                        <v-col cols="3" v-for="permission in permissions" :key="permission.id">
                          <v-checkbox
                              v-model="permissionIds"
                              color="green"
                              :value="permission.id"
                              :label="permission.name"
                              hide-details
                          ></v-checkbox>
                        </v-col>
                      </v-row>

                      <v-row wrap>
                        <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                          <v-btn
                              flat
                              color="primary"
                              class="custom-btn mr-2"
                              router
                              to="/permission"
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

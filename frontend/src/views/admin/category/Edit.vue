<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "CategoryEdit",

  data(){
    return{

    }
  },

  computed: {
    ...mapState({
      editCategory: state => state.category.category,
      message: state => state.category.success_message,
      errors: state => state.category.errors,
      success_status: state => state.category.success_status,
      error_status: state => state.category.error_status
    })
  },

  mounted() {
    this.getEditCategory(this.$route.params.id);
  },

  methods: {

    ...mapActions({
      getEditCategory: "category/GetSingleCategory"
    }),

    updateCategory: async function(){

      try {

        let id = this.$route.params.id;

        let formData = new FormData();

        formData.append('name', this.editCategory.name);
        formData.append('description', this.editCategory.description);
        formData.append('status', this.editCategory.status);

        await this.$store.dispatch('category/UpdateCategory', {id:id, data:formData}).then(() => {

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

            this.getEditCategory(id);

            setTimeout(function () {
              router.push({path: '/category'});
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
                <v-card-title><h3>Edit Category</h3></v-card-title>

                <v-divider></v-divider>

                <v-card-text>
                  <v-form v-on:submit.prevent="updateCategory">
                    <v-col cols="12">
                      <v-row wrap>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-text-field
                              type="text"
                              v-model="editCategory.name"
                              label="Name"
                              persistent-hint
                              variant="outlined"
                              required
                          ></v-text-field>
                          <p v-if="errors.name" class="error custom_error">{{errors.name[0]}}</p>
                        </v-col>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-textarea
                              v-model="editCategory.description"
                              label="Description"
                              variant="outlined"
                          ></v-textarea>
                          <p v-if="errors.description" class="error custom_error">{{errors.description[0]}}</p>
                        </v-col>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-checkbox
                              v-model="editCategory.status"
                              label="Status"
                          ></v-checkbox>
                          <p v-if="errors.status" class="error custom_error">{{errors.status[0]}}</p>
                        </v-col>

                        <v-row wrap>
                          <v-col cols="12" md="8" sm="12" lg="12" :class="['d-flex', 'justify-end']">
                            <v-btn
                                flat
                                color="primary"
                                class="custom-btn mr-2"
                                router
                                to="/category"
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
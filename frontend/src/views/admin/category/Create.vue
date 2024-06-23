<script>
import {mapState} from "vuex";
import router from "@/router";

export default {
  name: "CategoryCreate",

  data(){
    return{
      add_category: {
        name: '',
        description: '',
        status: ''
      }
    }
  },

  computed: {
    ...mapState({
      message: state => state.category.success_message,
      errors: state => state.category.errors,
      success_status: state => state.category.success_status,
      error_status: state => state.category.error_status
    })
  },

  methods: {
    addCategory: async function(){
      
      try {
        let formData = new FormData();

        formData.append('name', this.add_category.name);
        formData.append('description', this.add_category.description);
        formData.append('status', this.add_category.status);

        await this.$store.dispatch('category/StoreCategory', formData).then(() => {

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

            this.add_category = {};

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
    <div id="create">
      <v-row class="mx-5 mt-5">
        <v-col cols="12">
          <v-row>
            <v-col cols="12" md="12" sm="12" lg="12">
              <v-card>
                <v-card-title><h3>Add Category</h3></v-card-title>

                <v-divider></v-divider>

                <v-card-text>
                  <v-form v-on:submit.prevent="addCategory">
                    <v-col cols="12">
                      <v-row wrap>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-text-field
                              type="text"
                              v-model="add_category.name"
                              label="Name"
                              persistent-hint
                              variant="outlined"
                              required
                          ></v-text-field>
                          <p v-if="errors.name" class="error custom_error">{{errors.name}}</p>
                        </v-col>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-textarea
                              v-model="add_category.description"
                              label="Description"
                              variant="outlined"
                          ></v-textarea>
                          <p v-if="errors.description" class="error custom_error">{{errors.description}}</p>
                        </v-col>

                        <v-col cols="12" md="8" sm="12" lg="12">
                          <v-checkbox
                              v-model="add_category.status"
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
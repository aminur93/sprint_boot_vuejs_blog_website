<script>
import {mapActions, mapState} from "vuex";
import router from "@/router";

export default {
  name: "SubCategoryEdit",
  data(){
    return{

    }
  },

  computed: {
    ...mapState({
      editSubCategory: state => state.sub_category.subCategory,
      categories: state => state.category.categories,
      message: state => state.sub_category.success_message,
      errors: state => state.sub_category.errors,
      success_status: state => state.sub_category.success_status,
      error_status: state => state.sub_category.error_status
    })
  },

  mounted() {
    this.getAllCategory();
    this.getEditSubCategory(this.$route.params.id);
  },

  methods: {
    ...mapActions({
      getAllCategory: "category/GetAllCategory",
      getEditSubCategory: "sub_category/GetSingleSubCategory"
    }),

    updateSubCategory: async function(){
      console.log(this.editSubCategory.category);
      try {
        let id = this.$route.params.id;

        let formData = new FormData();

        // If category has been updated and has a valid id
        if (this.editSubCategory.category && this.editSubCategory.category.id) {
          formData.append('category_id', this.editSubCategory.category.id);
        } else if (this.editSubCategory.category_id) {
          // If no category update but existing category_id is available
          formData.append('category_id', this.editSubCategory.category_id);
        } else {
          // Handle the case where category_id is not available
          this.errors.category_id = ['Category is required'];
        }

        formData.append('name', this.editSubCategory.name);
        formData.append('description', this.editSubCategory.description);
        formData.append('status', this.editSubCategory.status);


        await this.$store.dispatch('sub_category/UpdateSubCategory', {id:id, data:formData}).then(() => {

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

            this.getEditSubCategory(id);

            setTimeout(function () {
              router.push({path: '/sub-category'});
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
              <v-card-title><h3>Edit Sub-Category</h3></v-card-title>

              <v-divider></v-divider>

              <v-card-text>
                <v-form v-on:submit.prevent="updateSubCategory">
                  <v-col cols="12">
                    <v-row wrap>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-select
                            variant="outlined"
                            :items="categories"
                            item-title="name"
                            item-value="id"
                            v-model="editSubCategory.category"
                            label="select Category"
                        ></v-select>
                        <p v-if="errors.category_id" class="error custom_error">{{errors.category_id[0]}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-text-field
                            type="text"
                            v-model="editSubCategory.name"
                            label="Name"
                            persistent-hint
                            variant="outlined"
                            required
                        ></v-text-field>
                        <p v-if="errors.name" class="error custom_error">{{errors.name[0]}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-textarea
                            v-model="editSubCategory.description"
                            label="Description"
                            variant="outlined"
                        ></v-textarea>
                        <p v-if="errors.description" class="error custom_error">{{errors.description[0]}}</p>
                      </v-col>

                      <v-col cols="12" md="8" sm="12" lg="12">
                        <v-checkbox
                            v-model="editSubCategory.status"
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
                              to="/sub-category"
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
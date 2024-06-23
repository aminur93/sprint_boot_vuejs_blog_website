/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    subCategories: [],
    subCategory: {},
    success_message: "",
    errors: {},
    error_message: "",
    error_status: "",
    success_status: "",
}

/* -------------------------------------------------------------------------- */
/*                              Mutations Define                              */
/* -------------------------------------------------------------------------- */
const mutations = {
    GET_ALL_SUB_CATEGORY: (state, data) => {
        state.subCategories = data;
    },

    STORE_SUB_CATEGORY: (state, data) => {
        if (state.subCategories.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_SUB_CATEGORY: (state, data) => {
        state.subCategory = data;
    },

    UPDATE_SUB_CATEGORY: (state, data) => {
        if (state.subCategories.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_SUB_CATEGORY: (state, {id, data}) => {
        if (id)
        {
            state.subCategories = state.subCategories.filter(item => {
                return item.id !== id;
            })

            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    SET_ERROR(state, { errors, errorStatus }) {
        state.errors = errors;
        state.error_status = errorStatus;
    }
}

/* -------------------------------------------------------------------------- */
/*                               Actions Define                               */
/* -------------------------------------------------------------------------- */
const actions = {
    /*get all sub-category start*/
    async GetAllSubCategory({ commit }) {
        try {
            const result = await http().get("v1/admin/sub-category/all-subCategories");
            commit("GET_ALL_SUB_CATEGORY", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err; // Re-throw the error to propagate it to the caller
        }
    },
    /*get all sub-category end*/

    /*store sub-category start*/
    StoreSubCategory: ({commit}, data) => {
        return http()
            .post("v1/admin/sub-category", data)
            .then((result) => {
                commit("STORE_SUB_CATEGORY", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*store sub-category end*/

    /*get single sub-category start*/
    GetSingleSubCategory: ({commit}, id) => {
        return http()
            .get(`v1/admin/sub-category/${id}`)
            .then((result) => {
                commit("GET_SINGLE_SUB_CATEGORY", result.data.data);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*get single sub-category end*/

    /*update sub-category start*/
    UpdateSubCategory: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/sub-category/${id}`, data)
            .then((result) => {
                commit("UPDATE_SUB_CATEGORY", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*update sub-category end*/

    /*destroy sub-category start*/
    DeleteSubCategory: ({commit}, id) => {
        return http()
            .delete(`v1/admin/sub-category/${id}`)
            .then((result) => {
                commit("DELETE_SUB_CATEGORY", {id:id, data:result});
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*destroy sub-category end*/
}

/* -------------------------------------------------------------------------- */
/*                               Getters Define                               */
/* -------------------------------------------------------------------------- */
const getters = {}

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
}
/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    categories: [],
    category: {},
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
    GET_ALL_CATEGORY: (state, data) => {
        state.categories = data;
    },

    STORE_CATEGORY: (state, data) => {
        if (state.categories.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_CATEGORY: (state, data) => {
        state.category = data;
    },

    UPDATE_CATEGORY: (state, data) => {
        if (state.categories.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_CATEGORY: (state, {id, data}) => {
        if (id)
        {
            state.authors = state.categories.filter(item => {
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
    /*get all category start*/
    async GetAllCategory({ commit }) {
        try {
            const result = await http().get("v1/admin/category/all-categories");
            commit("STORE_CATEGORY", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err; // Re-throw the error to propagate it to the caller
        }
    },
    /*get all category end*/

    /*store category start*/
    StoreCategory: ({commit}, data) => {
        return http()
            .post("v1/admin/category", data)
            .then((result) => {
                commit("STORE_CATEGORY", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*store category end*/

    /*get single category start*/
    GetSingleCategory: ({commit}, id) => {
        return http()
            .get(`v1/admin/category/${id}`)
            .then((result) => {
                commit("GET_SINGLE_CATEGORY", result.data.data);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*get single category end*/

    /*update category start*/
    UpdateCategory: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/category/${id}`, data)
            .then((result) => {
                commit("UPDATE_CATEGORY", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*update category end*/

    /*destroy category start*/
    DeleteCategory: ({commit}, id) => {
        return http()
            .delete(`v1/admin/category/${id}`)
            .then((result) => {
                commit("DELETE_CATEGORY", {id:id, data:result});
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*destroy category end*/
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
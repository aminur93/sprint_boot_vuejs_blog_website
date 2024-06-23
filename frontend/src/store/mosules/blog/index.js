/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http, httpFile} from "@/service/HttpService";

const state = {
    blogs: [],
    blog: {},
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
    GET_ALL_BLOG: (state, data) => {
        state.blogs = data;
    },

    STORE_BLOG: (state, data) => {
        if (state.blogs.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_BLOG: (state, data) => {
        state.blog = data;
    },

    UPDATE_BLOG: (state, data) => {
        if (state.blogs.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_BLOG: (state, {id, data}) => {
        if (id)
        {
            state.blogs = state.blogs.filter(item => {
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
    async GetAllBlog({ commit }) {
        try {
            const result = await http().get("v1/admin/blog/all-blogs");
            commit("GET_ALL_BLOG", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all category end*/

    /*store category start*/
    StoreBlog: ({commit}, data) => {
        return httpFile()
            .post("v1/admin/blog", data)
            .then((result) => {
                commit("STORE_BLOG", result);
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
    GetSingleBlog: ({commit}, id) => {
        return http()
            .get(`v1/admin/blog/${id}`)
            .then((result) => {
                commit("GET_SINGLE_BLOG", result.data.data);
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
    UpdateBlog: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/blog/${id}`, data)
            .then((result) => {
                commit("UPDATE_BLOG", result);
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
    DeleteBlog: ({commit}, id) => {
        return http()
            .delete(`v1/admin/blog/${id}`)
            .then((result) => {
                commit("DELETE_BLOG", {id:id, data:result});
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
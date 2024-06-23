/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    tags: [],
    tag: {},
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
    GET_ALL_TAG: (state, data) => {
        state.tags = data;
    },

    STORE_TAG: (state, data) => {
        if (state.tags.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
            console.log(state.success_status);
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_TAG: (state, data) => {
        state.tag = data;
    },

    UPDATE_TAG: (state, data) => {
        if (state.tags.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_TAG: (state, {id, data}) => {
        if (id)
        {
            state.tags = state.tags.filter(item => {
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
    /*get all tag start*/
    async GetAllTag({ commit }) {
        try {
            const result = await http().get("v1/admin/tag/all-tags");
            commit("GET_ALL_TAG", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StoreTag: ({commit}, data) => {
        return http()
            .post("v1/admin/tag", data)
            .then((result) => {
                commit("STORE_TAG", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*store tag end*/

    /*get single tag start*/
    GetSingleTag: ({commit}, id) => {
        return http()
            .get(`v1/admin/tag/${id}`)
            .then((result) => {
                commit("GET_SINGLE_TAG", result.data.data);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*get single tag end*/

    /*update tag start*/
    UpdateTag: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/tag/${id}`, data)
            .then((result) => {
                commit("UPDATE_TAG", result);
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*update tag end*/

    /*destroy tag start*/
    DeleteTag: ({commit}, id) => {
        return http()
            .delete(`v1/admin/tag/${id}`)
            .then((result) => {
                commit("DELETE_TAG", {id:id, data:result});
            })
            .catch((err) => {
                const errors = err.response.data.errors;
                const errorStatus = err.response.status;
                commit("SET_ERROR", { errors, errorStatus });
                throw err;
            })
    },
    /*destroy tag end*/
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
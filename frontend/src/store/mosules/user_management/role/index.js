/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    roles: [],
    role: {},
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
    GET_ALL_ROLE: (state, data) => {
        state.roles = data;
    },

    STORE_ROLE: (state, data) => {
        if (state.roles.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;

        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_ROLE: (state, data) => {
        state.role = data;
    },

    UPDATE_ROLE: (state, data) => {
        if (state.roles.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_ROLE: (state, {id, data}) => {
        if (id)
        {
            state.roles = state.roles.filter(item => {
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
    async GetAllRole({ commit }) {
        try {
            const result = await http().get("v1/admin/role/all-roles");
            commit("GET_ALL_ROLE", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StoreRole: ({commit}, data) => {
        return http()
            .post("v1/admin/role", data)
            .then((result) => {
                commit("STORE_ROLE", result);
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
    GetSingleRole: ({commit}, id) => {
        return http()
            .get(`v1/admin/role/${id}`)
            .then((result) => {
                commit("GET_SINGLE_ROLE", result.data.data);
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
    UpdateRole: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/role/${id}`, data)
            .then((result) => {
                commit("UPDATE_ROLE", result);
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
    DeleteRole: ({commit}, id) => {
        return http()
            .delete(`v1/admin/role/${id}`)
            .then((result) => {
                commit("DELETE_ROLE", {id:id, data:result});
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
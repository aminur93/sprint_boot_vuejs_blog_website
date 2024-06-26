/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    permissions: [],
    permission: {},
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
    GET_ALL_PERMISSION: (state, data) => {
        state.permissions = data;
    },

    STORE_PERMISSION: (state, data) => {
        if (state.permissions.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;

        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_PERMISSION: (state, data) => {
        state.permission = data;
    },

    UPDATE_PERMISSION: (state, data) => {
        if (state.permissions.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_PERMISSION: (state, {id, data}) => {
        if (id)
        {
            state.permissions = state.permissions.filter(item => {
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
    async GetAllPermission({ commit }) {
        try {
            const result = await http().get("v1/admin/permission/all-permission");
            commit("GET_ALL_PERMISSION", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StorePermission: ({commit}, data) => {
        return http()
            .post("v1/admin/permission", data)
            .then((result) => {
                commit("STORE_PERMISSION", result);
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
    GetSinglePermission: ({commit}, id) => {
        return http()
            .get(`v1/admin/permission/${id}`)
            .then((result) => {
                commit("GET_SINGLE_PERMISSION", result.data.data);
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
    UpdatePermission: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/permission/${id}`, data)
            .then((result) => {
                commit("UPDATE_PERMISSION", result);
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
    DeletePermission: ({commit}, id) => {
        return http()
            .delete(`v1/admin/permission/${id}`)
            .then((result) => {
                commit("DELETE_PERMISSION", {id:id, data:result});
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
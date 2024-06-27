/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    users: [],
    user: {},
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
    GET_ALL_USER: (state, data) => {
        state.users = data;
    },

    STORE_USER: (state, data) => {
        if (state.users.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;

        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_USER: (state, data) => {
        state.user = data;
    },

    UPDATE_USER: (state, data) => {
        if (state.users.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_USER: (state, {id, data}) => {
        if (id)
        {
            state.users = state.users.filter(item => {
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
    async GetAllUser({ commit }) {
        try {
            const result = await http().get("v1/admin/user/all-user");
            commit("GET_ALL_USER", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StoreUser: ({commit}, data) => {
        return http()
            .post("v1/admin/user", data)
            .then((result) => {
                commit("STORE_USER", result);
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
    GetSingleUser: ({commit}, id) => {
        return http()
            .get(`v1/admin/user/${id}`)
            .then((result) => {
                commit("GET_SINGLE_USER", result.data.data);
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
    UpdateUser: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/user/${id}`, data)
            .then((result) => {
                commit("UPDATE_USER", result);
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
    DeleteUser: ({commit}, id) => {
        return http()
            .delete(`v1/admin/user/${id}`)
            .then((result) => {
                commit("DELETE_USER", {id:id, data:result});
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
/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    menus: [],
    menu: {},
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
    GET_ALL_MENU: (state, data) => {
        state.menus = data;
    },

    STORE_MENU: (state, data) => {
        if (state.menus.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_MENU: (state, data) => {
        state.menu = data;
    },

    UPDATE_MENU: (state, data) => {
        if (state.menus.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_MENU: (state, {id, data}) => {
        if (id)
        {
            state.menus = state.menus.filter(item => {
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
    async GetAllMenu({ commit }) {
        try {
            const result = await http().get("v1/admin/menu/all-menus");
            commit("GET_ALL_MENU", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StoreMenu: ({commit}, data) => {
        return http()
            .post("v1/admin/menu", data)
            .then((result) => {
                commit("STORE_MENU", result);
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
    GetSingleMenu: ({commit}, id) => {
        return http()
            .get(`v1/admin/menu/${id}`)
            .then((result) => {
                commit("GET_SINGLE_MENU", result.data.data);
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
    UpdateMenu: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/menu/${id}`, data)
            .then((result) => {
                commit("UPDATE_MENU", result);
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
    DeleteMenu: ({commit}, id) => {
        return http()
            .delete(`v1/admin/menu/${id}`)
            .then((result) => {
                commit("DELETE_MENU", {id:id, data:result});
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
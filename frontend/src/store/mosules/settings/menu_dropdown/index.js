/* -------------------------------------------------------------------------- */
/*                                states Define                               */
/* -------------------------------------------------------------------------- */
import {http} from "@/service/HttpService";

const state = {
    menusDopDown: [],
    menuDopDown: {},
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
    GET_ALL_MENU_DROPDOWN: (state, data) => {
        state.menusDopDown = data;
    },

    STORE_MENU_DROPDOWN: (state, data) => {
        if (state.menusDopDown.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }else {
            state.success_message = '';
        }
    },

    GET_SINGLE_MENU_DROPDOWN: (state, data) => {
        state.menuDopDown = data;
    },

    UPDATE_MENU_DROPDOWN: (state, data) => {
        if (state.menusDopDown.push(data.data))
        {
            state.success_message = data.data.message;
            state.success_status = data.status;
        }
    },

    DELETE_MENU_DROPDOWN: (state, {id, data}) => {
        if (id)
        {
            state.menusDopDown = state.menusDopDown.filter(item => {
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
    async GetAllMenuDropDown({ commit }) {
        try {
            const result = await http().get("v1/admin/menu-dropdown/all-menu-dropdown");
            commit("GET_ALL_MENU_DROPDOWN", result.data.data);
        } catch (err) {
            const errors = err.response.data.errors;
            const errorStatus = err.response.status;
            commit("SET_ERROR", { errors, errorStatus });
            throw err;
        }
    },
    /*get all tag end*/

    /*store tag start*/
    StoreMenuDropDown: ({commit}, data) => {
        return http()
            .post("v1/admin/menu-dropdown", data)
            .then((result) => {
                commit("STORE_MENU_DROPDOWN", result);
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
    GetSingleMenuDropDown: ({commit}, id) => {
        return http()
            .get(`v1/admin/menu-dropdown/${id}`)
            .then((result) => {
                commit("GET_SINGLE_MENU_DROPDOWN", result.data.data);
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
    UpdateMenuDropDown: ({commit}, {id, data}) => {
        return http()
            .put(`v1/admin/menu-dropdown/${id}`, data)
            .then((result) => {
                commit("UPDATE_MENU_DROPDOWN", result);
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
    DeleteMenuDropDown: ({commit}, id) => {
        return http()
            .delete(`v1/admin/menu-dropdown/${id}`)
            .then((result) => {
                commit("DELETE_MENU_DROPDOWN", {id:id, data:result});
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
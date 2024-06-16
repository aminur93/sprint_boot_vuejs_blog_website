import {createStore}  from "vuex";
import createPersistedState from "vuex-persistedstate";

//root vuex start
import state from "./state";
import * as getters from "./getters";
import * as mutations from "./mutations";
import * as actions from "./actions";
//root vuex end

//module start
//module end

const store = createStore({

    state,
    getters,
    mutations,
    actions,

    modules: {},

    plugins: [
        createPersistedState({
            paths: ["token", "user", "permissions","role"]
        })
    ]

});

export default store;
import {createStore}  from "vuex";
import createPersistedState from "vuex-persistedstate";

//root vuex start
import state from "./state";
import * as getters from "./getters";
import * as mutations from "./mutations";
import * as actions from "./actions";
//root vuex end

//module start
import category from "@/store/mosules/category";
import sub_category from "@/store/mosules/sub_category";
import tag from "@/store/mosules/tag";
import blog from "@/store/mosules/blog";
//module end

const store = createStore({

    state,
    getters,
    mutations,
    actions,

    modules: {
        category,
        sub_category,
        tag,
        blog
    },

    plugins: [
        createPersistedState({
            paths: ["token", "user", "permissions","role"]
        })
    ]

});

export default store;
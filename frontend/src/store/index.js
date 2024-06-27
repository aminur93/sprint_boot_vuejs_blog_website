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
import permission from "@/store/mosules/user_management/permission";
import role from "@/store/mosules/user_management/role";
import user from "@/store/mosules/user_management/user";
import menu from "@/store/mosules/settings/menu";
import menu_dropdown from "@/store/mosules/settings/menu_dropdown";
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
        blog,
        permission,
        role,
        user,
        menu,
        menu_dropdown
    },

    plugins: [
        createPersistedState({
            paths: ["token", "user", "permissions","role"]
        })
    ]

});

export default store;
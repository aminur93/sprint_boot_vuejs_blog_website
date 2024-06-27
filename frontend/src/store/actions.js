import {http} from "@/service/HttpService";

export const logout = ({commit}) => {
    http().post('v1/auth/logout').then(() => {
        commit('clearToken');
    })
};

export const dashBoardCount = ({commit}) => {
    http().get("v1/admin/dashboard").then(result => {
        commit('DASHBOARD_COUNT', result.data.data);
    })
};
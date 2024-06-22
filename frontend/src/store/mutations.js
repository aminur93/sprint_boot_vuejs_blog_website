export const SET_TOKEN = (state, token) => {
    state.token = token;
    localStorage.setItem('token', token);
};

export const SET_REFRESH_TOKEN = (state, refreshToken) => {
    state.refreshToken = refreshToken;
    localStorage.setItem('refresh-token', refreshToken);
};

export const SET_USER = (state, data) => {
    state.user = data
    localStorage.setItem('user', JSON.stringify(data));
};

export const SET_ROLE = (state, role) => {
    state.role = role;
    localStorage.setItem('role', JSON.stringify(role));
};

export const SET_PERMISSION = (state, permissions) => {
    state.permissions = permissions;
    localStorage.setItem('permissions', JSON.stringify(permissions));
};

export const SET_MENU = (state, menus) => {
    state.menus = menus;
    localStorage.setItem('menus', JSON.stringify(menus));
};

export const clearToken = (state) => {
    localStorage.removeItem('token');
    localStorage.removeItem('refresh-token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    localStorage.removeItem('permissions');
    localStorage.removeItem('menus');
    state.token = '';
    state.refreshToken = '';
    state.user = '';
    state.role = '';
    state.permissions = [];
    state.menus = [];
};

export const SET_ERROR = (state, { errors, errorStatus }) => {
    state.errors = errors;
    state.error_status = errorStatus;
}
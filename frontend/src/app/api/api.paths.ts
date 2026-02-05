export const API_PATHS = {
  auth: {
    signup: '/api/auth/signup',
    login: '/api/auth/login',
    refresh: '/api/refreshToken',
    logout: '/api/auth/logout',
  },
  users: {
    aboutUser: '/api/user/aboutUser',
  },
  products: {
    list: '/api/products',
  },
} as const;

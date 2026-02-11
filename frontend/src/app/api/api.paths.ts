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
  booksGenreGroup: {
    getAll: '/api/bookGenreGroup/getAll',
  },
  bookGenre: {
    getAll: '/api/bookGenre/getAllBookGenre',
    getAllGenreAndGroups: '/api/bookGenre/getAllBookGenreAndGroups'
  },
  products: {
    list: '/api/products',
  },
} as const;

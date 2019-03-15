import cookie from 'vue-cookie'
import router from '../router'
import Axios from 'axios'



let instance = Axios.create({
  baseURL: 'http://localhost:8080/',
  withCredentials: true
});

instance.interceptors.request.use((config) => {
  config.headers.Authorization = 'Bearer ' + cookie.get('token');
  return config;
}, (error) => {
  return Promise.reject(error);
});

instance.interceptors.response.use(null,
  error => {
    console.log(error.response.status);
    if (error.response.status === 401) {
      router.push('/')
    }

    return Promise.reject(error)
  }
);

export default instance

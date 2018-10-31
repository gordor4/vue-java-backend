import axios from 'axios'
import cookie from 'vue-cookie'
import router from '../router'


let instance = axios.create({
  baseURL: 'http://gordor.host/rest/api/'
});

instance.interceptors.request.use((config) => {
  config.headers.Authorization = 'Bearer ' + cookie.get('token')
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

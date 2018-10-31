import axios from 'axios'
import cookie from 'vue-cookie'
import router from '../router'


let instance = axios.create({
  baseURL: 'http://gordor.host/rest/api/',
  // baseURL: 'http://localhost:8080/rest-1.0-SNAPSHOT/api/',
  headers: {
    'Authorization': 'Bearer ' + cookie.get('token')
  }
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

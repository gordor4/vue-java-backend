import axios from 'axios'
import cookie from 'vue-cookie'

export default axios.create({
  baseURL: 'http://localhost:8080/rest-1.0-SNAPSHOT/rest/',
  headers: {
    'Authorization': 'Bearer ' + cookie.get('token')
  }
})

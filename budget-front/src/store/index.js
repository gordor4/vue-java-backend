import Vue from 'vue'
import Vuex from 'vuex'
import axios from '../http/axios'

Vue.use(Vuex);

//TODO: add drawer state

export default new Vuex.Store({
  getters: {
    getUser: state => state.user
  },
  state: {
    user: {
      username: 'Username',
      avatar: '',
      full_name: ''
    }
  },
  actions: {
    loadUserData(context) {
      console.log('start download user data');
      axios.post('users/get', {
        headers: {
          'Content-Type': 'application/json',
        }})
        .then(response => {
          console.log(response.data)
          context.commit('setUser', response.data)
        })
        .catch(reason => console.log(reason))
    },
  },
  mutations: {
    setUser(state, user) {
      console.log(user);
      state.user = user
    }
  }
});

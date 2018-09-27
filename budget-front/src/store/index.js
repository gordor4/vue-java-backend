import Vue from 'vue'
import Vuex from 'vuex'
import axios from '../http/axios'

Vue.use(Vuex);

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
      axios.get('users/get')
        .then(response => context.commit('setUser', response.data.user))
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

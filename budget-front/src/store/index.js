import Vue from 'vue'
import Vuex from 'vuex'
import axios from '../http/axios'

Vue.use(Vuex);

//TODO: add drawer state

export default new Vuex.Store({
  getters: {
    getUser: state => state.user,
    getNavState: state => state.navigationShow
  },
  state: {
    user: {
      username: 'Username',
      avatar: '',
      firstName: '',
      lastName: '',
      secondName: ''
    },
    navigationShow: false
  },
  actions: {
    toggleNavBar(context, value) {
      context.commit('setNavBar', value)
    },
    loadUserData(context) {
      axios.post('users/get', {
        headers: {
          'Content-Type': 'application/json',
        }})
        .then(response => {
          context.commit('setUser', response.data)
        })
        .catch(reason => console.log(reason))
    },
  },
  mutations: {
    setUser(state, user) {
      console.log(user);
      state.user = user
    },
    setNavBar(state, value) {
      state.navigationShow = value
    }
  }
});

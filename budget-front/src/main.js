// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import BootstrapVue from 'bootstrap-vue'
import VeeValidate from 'vee-validate'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import querystring from 'querystring'
import VueCookie from 'vue-cookie'

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(querystring);
Vue.use(VeeValidate);
Vue.use(Vuetify);
Vue.use(VueCookie);

//axios.defaults.baseURL = 'http://gordor.host';
axios.defaults.baseURL = 'http://localhost:8080/rest-1.0-SNAPSHOT/rest/';

Vue.prototype.$http = axios;
Vue.prototype.$querystring = querystring;

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});

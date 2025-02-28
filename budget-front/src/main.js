// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from './http/axios'
import BootstrapVue from 'bootstrap-vue'
import VeeValidate from 'vee-validate'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import querystring from 'querystring'
import VueCookie from 'vue-cookie'
import VueRecaptcha from 'vue-recaptcha'
import store from './store'
import VueShowdown from 'vue-showdown'

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(querystring);
Vue.use(VeeValidate);
Vue.use(Vuetify);
Vue.use(VueCookie);
Vue.use(VueRecaptcha);
Vue.use(VueShowdown, {
  emoji: true
});

Vue.prototype.$http = axios;
Vue.prototype.$querystring = querystring;

new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
});

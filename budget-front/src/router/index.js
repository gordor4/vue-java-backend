import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Index from '@/components/Index'
import Reset from '@/components/Reset'
import ResetPassword from '@/components/ResetPassword'
import Profile from '@/views/Profile'

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/index',
      name: 'Index',
      component: Index,
      children: [
        {
          path: 'profile',
          name: 'Profile',
          component: Profile
        }
      ]
    },
    {
      path: '/reset',
      name: 'Reset',
      component: Reset
    },
    {
      path: '/resetPassword',
      name: 'resetPassword',
      component: ResetPassword
    }
  ]
})

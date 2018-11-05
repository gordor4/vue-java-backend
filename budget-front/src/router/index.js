import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Index from '@/components/Index'
import Reset from '@/components/Reset'
import ResetPassword from '@/components/ResetPassword'
import Profile from '@/views/Profile'
import Dashboard from '@/views/Dashboard'
import DashboardView from '@/views/DashboardView'

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
        },
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: Dashboard
        },
        {
          path: 'board/:board_id',
          name: 'DashboardView',
          component: DashboardView
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

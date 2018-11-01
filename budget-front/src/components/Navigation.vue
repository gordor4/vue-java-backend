<template>
  <v-navigation-drawer :clipped="$vuetify.breakpoint.mdAndUp" absolute temporary :value="showDrawer">
    <v-list dense>
      <template v-for="item in items">
        <v-layout row npm v-if="item.heading" align-center :key="item.heading">
          <v-flex xs6>
            <v-subheader v-if="item.heading">
              {{ item.heading }}
            </v-subheader>
          </v-flex>
        </v-layout>
        <v-list-tile v-else @click="router_push(item.route)" :key="item.text">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>
              {{ item.text }}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </template>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
  export default {
    name: "Navigation",
    data() {
      return {
        dialog: false,
        avatarSize: '46px',
        items: [
          {icon: 'dashboard', text: 'Мои доски', route:'/index/dashboard'},
          {icon: 'settings', text: 'Настройки'},
          {icon: 'help', text: 'Помощь'},
          {icon: 'phonelink', text: 'Скачать приложение'},
          {icon: 'assignment_ind', text: 'Профиль', route: '/index/profile'},
          {icon: 'exit_to_app', text: 'Выйти', route: '/'}
        ]
      }
    },
    methods: {
      logout() {
        this.$cookie.delete('token')
      },
      router_push(route) {
        this.$store.dispatch('toggleNavBar', false);
        if (route === '/') {
          this.$store.dispatch('clearUserData');
          this.logout()
        }
        this.$router.push(route)
      }
    },
    computed: {
      showDrawer() {
        return this.$store.getters.getNavState
      }
    }
  }
</script>

<style scoped>

</style>

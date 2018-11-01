<template>
  <div id="app">
    <v-app id="inspire">
      <navigation></navigation>
      <v-toolbar color="blue darken-3" dark app :clipped-left="$vuetify.breakpoint.mdAndUp" fixed>
        <v-toolbar-title class="ml-0 pl-3">
          <v-toolbar-side-icon @click.stop="toggleNavBar"></v-toolbar-side-icon>
          <span class="hidden-sm-and-down mr-3"></span>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-avatar size="33" color="grey lighten-4">
          <img :src=userAvatar()>
        </v-avatar>
      </v-toolbar>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-app>
  </div>
</template>

<script>
  import Navigation from "./Navigation";

  export default {
    name: "Index",
    components: {Navigation},
    data() {
      return {}
    },
    computed: {
      showDrawer() {
        return this.$store.getters.getNavState
      },
      user() {
        return this.$store.getters.getUser
      }
    },
    methods: {
      toggleNavBar(state) {
        this.$store.dispatch('toggleNavBar', state)
      },
      userAvatar() {
        return 'data:image/jpg;base64,' + this.user.avatar
      }
    },
    created() {
      this.$store.dispatch('loadUserData')
    }
  }
</script>

<style scoped>

</style>

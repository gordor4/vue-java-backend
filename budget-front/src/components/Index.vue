<template>
  <div id="app">
    <v-app id="inspire">
      <navigation></navigation>
      <v-toolbar color="blue darken-3" dark app :clipped-left="$vuetify.breakpoint.mdAndUp" fixed>
        <v-toolbar-title class="ml-0 pl-3">
          <v-toolbar-side-icon @click.stop="toggleNavBar"></v-toolbar-side-icon>
          <span class="hidden-sm-and-down mr-3"></span>
        </v-toolbar-title>
        <v-text-field flat solo-inverted prepend-icon="search" label="Search" class="hidden-sm-and-down"></v-text-field>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>apps</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>notifications</v-icon>
        </v-btn>
      </v-toolbar>
      <v-container>
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
      }
    },
    methods: {
      toggleNavBar(state) {
        this.$store.dispatch('toggleNavBar', state)
      }
    }
  }
</script>

<style scoped>

</style>

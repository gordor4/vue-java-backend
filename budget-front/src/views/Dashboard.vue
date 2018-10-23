<template>
  <v-container v-bind="{ [`grid-list-$3`]: true }" fluid>
    <v-layout row wrap>
      <v-flex
        v-for="n in 9"
        :key="n"
        xs4
      >
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: "Dashboard",
    data() {
      return {
        dashboards: null
      }
    },
    created() {
      this.$http.post('users/resetPassword', '')
        .then(response => {})
        .catch(error => {})
    }
  }
</script>

<style scoped>

</style>

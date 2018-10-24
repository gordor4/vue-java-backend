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
    <v-btn
      color="pink"
      fab
      dark
      fixed
      bottom
      right
      @click="dialog = true"
    >
      <v-icon>add</v-icon>
    </v-btn>

    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click.native="dialog = false">
            <v-icon>close</v-icon>
          </v-btn>
          <v-toolbar-title>Создание новой доски</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn dark flat @click.native="dialog = false">Создать</v-btn>
          </v-toolbar-items>
        </v-toolbar>

        <v-list three-line subheader>
          <v-subheader>Общая информация</v-subheader>
          <v-layout row wrap px-3>
            <v-flex
              xs12
              sm2
              md3
              align-center
              layout
              text-xs-center
            >
              <v-list-tile avatar>
                <v-list-tile-content>
                  <v-list-tile-title>Название доски</v-list-tile-title>
                  <v-list-tile-sub-title>Имя будут видеть все, кто имеет доступ к доске</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
            <v-flex
              xs12
              sm7
              md6
              align-center
              layout
            >
              <v-text-field v-model="newBoard.boardName" label="Название доски"></v-text-field>
            </v-flex>
          </v-layout>

          <v-layout row wrap px-3>
            <v-flex
              xs12
              sm2
              md3
              align-center
              layout
              text-xs-center
            >
              <v-list-tile avatar>
                <v-list-tile-content>
                  <v-list-tile-title>Название доски</v-list-tile-title>
                  <v-list-tile-sub-title>Имя будут видеть все, кто имеет доступ к доске</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
            <v-flex
              xs12
              sm7
              md6
              align-center
              layout
            >
              <v-text-field v-model="newBoard.boardName" label="Название доски"></v-text-field>
            </v-flex>
          </v-layout>

        </v-list>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  export default {
    name: "Dashboard",
    data() {
      return {
        dashboards: null,
        dialog: false,
        newBoard: {
          boardName: null
        }
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

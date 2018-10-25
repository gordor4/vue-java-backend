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

    <v-dialog width="600" v-model="dialog" transition="dialog-bottom-transition">
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
      <v-card>
        <v-list three-line subheader>
          <v-subheader>Общая информация</v-subheader>
          <v-layout>
            <v-flex
              px-4
              xs12
              sm12
              md12
              align-center
              text-xs-center
            >
              <v-text-field v-model="newBoard.boardName" label="Название доски"></v-text-field>
            </v-flex>
          </v-layout>

          <v-layout>
            <v-flex
              xs12
              sm12
              md12
              align-center
              text-xs-center
            >
              <v-list-tile avatar>
                <v-list-tile-action>
                  <v-checkbox v-model="is_public"></v-checkbox>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>Доступ по ссылке</v-list-tile-title>
                  <v-list-tile-sub-title>Люди, у которых есть ссылка смогут просматривать доску</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
          </v-layout>

          <v-layout>
            <v-flex
              xs12
              sm12
              md12
              align-center
              layout
              text-xs-center
            >
              <v-list-tile avatar>
                <v-list-tile-action>
                  <v-checkbox v-model="is_public_edit"></v-checkbox>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>Редактирование</v-list-tile-title>
                  <v-list-tile-sub-title>Люди, у которых есть ссылка смогут редактировать доску</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
          </v-layout>

          <v-layout v-if="!is_public">
            <v-flex
              xs12
              sm12
              md12
              align-center
              layout
              text-xs-center
            >
              <v-list-tile avatar>
                <v-list-tile-content>
                  <v-list-tile-title>Добавить людей</v-list-tile-title>
                  <v-list-tile-sub-title>Люди, у которых есть доступ для просмотра доски</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
          </v-layout>
          <v-layout v-if="!is_public">
            <v-flex
              xs12
              sm12
              md12
              align-center>

              <v-chip v-for="user in available_users" close>{{user}}</v-chip>
              <v-btn fab small dark color="indigo">
                <v-icon dark @click="addUser">add</v-icon>
              </v-btn>
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
        is_public: false,
        is_public_edit: false,
        available_users: [
          'user1', 'user2', 'user3'
        ],
        newBoard: {
          boardName: null
        }
      }
    },
    methods: {
      addUser() {
        this.available_users.push('user')
      }
    },
    created() {
      this.$http.post('users/resetPassword', '')
        .then(response => {
        })
        .catch(error => {
        })
    }
  }
</script>

<style scoped>

</style>

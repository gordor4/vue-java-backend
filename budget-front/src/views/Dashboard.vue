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
          <v-btn dark flat @click.native="createBoard">Создать</v-btn>
        </v-toolbar-items>
      </v-toolbar>
      <v-card>
        <v-list three-line subheader>
          <v-layout>
            <v-flex
              px-4
              pt-3
              xs12
              sm12
              md12
              align-center
              text-xs-center
            >
              <v-text-field v-model="board.boardName" label="Название доски"></v-text-field>
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
                  <v-checkbox v-model="board.isPublic"></v-checkbox>
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
                  <v-checkbox v-model="board.isPublicEdit"></v-checkbox>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>Редактирование</v-list-tile-title>
                  <v-list-tile-sub-title>Люди, у которых есть ссылка смогут редактировать доску</v-list-tile-sub-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-flex>
          </v-layout>
          <v-layout v-if="!board.isPublic">
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
          <v-layout v-if="!board.isPublic">
            <v-flex
              xs12
              sm12
              md12
              align-center>
              <v-chip v-for="(user, index) in available_users" :key="index" @click="deleteUser(index)" close>{{user}}
              </v-chip>
              <v-btn fab small dark color="indigo">
                <v-icon dark @click="openNewUserDialog">add</v-icon>
              </v-btn>
            </v-flex>
          </v-layout>
        </v-list>
      </v-card>
    </v-dialog>

    <v-dialog width="500" v-model="user_dialog">
      <v-toolbar dark color="primary">
        <v-btn icon dark @click.native="user_dialog = false">
          <v-icon>close</v-icon>
        </v-btn>
        <v-toolbar-title>Добавление пользователя</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
          <v-btn dark flat @click.native="user_dialog = false">Добавить</v-btn>
        </v-toolbar-items>
      </v-toolbar>
      <v-card>
        <v-layout>
          <v-flex
            py-3
            px-4
            xs12
            sm9
            md9
            align-center
            text-xs-center
          >
            <v-text-field v-model="search_user" label="Имя пользователя / Почта"></v-text-field>
          </v-flex>
          <v-flex
            py-4
            px-3
            xs12
            sm3
            md3>
            <v-btn outline color="indigo">Найти</v-btn>
          </v-flex>
        </v-layout>
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
        user_dialog: false,
        search_user: null,
        available_users: [],
        board: {
          boardName: null,
          isPublic: false,
          isPublicEdit: false,
        }
      }
    },
    methods: {
      createBoard() {
        this.dialog = false

        this.$http.post('board/createBoard', {
          "boardName": this.board.boardName,
          "isPublic": this.board.isPublic,
          "isPublicEdit": this.board.isPublicEdit
        })
          .then(response => {
          })
          .catch(error => {
          })
      },
      openNewUserDialog() {
        this.user_dialog = true

      },
      addUser() {
        this.available_users.push('user')
      },
      deleteUser(index) {
        this.available_users.splice(index, 1)
      }
    },
    created() {
      // this.$http.post('users/resetPassword', '')
      //   .then(response => {
      //   })
      //   .catch(error => {
      //   })
    }
  }
</script>

<style scoped>

</style>

<template>
  <v-layout>
    <v-container fluid>
      <v-layout row wrap pt-4>
        <v-flex xs12 sm4 md3 lg2
          v-for="(dashboard , index) in dashboards" :key="dashboard.name" xs3 md3 lg3 my-1 pa-2>
          <v-card>
            <v-img
              src="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
              aspect-ratio="2.75">
            </v-img>
            <v-card-title>
              <div>
                <h3>{{dashboard.boardName}}</h3>
                <div>{{dashboard.boardDescription}}</div>
              </div>
            </v-card-title>
            <v-card-actions>
              <v-btn flat icon v-if="dashboard.public" @click="showLinkDialog">
                <v-icon>insert_link</v-icon>
              </v-btn>
              <v-btn flat icon v-if="!dashboard.public" @click="showLinkDialog">
                <v-icon>person_add</v-icon>
              </v-btn>
              <v-btn flat icon>
                <v-icon>settings</v-icon>
              </v-btn>
              <v-btn flat icon @click="deleteBoard(dashboard.id, index)">
                <v-icon>delete</v-icon>
              </v-btn>
              <v-spacer></v-spacer>
              <v-btn flat icon @click="openBoard(dashboard.id)">
                <v-icon large>chevron_right</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>

        </v-flex>
      </v-layout>
    </v-container>

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
            <v-flex px-4 pt-4 xs12 sm12 md12 align-center text-xs-center>
              <v-text-field outline v-model="board.boardName" label="Название доски"></v-text-field>
            </v-flex>
          </v-layout>
          <v-layout>
            <v-flex pt-2 px-4>
              <v-textarea outline label="Описание доски" v-model="board.boardDescription"></v-textarea>
            </v-flex>
          </v-layout>
          <v-layout>
            <v-flex xs12 sm12 md12 align-center text-xs-center>
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
            <v-flex xs12 sm12 md12 align-center layout text-xs-center>
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
          <v-layout column v-if="!board.isPublic">
            <v-layout>
              <v-flex xs12 sm12 md12 align-center layout text-xs-center>
                <v-list-tile avatar>
                  <v-list-tile-content>
                    <v-list-tile-title>Добавить людей</v-list-tile-title>
                    <v-list-tile-sub-title>Люди, у которых есть доступ для просмотра доски</v-list-tile-sub-title>
                  </v-list-tile-content>
                </v-list-tile>
              </v-flex>
            </v-layout>
            <v-layout>
              <v-flex xs12 sm12 md12 align-center>
                <v-chip v-for="(user, index) in available_users" :key="index" @click="deleteUser(index)" close>{{user}}
                </v-chip>
                <v-btn fab small dark color="indigo">
                  <v-icon dark @click="openNewUserDialog">add</v-icon>
                </v-btn>
              </v-flex>
            </v-layout>
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
          <v-flex py-2 px-4 xs12 sm9 md9 align-center text-xs-center>
            <v-text-field v-model="search_user" label="Имя пользователя / Почта"></v-text-field>
          </v-flex>
          <v-flex py-2 px-3 xs12 sm3 md3>
            <v-btn outline color="indigo">Найти</v-btn>
          </v-flex>
        </v-layout>
      </v-card>
    </v-dialog>

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
  </v-layout>
</template>

<script>
  export default {
    name: "Dashboard",
    data() {
      return {
        dashboards: [],
        downloaded: false,
        dialog: false,
        user_dialog: false,
        search_user: null,
        available_users: [],
        board: {
          boardName: null,
          isPublic: false,
          isPublicEdit: false,
          boardDescription: null
        }
      }
    },
    methods: {
      deleteBoard(id, index) {
        this.$http.post('board/deleteBoard', {"id": id})
          .then(response => {
            this.dashboards.splice(index, 1)
          })
      },
      updateBoards() {
        this.$http.post('board/getUserBoard')
          .then(response => {
            this.dashboards = response.data;
            this.downloaded = true
          })
          .catch(error => {
          })
      },
      createBoard() {
        this.dialog = false;

        this.$http.post('board/createBoard', this.board)
          .then(response => {
          })
          .catch(error => {
          })
          .finally(() => this.updateBoards())
      },
      openNewUserDialog() {
        this.user_dialog = true

      },
      addUser() {
        this.available_users.push('user')
      },
      deleteUser(index) {
        this.available_users.splice(index, 1)
      },
      showLinkDialog() {
        //TODO: добавить доступ по ссылке
      },
      openBoard(id) {
        this.$router.push('board/' + id)
      }
    },
    created() {
      this.updateBoards()
    }
  }
</script>

<style scoped>

</style>

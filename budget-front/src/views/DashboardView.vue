<template>
  <v-container fluid>
    <v-card class="mt-5" style="height: 100%">
      <v-toolbar color="primary">
        <v-btn @click="backToList" icon dark>
          <v-icon>arrow_back</v-icon>
        </v-btn>
        <v-toolbar-title>
          {{this.board.boardName}}
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-speed-dial
          v-model="fab"
          direction="bottom"
          hover="true"
          :open-on-hover="true">
          <v-btn
            small slot="activator" v-model="fab" icon dark fab>
            <v-icon color="white" medium>dehaze</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            small
            color="green">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn
            @click="addCard"
            fab
            dark
            small
            color="indigo">
            <v-icon>add</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            small
            color="red">
            <v-icon>delete</v-icon>
          </v-btn>
        </v-speed-dial>
      </v-toolbar>
      <v-card-title>
        <v-layout>
          <v-flex xs12 sm6 md4 lg3>
            <v-card v-for="card in boardCards">
              <v-card-title>
                {{card.cardName}}
              </v-card-title>
              <v-card-text>

              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>
      </v-card-title>
    </v-card>



  </v-container>
</template>

<script>
  export default {
    name: "DashboardView",
    data() {
      return {
        board: null,
        boardCards: [{cardName: null}],
        fab: false,
        newCardDialog: false
      }
    },
    computed: {
      board_id() {
        return this.$route.params.board_id
      }
    },
    methods: {
      backToList() {
        this.$router.back()
      },
      addCard() {

      }
    },
    created() {
      this.$http.post('board/getBoardCards', {id: this.board_id})
        .then(response => {
          this.board = response.data.board;
          this.boardCards = response.data.boardCards;
        })
    }
  }
</script>

<style scoped>

</style>

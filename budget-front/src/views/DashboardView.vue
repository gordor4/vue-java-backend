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
      </v-toolbar>
      <v-card-title>
        <v-layout>
          <v-flex xs12 sm6 md4 lg3>
            <v-card v-for="card in boardCards">
              <v-card-title>
                {{card.cardName}}
              </v-card-title>
            </v-card>
          </v-flex>
        </v-layout>
      </v-card-title>
      <v-card-actions>

      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
  export default {
    name: "DashboardView",
    data() {
      return {
        board: null,
        boardCards: [{cardName: null}]
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

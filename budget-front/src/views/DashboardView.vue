<template>
  <v-container fluid>
    <v-card class="mt-5" style="height: 100%">
      <v-toolbar dark color="primary">
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
      <v-card-text>
        <v-layout grayBackground>
          <v-flex xs12 sm6 md4 pa-2 lg3 :key="card.cardName" v-for="card in boardCards">
            <v-card hover @click.native="openCard(card.id, card.cardType)" >
              <v-card-title>
                {{card.cardName}}
              </v-card-title>
              <v-card-text>
              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>
      </v-card-text>
    </v-card>
    <v-dialog width=600 v-model="newCardDialog">
      <v-card>
        <v-toolbar color="primary">
          Добавление новой карточки
        </v-toolbar>
        <v-card-text>
          <v-text-field outline label="Название карточки" v-model="newCard.cardName">
          </v-text-field>
          <v-select
            v-model="newCard.cardType"
            outline
            :items="newCardTypes"
            item-text="name"
            item-value="type"
            label="Выберите тип карточки"
          ></v-select>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="createNewCard" color="primary" flat>
            Добавить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="cardDialog" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click.native="cardDialog = false">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-text>
          <component v-bind:is=cardTypes.text_card :cardContent=cardContent></component>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  import TextCard from '@/components/TextCard.vue'

  export default {
    name: "DashboardView",
    data() {
      return {
        board: {},
        boardCards: [{cardName: null}],
        fab: false,
        cardDialog: false,
        newCardDialog: false,
        newCardTypes: [
          {name: 'Текстовая карточка', type: 'text_card'}
        ],
        newCard: {
          cardName: '',
          cardType: null,
          boardId: null
        },
        cardTypes: {
          text_card: TextCard
        },
        cardContent: null
      }
    },
    computed: {
      board_id() {
        return this.$route.params.board_id
      },
      backgroundUrl() {
        return "@/assets/background/4.jpg"
      }
    },
    methods: {
      backToList() {
        this.$router.back()
      },
      addCard() {
        this.newCardDialog = true
      },
      createNewCard() {
        this.newCard.boardId = this.board_id;
        this.$http.post('board/createBoardCard', this.newCard)
          .then(response => {
            this.newCardDialog = false;
            this.updateCardList()
          })
      },
      updateCardList() {
        this.$http.post('board/getBoardCards', {id: this.board_id})
          .then(response => {
            this.board = response.data.board;
            this.boardCards = response.data.boardCards;
          })
      },
      openCard(id, type) {
        this.loadCardContent(id, type)
      },
      loadCardContent(id, type) {
        this.$http.post('board/getBoardCardContent',
          {
            boardCardId: id, cardType: type
          }
        ).then(response => {
          this.cardContent = response.data;
          this.cardDialog = true;
        }).catch(error => {
          console.log(error)
        })
      }

    },
    created() {
      this.updateCardList()
    }
  }
</script>

<style scoped>

</style>

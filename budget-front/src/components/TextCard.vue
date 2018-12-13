<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12>
        <v-card>
          <v-toolbar color="primary">
            <v-toolbar-title>
              {{cardContentTitle}}
            </v-toolbar-title>
            <v-layout aling-end>
              <v-btn icon dark>
                <v-icon fab @click="editCardText">edit</v-icon>
              </v-btn>
              <v-btn icon dark>
                <v-icon fab @click="updateTextCard">save</v-icon>
              </v-btn>
              <v-btn icon dark>
                <v-icon fab @click="editCardText">settings</v-icon>
              </v-btn>
            </v-layout>

          </v-toolbar>
          <v-card-text>
            <v-layout row wrap>
              <v-flex pa-2 v-if="editMode">
                <v-textarea outline v-model=cardText></v-textarea>
              </v-flex>
              <v-flex>
                <VueShowdown :markdown=cardContentText></VueShowdown>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>

  </v-container>
</template>

<script>
  export default {
    name: "TextCard",
    data() {
      return {
        editMode: false,
        cardText: ''
      }
    },
    props: ['cardContent'],
    methods: {
      editCardText() {
        this.editMode = !this.editMode;
        this.cardText = this.cardContent.cardText
      },
      updateTextCard() {
        this.$http.post('board/updateTextCard', {
          id: this.cardContent.id,
          cardText: this.cardText,
          title: this.title
        }).then(response => {
          console.log('ok', response)
        })
      }
    },
    computed: {
      cardContentText() {
        return this.editMode ? this.cardText : this.cardContent.cardText
      },
      cardContentTitle() {
        return !this.cardContent ? '' : this.cardContent.title
      }
    }
  }
</script>

<style scoped>

</style>

<template>
  <v-container>
    <v-flex xs12 mt-5>
      <v-card>
        <v-card-title primary-title>
          <div>
            <h3 class="headline mb-0">Профиль пользователя</h3>
          </div>
        </v-card-title>

        <v-card-text>
          <v-layout row wrap pa-3>
            <v-flex
              xs12
              sm3
              md4
              align-center
              justify-center
              layout
              text-xs-center
            >
              <v-avatar
                :size="100"
                color="grey lighten-4"
              >
                <img :src=userAvatar()>
              </v-avatar>
            </v-flex>
            <v-flex xs12 sm9 md8>
              <v-text-field v-model="user.firstName" label="Фамилия"></v-text-field>
              <v-text-field v-model="user.lastName" label="Имя"></v-text-field>
              <v-text-field v-model="user.secondName" label="Отчество"></v-text-field>
            </v-flex>
          </v-layout>
          <v-layout row wrap pa-3>
            <v-btn block>
              Сохранить
            </v-btn>
          </v-layout>
        </v-card-text>

      </v-card>
    </v-flex>

  </v-container>
</template>

<script>
  export default {
    name: "Profile",
    data() {
      return {
        user: {
          firstName: null,
          lastName: null,
          secondName: null,
          avatar: null
        }
      }
    },
    computed: {
      userStore() {
        return this.$store.getters.getUser
      }
    },
    methods: {
      userAvatar() {
        return 'data:image/jpg;base64,' + this.userStore.avatar
      }
    },
    created() {
    }
  }

</script>

<style scoped>

</style>

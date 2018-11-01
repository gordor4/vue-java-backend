<template>
  <v-app>
    <v-container >
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-card row class="pa-3">
              <!--TODO:Добавить валидацию email-->
              <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
              <v-card-title>
                <v-flex>
                  <h2>Восстановление пароля</h2>
                </v-flex>
              </v-card-title>
              <v-layout row wrap my-2 mx-3>
                <v-text-field v-model="email" label="E-mail"></v-text-field>
              </v-layout>
              <v-layout row wrap my-2 mx-3>
                <router-link to="/">Войти</router-link>
              </v-layout>
              <v-layout row wrap align-center justify-end fill-height mt-5>
                <v-flex xs12 sm4 md4 lg4>
                  <v-btn color="primary" class="rounded-btn" @click="reset">Восстановить</v-btn>
                </v-flex>
              </v-layout>
              <vue-recaptcha sitekey="6LezU3IUAAAAADypzP5J4e1-5i8vBlmiRaYnOQJy"></vue-recaptcha>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
  import VueRecaptcha from 'vue-recaptcha';

  export default {
    name: "Reset",
    data() {
      return {
        loading: false,
        email: ""
      }
    },
    components: {VueRecaptcha},
    methods: {
      reset() {
        this.loading = true;

        this.$http.post('users/reset', {email: this.email})
          .then(response => {
            this.loading = false;
            this.$route.push('/');
          })
      }
    }
  }
</script>

<style scoped>
  .rounded-btn {
    border-radius:4px;
  }
</style>

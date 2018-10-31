<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-card row class="text-xs-center">
            <div class="centered-container">
              <!--TODO:Добавить валидацию email-->
              <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
              <div class="form">
                <v-card-title>
                  <h1>Восстановление пароля</h1>
                </v-card-title>

                <v-text-field v-model="email" label="E-mail"></v-text-field>

                <div class="md-layout mb-5">
                  <router-link to="/">Войти</router-link>
                  <v-btn @click="reset">Восстановить</v-btn>
                </div>
                <vue-recaptcha sitekey="6LezU3IUAAAAADypzP5J4e1-5i8vBlmiRaYnOQJy"></vue-recaptcha>
              </div>
            </div>
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

</style>

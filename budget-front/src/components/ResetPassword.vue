<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-card row class="text-xs-center">
                <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
                <div class="form">
                  <v-card-title>
                    <h1>Восстановление пароля</h1>
                  </v-card-title>

                  <v-text-field v-model="newPassword" type="password" label="Новый пароль"></v-text-field>
                  <v-text-field v-model="repeatNewPassword" type="password"
                                label="Повторите новый пароль"></v-text-field>

                  <div class="md-layout mb-5">
                    <router-link to="/">Войти</router-link>
                    <v-btn @click="reset">Сменить пароль</v-btn>
                  </div>
                </div>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
  export default {
    name: "ResetPassword",
    data() {
      return {
        loading: false,
        newPassword: null,
        repeatNewPassword: null
      }
    },
    methods: {
      reset() {
        this.loading = true;

        this.$http.post('users/resetPassword', this.$querystring.stringify(
          {
            newPassword: this.newPassword
          }),
          {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          })
          .then(response => {
            this.loading = false;

            this.$router.push('/');
          })
      }
    },
    created() {
      let queryToken = this.$route.query.token;
      if (queryToken != null && queryToken.length !== 0) {
        console.log(queryToken);
        this.$cookie.set('token', queryToken, 15);
      }
    }
  }
</script>

<style scoped>

</style>

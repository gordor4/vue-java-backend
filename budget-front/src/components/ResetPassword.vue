<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
          <v-card row class="pa-3">
            <v-flex pa-3>
              <v-card-title>
                <v-flex>
                  <h2>Смена пароля</h2>
                </v-flex>

              </v-card-title>

              <v-layout row wrap my-2 mx-3>
                <v-text-field v-model="newPassword" type="password" label="Новый пароль"></v-text-field>
              </v-layout>
              <v-layout row wrap my-2 mx-3>
                <v-text-field v-model="repeatNewPassword" type="password"
                              label="Повторите новый пароль"></v-text-field>
              </v-layout>

              <v-layout row wrap my-2 mx-3>
                <router-link to="/">Войти</router-link>
              </v-layout>
              <v-layout row wrap align-center justify-end row fill-height mt-5>
                <v-flex xs12 sm3 md3 lg3>
                  <v-btn color="primary" class="rounded-btn" @click="reset">Сменить пароль</v-btn>
                </v-flex>
              </v-layout>
            </v-flex>
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
  .rounded-btn {
    border-radius:4px;
  }
</style>

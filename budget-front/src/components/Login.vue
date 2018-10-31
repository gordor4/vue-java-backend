<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-card class="text-xs-center">
            <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
            <v-form ref="form" class="form" v-model="valid">
              <v-card-title>
                <h1>Авторизация</h1>
              </v-card-title>

              <v-text-field v-model="login.username" :rules="usernameRules" required
                            label="Имя пользователя"></v-text-field>
              <v-text-field v-model="login.password" :rules="passwordRules" required type="password"
                            label="Пароль"></v-text-field>

              <div class="md-layout mt-2 mb-5">
                <router-link to="/reset">Восстановить пароль</router-link>
              </div>
              <div class="md-layout mb-3">
                Войти с помощью:
              </div>
              <div class="md-layout mb-3">
                <img :src="require('../assets/google.svg')"/>
              </div>
              <div class="md-layout mb-3">
                <router-link to="/register">Регистрация</router-link>
                <v-btn @click="auth">Войти</v-btn>
              </div>
            </v-form>
            <v-alert v-if="errorText" :value="true" type="error">
              {{errorText}}
            </v-alert>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        valid: false,
        loading: false,
        login: {
          username: "",
          password: ""
        },
        errorText: "",
        usernameRules: [
          v => !!v || 'Имя пользователя обязательно'
        ],
        passwordRules: [
          v => !!v || 'Пароль обязателен'
        ]
      };
    },
    methods: {
      auth() {
        if (this.$refs.form.validate()) {
          this.errorText = '';
          this.loading = true;

          this.$http.post('users/login', this.$querystring.stringify(
            {
              username: this.login.username,
              password: this.login.password
            }),
            {
              headers: {
                "Content-Type": "application/x-www-form-urlencoded"
              }
            })
            .then(response => {
              this.loading = false;
              let newToken = response.data.token;

              if (newToken) {
                this.$cookie.set('token', newToken, 15);
                this.$router.push("index")
              }
            })
            .catch(error => {
              switch (error.response.status) {
                case 15:
                  this.errorText = 'Пользователь не найден';
                  break;
                default:
                  this.errorText = 'При авторизации произошла ошибка';
              }
              this.loading = false;
            });
        }
      }
    }
  }
</script>

<style scoped>
</style>

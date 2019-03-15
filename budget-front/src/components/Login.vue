<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-card class="rounded-card">
            <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
            <v-form ref="form" class="pa-3" v-model="valid">
              <v-card-title>
                <v-flex>
                  <h2>Авторизация</h2>
                </v-flex>
              </v-card-title>
              <v-layout row wrap mb-3 mt-3 mx-3>
                <v-text-field v-model="login.username" :rules="usernameRules" required
                              label="Имя пользователя"></v-text-field>
              </v-layout>
              <v-layout row wrap mb-3 mt-3 mx-3>
                <v-text-field v-model="login.password" :rules="passwordRules" required type="password"
                              label="Пароль"></v-text-field>
              </v-layout>
              <v-layout row wrap mb-3 mt-3 mx-3>
                <router-link to="/reset">Восстановить пароль</router-link>
              </v-layout>
              <v-layout row wrap mb-5 mx-3>
                <router-link to="/register">Регистрация</router-link>
              </v-layout>
              <!--<v-layout row wrap mb-3 ml-2>-->
              <!--<img :src="require('../assets/google.svg')"/>-->
              <!--</v-layout>-->
              <v-layout row wrap align-center justify-end row fill-height>
                <v-flex xs12 sm3 md3 lg3>
                  <v-btn color="primary" class="rounded-btn" @click="auth">Войти</v-btn>
                </v-flex>
              </v-layout>
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

          this.$http.post('auth/auth', this.$querystring.stringify(
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
  .rounded-btn {
    border-radius: 4px;
  }
</style>

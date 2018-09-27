<template>
  <v-app>
    <v-container class="md-content">
      <v-card row class="text-xs-center">
        <div class="centered-container">
          <v-flex>
            <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
            <div class="form">
              <v-card-title>
                <h1>Авторизация</h1>
              </v-card-title>

              <v-text-field v-model="login.username" label="Имя пользователя"></v-text-field>
              <v-text-field v-model="login.password" type="password" label="Пароль"></v-text-field>

              <div class="md-layout mb-5">
                <router-link to="/reset">Восстановить пароль</router-link>
              </div>
              <div class="md-layout mb-3">
                <router-link to="/register">Регистрация</router-link>
                <v-btn @click="auth">Войти</v-btn>
              </div>
              <v-alert v-if="errorText" :value="true" type="error">
                {{errorText}}
              </v-alert>

            </div>
          </v-flex>
        </div>
      </v-card>

    </v-container>
  </v-app>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        loading: false,
        login: {
          username: "",
          password: ""
        },
        errorText: ""
      };
    },
    methods: {
      auth() {
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
            let newToken =  response.data.token;

            if(newToken) {
              this.$cookie.set('token', newToken, 15);
              this.$router.push("index")
            }
          })
          .catch(error => {
            switch(error.response.status) {
              case 15:
                this.errorText = 'Пользователь не найден';
                break;
              default:
                this.errorText = 'при авторизации произошла ошибка';
            }
            this.loading = false;

          });
      }
    }
  }
</script>

<style scoped>
</style>

<template>
  <v-app>
    <v-container>
      <v-layout row wrap>
        <v-flex xs10 sm8 md6 lg6 offset-xs1 offset-sm2 offset-md3 offset-lg3 fill-height mt-5>
          <v-progress-linear :indeterminate="true" v-if="loading"></v-progress-linear>
          <v-card class="text-xs-center">
            <v-form ref="form" v-model="valid" class="pa-3">
              <v-card-title>
                <v-flex>
                  <h2>Регистрация</h2>
                </v-flex>
              </v-card-title>
              <v-layout row wrap mb-2  mx-3>
                <v-text-field v-model="user.username" :rules="usernameRules" required
                              label="Имя пользователя"></v-text-field>
              </v-layout>
              <v-layout row wrap my-2 mx-3>
                <v-text-field v-model="user.password" :rules="passwordRules" required type="password"
                              label="Пароль"></v-text-field>
              </v-layout>
              <v-layout row wrap my-2 mx-3>
                <v-text-field v-model="user.email" :rules="emailRules" required label="E-mail"></v-text-field>
              </v-layout>
              <v-layout row wrap my-2 mx-3>
                <router-link to="/">Войти</router-link>
              </v-layout>
              <v-layout row wrap align-center justify-end fill-height mt-5>
                <v-flex xs12 sm5 md5 lg5>
                  <v-btn color="primary" class="rounded-btn" @click="register">Зарегистрироваться</v-btn>
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
    name: "Register",
    data() {
      return {
        errorText: null,
        loading: false,
        user: {
          username: "",
          password: "",
          email: ""
        },
        snackbar: false,
        valid: false,
        usernameRules: [
          v => !!v || 'Имя пользователя обязательно'
        ],
        passwordRules: [
          v => !!v || 'Пароль обязателен'
        ],
        emailRules: [
          v => !!v || 'E-mail обязателен',
          v => /.+@.+/.test(v) || 'E-mail должен быть заполнен верно'
        ]
      };
    },
    methods: {
      register() {
        if (this.$refs.form.validate()) {
          this.$http.post('users/create', {
            "username": this.user.username,
            "password": this.user.password,
            "email": this.user.email
          })
            .then(response => {
              this.loading = false;

              this.$router.push('/')
            })
            .catch(error => {
              console.log("error on register");
              this.loading = false;
              this.errorText = 'Ошибка при регистрации'
            });
        }
      }
    }
  }
</script>

<style scoped>
  .rounded-btn {
    border-radius:4px;
  }
</style>

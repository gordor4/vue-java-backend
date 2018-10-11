<template>
  <v-app>
    <v-container class="md-content">
      <v-card row class="text-xs-center">
        <div class="centered-container">
          <v-form ref="form" v-model="valid">
            <v-flex>
              <div class="form">
                <v-card-title>
                  <h1>Регистрация</h1>
                </v-card-title>

                <v-text-field v-model="user.username" :rules="usernameRules" required
                              label="Имя пользователя"></v-text-field>
                <v-text-field v-model="user.password" :rules="passwordRules" required type="password"
                              label="Пароль"></v-text-field>
                <v-text-field v-model="user.email" :rules="emailRules" required label="E-mail"></v-text-field>

                <div class="md-layout mb-5">
                  <router-link to="/">Войти</router-link>
                  <v-btn @click="register">Зарегистрироваться</v-btn>
                </div>
              </div>
            </v-flex>
          </v-form>
        </div>
      </v-card>
    </v-container>
  </v-app>
</template>

<script>
  export default {
    name: "Register",
    data() {
      return {
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
              this.loading = false;
              console.log(error);
            });
        }
      }
    }
  }
</script>

<style scoped>
</style>

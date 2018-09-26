<template>
  <div class="hello">
    <v-app>
      <v-container class="md-content">
        <v-card row class="text-xs-center">
          <div class="centered-container">
            <v-flex>
              <div class="form">
                <v-card-title >
                  <h1>Регистрация</h1>
                </v-card-title>

                <v-text-field v-model="user.username" label="Имя пользователя"></v-text-field>
                <v-text-field v-model="user.password" type="password" label="Пароль"></v-text-field>
                <v-text-field v-model="user.email" label="E-mail"></v-text-field>

                <div class="md-layout mb-5">
                  <router-link to="/">Войти</router-link>
                  <v-btn @click="register">Зарегистрироваться</v-btn>
                </div>
              </div>
            </v-flex>
          </div>
        </v-card>

      </v-container>
    </v-app>
  </div>
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
        }
      };
    },
    methods: {
      register() {
        this.$http.post('users/create', {
          "username": this.user.username,
          "password": this.user.password,
          "email": this.user.email
        })
          .then(response => {
            this.loading = false;
            console.log(response);
          })
          .catch(error => {
            this.loading = false;
            console.log(error);
          });
      }
    }
  }
</script>

<style scoped>
  .hello {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .md-content {
    z-index: 1;
    padding: 40px;
    width: 600px;
    position: relative;
  }

  .form {
    padding: 30px;
  }

  .centered-container {
    margin-top: 50px;
  }

  .loading-overlay {
    z-index: 10;
    top: 0;
    left: 0;
    right: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    background: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>

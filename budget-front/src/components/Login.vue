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
                <a href="/resetpassword">Восстановить пароль</a>
              </div>
              <div class="md-layout mb-5">
                <router-link to="/register">Регистрация</router-link>
                <v-btn @click="auth">Войти</v-btn>
              </div>
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
        }
      };
    },
    methods: {
      auth() {
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
            this.loading = false;
            //TODO: implement error handling
            console.log(error);
          });
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .hello {
    display: flex;
    justify-content: center;
    align-items: center;
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

<template>
  <v-container fluid mt-4>
    <v-layout row wrap>
      <v-flex px-3 mt-4 xs12 sm6 md6 lg6>
        <v-card class="px-4">
          <v-card-title primary-title>
            <div>
              <h3 class="headline mb-0">Личные данные</h3>
            </div>
          </v-card-title>
          <v-card-text>
            <v-layout row wrap>
              <v-text-field v-model="user.firstName" outline label="Фамилия"></v-text-field>
            </v-layout>
            <v-layout row wrap>
              <v-text-field v-model="user.lastName" outline label="Имя"></v-text-field>
            </v-layout>
            <v-layout row wrap>
              <v-text-field v-model="user.secondName" outline label="Отчество"></v-text-field>
            </v-layout>
            <v-layout row wrap>
              <v-flex>
                <v-dialog
                  ref="dialog"
                  v-model="menu"
                  :return-value.sync="user.dateOfBirth"
                  persistent
                  lazy
                  full-width
                  width="290px"
                >
                  <v-text-field slot="activator" outline v-model="user.dateOfBirth" label="День рождения"
                                readonly></v-text-field>
                  <v-date-picker v-model="user.dateOfBirth" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
                    <v-btn flat color="primary" @click="$refs.dialog.save(user.dateOfBirth)">OK</v-btn>
                  </v-date-picker>
                </v-dialog>
              </v-flex>

            </v-layout>
            <v-layout row wrap align-center justify-end fill-height>
              <v-flex xs12 md6 sm6 lg3>
                <v-btn block outline color="primary" @click="updateUser">Сохранить</v-btn>
              </v-flex>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex px-3 mt-4 xs12 sm6 md6 lg6 mt-6>
        <v-card class="px-4">
          <v-card-title primary-title>
            <div>
              <h3 class="headline mb-0">Данные аккаунта</h3>
            </div>
          </v-card-title>
          <v-card-text>
            <v-layout row wrap>
              <v-text-field readonly v-model="userStore.username" outline label="Логин"></v-text-field>
            </v-layout>
            <v-layout row wrap>
              <v-text-field readonly v-model="userStore.email" outline label="Email"></v-text-field>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: "Profile",
    data() {
      return {
        menu: false,
        user: {
          firstName: null,
          lastName: null,
          secondName: null,
          dateOfBirth: new Date().toISOString().substr(0, 10)
        }
      }
    },
    computed: {
      userStore() {
        return this.$store.getters.getUser
      }
    },
    methods: {
      userAvatar() {
        return 'data:image/jpg;base64,' + this.userStore.avatar
      },
      pickFile() {
        this.$refs.image.click()
      },
      onFilePicked(e) {
        const files = e.target.files
        if (files[0] !== undefined) {
          this.imageName = files[0].name;
          if (this.imageName.lastIndexOf('.') <= 0) {
            return
          }
          const fr = new FileReader();
          fr.readAsDataURL(files[0]);
          fr.addEventListener('load', () => {
            this.uploadAvatar(fr.result)
          })
        }
      },
      uploadAvatar(photo) {
        this.$http.post('users/uploadUserPhoto', photo,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(response =>  {

          })
          .catch(error => {

          })
      },
      updateUser() {
        this.$http.post('users/updateUser', this.user)
          .then(response => {

          })
      },
      updateUserData() {
        this.user.firstName = this.userStore.firstName;
        this.user.lastName = this.userStore.lastName;
        this.user.secondName = this.userStore.secondName;
      }
    },
    created() {
      this.updateUserData()
    }
  }

</script>

<style scoped>

</style>

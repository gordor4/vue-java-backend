<template>
  <v-container>
    <v-flex xs12 mt-5>
      <v-card>
        <v-card-title primary-title>
          <div>
            <h3 class="headline mb-0">Профиль пользователя</h3>
          </div>
        </v-card-title>

        <v-card-text>
          <v-layout row wrap pa-3>
            <v-flex
              xs12
              sm3
              md4
              align-center
              justify-center
              layout
              text-xs-center
            >
              <v-avatar
                :size="220"
                color="grey lighten-4"
                @click="pickFile"
              >
                <img :src=userAvatar()>
              </v-avatar>
              <input
                type="file"
                style="display: none"
                ref="image"
                accept="image/*"
                @change="onFilePicked"
              >
            </v-flex>
            <v-flex xs12 sm9 md8 pr-5>
              <v-text-field v-model="user.firstName" outline label="Фамилия"></v-text-field>
              <v-text-field v-model="user.lastName" outline label="Имя"></v-text-field>
              <v-text-field v-model="user.secondName" outline label="Отчество"></v-text-field>
            </v-flex>
          </v-layout>
          <v-layout row wrap pa-3>
            <v-flex>
              <v-btn outline>
                Сохранить
              </v-btn>
            </v-flex>

          </v-layout>
        </v-card-text>

      </v-card>
    </v-flex>

  </v-container>
</template>

<script>
  export default {
    name: "Profile",
    data() {
      return {
        user: {
          firstName: null,
          lastName: null,
          secondName: null,
          avatar: null
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
        console.log(photo)
        this.$http.post('users/uploadUserPhoto',
          photo,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then()
          .catch()
      }
    },
    created() {
    }
  }

</script>

<style scoped>

</style>

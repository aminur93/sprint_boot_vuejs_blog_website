<script>
import {http} from "@/service/HttpService";
import {mapState} from "vuex";

export default {
  name: "AdminLogin",

  data(){
    return{
      email: '',
      password: '',
      loading: true,
    }
  },

  computed: {
    ...mapState({
      message: state => state.success_message,
      errors: state => state.errors,
      success_status: state => state.success_status,
      error_status: state => state.error_status
    })
  },

  watch: {},

  mounted() {
  },

  methods: {
    login: function(){
      try {
        console.log("login")

        let formData = new FormData();

        formData.append('email', this.email);
        formData.append('password', this.password);

        return http().post('v1/auth/login', formData).then(res => {
          if (res.data.statusCode === 200)
          {
            this.$store.commit('SET_TOKEN', res.data.token);
            this.$store.commit('SET_REFRESH_TOKEN', res.data.refreshToken);
            this.$store.commit('SET_USER', res.data.user);
            this.$store.commit('SET_ROLE', res.data.role);
            this.$store.commit('SET_PERMISSION', res.data.permissions);
            this.$store.commit('SET_MENU', res.data.menus);

            this.$swal.fire({
              toast: true,
              position: 'top-end',
              icon: 'success',
              title: res.data.message,
              showConfirmButton: false,
              timer: 1500
            });
            this.$router.push('/dashboard');
          }
        }).catch(err => {
          const errors = err.response.data.errors;
          const errorStatus = err.response.status;
          this.$store.commit("SET_ERROR", { errors, errorStatus });
          throw err;
        })
      }catch (e) {
        if (this.error_status === 422)
        {
          console.log('error');
        }else {
          this.$swal.fire({
            icon: 'error',
            text: 'Oops',
            title: 'Something wen wrong!!!',
          });
        }
      }
    }
  }
}
</script>

<template>
  <div class="login">

    <div class="wrapper">
      <form v-on:submit.prevent="login">
        <h2>Login</h2>

        <div class="input-box">
          <input type="text" name="email" v-model="email" placeholder="email">
          <i class='bx bxs-envelope'></i>
          <p v-if="errors.email" class="error custom_error">{{errors.email[0]}}</p>
        </div>


        <div class="input-box">
          <input type="password" name="password" v-model="password" placeholder="password">
          <i class='bx bxs-lock-alt'></i>
          <p v-if="errors.password" class="error custom_error">{{errors.password[0]}}</p>
        </div>


        <div class="remember-forget">
          <label for="">
            <input type="checkbox">
            Remember me
          </label>
          <a href="">Forget password?</a>
        </div>

        <button type="submit" class="btn">Submit</button>

        <div class="register-link">
          <p>
            Dont have an account?
            <a href="">Register</a>
          </p>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap");

.login{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
  display: flex;
  justify-content: center !important;
  align-items: center !important;
  min-height: 100vh;
  background: url(../../assets/img.jpg) no-repeat;
  background-size: cover;
  background-position: center;
}

.wrapper{
  width: 420px;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, .2);
  backdrop-filter: blur(20px);
  color: #fff;
  border-radius: 10px;
  padding: 30px 40px;
}

.wrapper h2{
  font-size: 36px;
  text-align: center;
}

.wrapper .input-box{
  position: relative;
  width: 100%;
  height: 50px;
  margin: 30px 0;
}

.input-box input {
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
  border: 2px solid rgba(255, 255, 255, .2);
  border-radius: 40px;
  font-size: 16px;
  color: #fff;
  padding: 20px 45px 20px 20px;
}

.input-box input::placeholder{
  color: #fff;
}

.input-box i {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
}

.wrapper .remember-forget{
  display: flex;
  justify-content: space-between;
  font-size: 14.5px;
  margin: -15px 0 15px;
  margin-top: 25px;
}

.remember-forget label input{
  accent-color: #fff;
  margin-right: 3px;
}

.remember-forget a {
  color: #fff;
  text-decoration: none;
}

.remember-forget a:hover{
  text-decoration: underline;
}

.wrapper .btn{
  width: 100%;
  height: 45px;
  background: #fff;
  border: none;
  outline: none;
  border-radius: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, .1);
  cursor: pointer;
  font-size: 16px;
  color: #333;
  font-weight: 600;
}

.wrapper .register-link{
  font-size: 14.5px;
  text-align: center;
  margin: 20px 0 15px;
}

.register-link p a{
  color: #fff;
  text-decoration: none;
  font-weight: 600;
}

.register-link p a:hover{
  text-decoration: underline;
}

.custom_error {
  margin-left: 20px;
}

.error{
  color: red;
}
</style>
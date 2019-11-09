<template>
  <div class="back">
    <div class="login">
      <div class="manage_tip">
        <p style="margin-bottom: 30px;">FD后台管理系统</p>
      </div>
      <el-form :model="loginForm" ref="loginForm">
        <el-form-item style="margin-bottom: 30px;" prop="username">
          <span>账号:</span>
          <input class="text" v-model="loginForm.loginId" placeholder="请输入账号"></input>
        </el-form-item>
        <el-form-item style="margin-bottom: 10px;" prop="password">
          <span>密码:</span>
          <input class="text" type="password" placeholder="请输入密码" v-model="loginForm.password"></input>
        </el-form-item>
        <el-form-item>
          <el-button value="登录" :plain="true" style="width: 70px;height: 40px;" type="primary"
                     @click="submitForm(loginForm)"
                     class="submit_btn">
            <span style="font-size: 16px">登录</span></el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
    import {Message} from 'element-ui';

    export default {
        data() {
            return {
                loginForm: {
                    loginId: '',
                    password: ''
                }
            }
        },
        methods: {
            submitForm: function (e) {
                let that = this;
                let data = {"loginId": e.loginId, "password": e.password}
                $.ajax({
                    url: "http://localhost/FD/manager/login.do",
                    type: 'POST',
                    data: JSON.stringify(data),
                    dataType: "json",
                    async: false,
                    cache: false,
                    processData: false,
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message({
                                message: data.message,
                                type: "success"
                            })
                            that.$router.push('manager')
                        } else {
                            Message({
                                message: data.message,
                                type: "error"
                            })
                        }
                    }
                });

            },
        }

    }
</script>

<style scoped>
  .back {
    height: 100%;
    width: 100%;
    background: url('../image/bg.jpg') no-repeat;
    background-size: cover;
    position: absolute;
    overflow: hidden;
  }

  .login {
    margin: 200px auto auto;
    width: 300px;
    height: 200px;
    font-size: 16px;
    text-align: center;
    /*background-color: #f4f5f7;*/
  }

  .text {
    height: 25px;
    font-size: 18px;
    /*border-radius: 10px;*/
  }
</style>

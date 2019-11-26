<template>
  <div>
    <el-dialog
      title="修改密码"
      :visible.sync="dialogUpdate"
      width="40%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="旧密码:">
          <el-input v-model="form.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码:">
          <el-input v-model="form.newPassword"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updatePassword(form)">修改</el-button>
          <el-button @click="back">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    import {Message} from 'element-ui'

    export default {
        data() {
            return {
                dialogUpdate: true,
                form: {
                    oldPassword: '',
                    newPassword: '',
                }
            }
        }, created() {

        },
        methods: {
            updatePassword(form) {
                let that = this;
                let data = {
                    loginId: sessionStorage.getItem("loginId"),
                    newPassword: form.newPassword,
                    oldPassword: form.oldPassword
                }
                console.log(data)
                $.ajax({
                    url: "http://localhost/FD/manager/updatePassword.do",
                    type: 'POST',
                    data: JSON.stringify(data),
                    dataType: "json",
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message({
                                message: data.message,
                                type: "success"
                            })
                            sessionStorage.removeItem("loginId");
                            sessionStorage.removeItem("token")
                            window.location.replace("http://localhost:8080/#/")
                        } else {
                            Message({
                                message: data.message,
                                type: "error"
                            })
                        }
                    }
                });
            },
            back() {
                let that = this;
                that.$router.push("/homePage")
            }

        }
    }
</script>

<style scoped>

</style>

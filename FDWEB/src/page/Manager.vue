<template>
  <div>
    <el-container style="height: 754px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(255, 255, 255)">
        <el-menu router>
          <el-menu-item index="homePage"><i class="el-icon-s-home"></i>主页</el-menu-item>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-s-custom"></i>店长管理</template>
            <el-menu-item-group>
              <el-menu-item index="managerList">店长列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-user"></i>角色管理</template>
            <el-menu-item-group>
              <el-menu-item index="roleList">角色列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-user-solid"></i>用户管理</template>
            <el-menu-item-group>
              <el-menu-item index="userList">用户列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-medal-1"></i>团队管理</template>
            <el-menu-item-group>
              <el-menu-item index="groupList">团队列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title"><i class="el-icon-wind-power"></i>俱乐部管理</template>
            <el-menu-item-group>
              <el-menu-item index="clubList">俱乐部列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="7">
            <template slot="title"><i class="el-icon-trophy"></i>战队管理</template>
            <el-menu-item-group>
              <el-menu-item index="teamList">战队列表</el-menu-item>
              <el-menu-item index="teamAudit">战队审核</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="8">
            <template slot="title"><img src="../image/PK.png"
                                        style="width: 18px;height: 18.4px;margin: 0px 5px;"/>PK管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="pkList">PK列表</el-menu-item>
              <el-menu-item index="pkAudit">PK审核</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="9">
            <template slot="title"><i class="el-icon-document"></i>系统管理</template>
            <el-menu-item-group>
              <el-menu-item index="serviceLog">系统操作日志</el-menu-item>
              <el-menu-item index="config">系统基础配置</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown trigger="click">
            <i class="el-icon-s-check" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item><a href="http://localhost:8080/#/updatePassword"
                                   style="color: #606266;text-decoration:none">修改密码</a></el-dropdown-item>
              <el-dropdown-item><span @click="exitUser">退出</span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>admin</span>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>
<script>
    export default {
        data() {
            return {}
        },
        methods: {
            exitUser() {
                let that = this;
                sessionStorage.removeItem("token")
                sessionStorage.removeItem("loginId")
                that.$router.go("/")
                // window.location.replace("http://localhost:8080/#/")

            }
        }
    }
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
    padding: 0px;
  }

  .el-aside {
    color: #333;
  }

  .el-main {
    padding: 0px;
  }
</style>

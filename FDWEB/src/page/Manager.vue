<template>
  <div>
    <el-container style="height: 754px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(255, 255, 255)">
        <el-menu router>
          <el-menu-item index="manager"><i class="el-icon-s-home"></i>首页</el-menu-item>
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
              <el-menu-item index="userList">团队列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title"><i class="el-icon-wind-power"></i>俱乐部管理</template>
            <el-menu-item-group>
              <el-menu-item index="userList">俱乐部列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="7">
            <template slot="title"><i class="el-icon-trophy"></i>战队管理</template>
            <el-menu-item-group>
              <el-menu-item index="userList">战队列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown trigger="click">
            <i class="el-icon-s-check" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>修改密码</el-dropdown-item>
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
                console.log(1)
                let that = this;
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

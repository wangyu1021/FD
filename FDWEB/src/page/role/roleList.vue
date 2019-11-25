<template>
  <div>
    <div class="addRoleButton">
      <el-button
        style="width: 70px;padding: 7px 9px;"
        type="primary"
        size="mini"
        @click="openAdd">添加角色
      </el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%;">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="角色名:">
              <span>{{scope.row.name}}</span>
            </el-form-item>
            <el-form-item label="权限列表 :">
              <el-checkbox v-for="item in scope.row.p" v-model="item.checked" :label="item.name" :key="item.id">
                <span @click="updatePermission(item.id,scope.row.id)">{{item.name}}</span>
              </el-checkbox>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="序号"
        type="index"
        width="200">
      </el-table-column>
      <el-table-column
        label="角色"
        prop="name"
      >
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createTime"
      >
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button style="width: 40px;padding: 7px 9px;"
                     size="mini"
                     type="danger"
                     @click="deleteRole(scope.row.id)">删除
          </el-button>
          <el-button style="width: 40px;padding: 7px 9px;"
                     size="mini"
                     type="primary"
                     @click="initForm(scope.row.id)">编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="修改角色名称" :visible.sync="dialogFormVisible">
      <el-form :inline="true" ref="form" label-width="120px" :model="form">
        <el-form-item label="角色名：">
          <el-input v-model="form.name" :value="form.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRole(form)">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="添加角色" :visible.sync="dialogRole">
      <el-form :inline="true" ref="form" label-width="120px" :model="role">
        <el-form-item label="角色名：">
          <el-input v-model="role.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRole = false">取 消</el-button>
        <el-button type="primary" @click="addRole(role)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {Message} from 'element-ui';
    import {Notification} from 'element-ui';

    function getMyDate(str) {
        let oDate = new Date(str),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oHour = oDate.getHours(),
            oMin = oDate.getMinutes(),
            oSen = oDate.getSeconds(),
            oTime = oYear + '-' + addZero(oMonth) + '-' + addZero(oDay) + ' ' + addZero(oHour) + ':' +
                addZero(oMin) + ':' + addZero(oSen);
        return oTime;
    }

    //补零操作
    function addZero(num) {
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }

    export default {
        data() {
            return {
                tableData: [],
                role: {
                    name: ''
                },
                form: {
                    id: '',
                    name: ''
                },
                permission: [],
                rolePermission: [],
                dialogFormVisible: false,
                dialogRole: false
            }
        },
        created() {
            this.initPermision();
            this.initRole();

        },
        methods: {
            openAdd() {
                let that = this;
                that.dialogRole = true;
            },
            initRole() {
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/role/findAllRole.do",
                    type: 'GET',
                    dataType: "json",
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let l = 0; l < that.permission.length; l++) {
                                that.permission[l].checked = false;
                            }
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                let date = getMyDate(obj.createTime);
                                obj.createTime = date;
                                obj.p = JSON.parse(JSON.stringify(that.permission))
                                if (obj.pids != null) {
                                    that.isChecked(obj.p, obj.pids)
                                }
                                that.tableData.push(obj)
                            }
                        } else {
                            Message({
                                message: "请检查您的网络",
                                type: "error"
                            })
                        }
                    }
                });
            },
            isChecked(p, pids) {
                for (let j = 0; j < pids.length; j++) {
                    let pid = pids[j];
                    for (let k = 0; k < p.length; k++) {
                        if (pid == p[k].id) {
                            p[k].checked = true;
                            break;
                        }
                    }
                }
            },
            initPermision() {
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/role/findPermission.do",
                    type: 'GET',
                    dataType: "json",
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.checked = false;
                                that.permission.push(obj)
                            }
                        } else {
                            Message({
                                message: "请检查您的网络",
                                type: "error"
                            })
                        }
                    }
                });
            },
            updatePermission(pId, rId) {
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/role/updatePermission.do",
                    type: 'GET',
                    data: 'roleId=' + rId + "&permissionId=" + pId,
                    dataType: 'json',
                    contentType: 'application/json:charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: '修改权限成功'
                            })
                        }
                    }
                });
            },
            deleteRole(rId) {
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/role/deleteRole.do",
                    type: 'GET',
                    data: 'roleId=' + rId,
                    dataType: 'json',
                    contentType: 'application/json:charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Notification.success({
                                message: '删除成功',
                                onClose: (that.$router.go("roleList"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                })
            },
            initForm(e) {
                let that = this;
                that.dialogFormVisible = true;
                $.ajax({
                    url: 'http://localhost/FD/role/findRoleById.do',
                    type: 'GET',
                    data: 'id=' + e,
                    dataType: 'json',
                    contentType: 'application/json:charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            that.form.name = data.data.name
                            that.form.id = data.data.id
                        }
                    }
                });
            },
            updateRole(e) {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/role/updateRoleById.do',
                    type: 'GET',
                    data: 'id=' + e.id + "&name=" + e.name,
                    dataType: 'json',
                    contentType: 'application/json:charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Notification.success({
                                message: '修改成功',
                                onClose: (that.$router.go("roleList"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            addRole(role) {
                let that = this;
                if (role.name == "" || role.name == null) {
                    Message.error({
                        message: "角色名不能为空"
                    })
                    return
                }
                $.ajax({
                    url: 'http://localhost/FD/role/addRole.do',
                    type: 'GET',
                    data: "name=" + role.name,
                    dataType: 'json',
                    contentType: 'application/json:charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Notification.success({
                                message: '添加成功 ',
                                onClose: (that.$router.go("roleList"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            }
        }
    }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .addRoleButton {
    border-bottom: 1px #e8ecf1 solid;
    padding-bottom: 10px;
    padding-top: 10px;
    margin-left: 10px;
  }
</style>

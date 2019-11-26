<template>
  <div>
    <div class="solt">
      <i class="el-icon-search"></i>
      <span style="font-size: 12px">工号:</span>
      <el-input v-model="jobNumber" placeholder="请输入工号" style="width:200px;"></el-input>
      <span style="font-size: 12px">用户名:</span>
      <el-input v-model="username" placeholder="请输入用户名" style="width:200px"></el-input>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini"
                 @click="soltManager(jobNumber,username)">搜索
      </el-button>
      <el-button
        style="width: 85px;padding: 7px 9px;"
        type="primary"
        size="mini"
        @click="soltAdmin">搜索管理员
      </el-button>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini" @click="resertSolt">重置</el-button>
      <el-button
        style="width: 75px;padding: 7px 9px;"
        type="primary"
        size="mini"
        @click="addManager = true">添加店长
      </el-button>
      <el-button
        style="width: 75px;padding: 7px 9px;"
        type="primary"
        size="mini"
        @click="addAdmin = true">添加管理员
      </el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        style="width: 100%;">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="right" inline label-width="100px" class="demo-table-expand">
              <el-form-item label="昵称 :">
                <span>{{ props.row.nickName}}</span>
              </el-form-item>
              <el-form-item label="手机 :">
                <span>{{ props.row.phone}}</span>
              </el-form-item>
              <el-form-item label="登录账号 :">
                <span>{{ props.row.loginId}}</span>
              </el-form-item>
              <el-form-item label="地址 :">
                <span>{{props.row.address}}</span>
              </el-form-item>
              <el-form-item label="创建时间 :">
                <span>{{props.row.createTime}}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="序号"
          type="index"
          width="100">
        </el-table-column>
        <el-table-column
          label="工号"
          prop="jobNumber">
        </el-table-column>
        <el-table-column
          label="用户名"
          prop="name">
        </el-table-column>
        <el-table-column
          label="所属俱乐部"
          prop="clubId">
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button style="width: 40px;padding: 7px 9px;"
                       size="mini"
                       type="danger"
                       @click="deleteAdmin(scope.row.id)">删除
            </el-button>
            <el-button style="width: 40px;padding: 7px 9px;"
                       size="mini"
                       type="primary"
                       @click="updateDialogFormVisible(scope.row.id)">编辑
            </el-button>
            <el-dialog class="dialogUpdate" title="修改店长信息" :visible.sync="dialogFormVisible">
              <el-form :inline="true" ref="form" label-width="120px" :model="form">
                <el-form-item label="工号：">
                  <el-input v-model="form.jobNumber" :value="form.jobNumber"></el-input>
                </el-form-item>
                <el-form-item label="姓名：">
                  <el-input v-model="form.name" :value="form.name"></el-input>
                </el-form-item>
                <el-form-item label="手机号：">
                  <el-input v-model="form.phone" :value="form.phone"></el-input>
                </el-form-item>
                <el-form-item label="昵称：">
                  <el-input v-model="form.nickName" :value="form.nickName"></el-input>
                </el-form-item>
                <el-form-item label="详细地址：">
                  <el-input v-model="form.address" :value="form.address"></el-input>
                </el-form-item>
                <el-form-item label="所属俱乐部：">
                  <el-select v-model="form.clubId" placeholder="请选择俱乐部">
                    <el-option v-for="item in club"
                               :key="item.id"
                               :value="item.id"
                               :label="item.label"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateManager(form)">确 定</el-button>
              </div>
            </el-dialog>
            <el-dialog class="dialogAdd" title="添加店长" :visible.sync="addManager">
              <el-form :inline="true" ref="form" :model="manager" label-width="120px">
                <el-form-item label="店长姓名:">
                  <el-input v-model="manager.name"></el-input>
                </el-form-item>
                <el-form-item label="工号:">
                  <el-input v-model="manager.jobNumber"></el-input>
                  <el-tooltip class="item" effect="dark" content="注意工号即为登录账号" placement="right">
                    <i class="el-icon-info"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="昵称:">
                  <el-input v-model="manager.nickName"></el-input>
                </el-form-item>
                <el-form-item label="手机号:">
                  <el-input v-model="manager.phone"></el-input>
                </el-form-item>
                <el-form-item label="登录密码:">
                  <el-input type="password" maxlength=16 v-model="manager.password"></el-input>
                  <el-tooltip class="item" effect="dark" content="密码不小于6位数，不大于16位数" placement="right">
                    <i class="el-icon-info"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label-width="101.85px" label="地址:">
                  <el-cascader
                    size="large"
                    :options="options"
                    v-model="selectedOptions"
                    @change="handleChange(options,selectedOptions)">
                  </el-cascader>
                </el-form-item>
                <el-form-item label="俱乐部:">
                  <el-select v-model="manager.clubId" placeholder="请选择俱乐部">
                    <el-option v-for="item in club"
                               :key="item.id"
                               :value="item.id"
                               :label="item.name"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <div style="margin-left:275px;margin-top: 30px">
                  <el-form-item>
                    <el-button type="primary" @click="insertManager(manager)">立即创建</el-button>
                    <el-button @click="addManager = false">取消</el-button>
                  </el-form-item>
                </div>
              </el-form>
            </el-dialog>
            <el-dialog class="dialogAdd" title="添加管理员" :visible.sync="addAdmin">
              <el-form :inline="true" ref="form" :model="admin" label-width="120px">
                <el-form-item label="管理员姓名:">
                  <el-input v-model="admin.name"></el-input>
                </el-form-item>
                <el-form-item label="工号:">
                  <el-input v-model="admin.jobNumber"></el-input>
                  <el-tooltip class="item" effect="dark" content="注意工号即为登录账号" placement="right">
                    <i class="el-icon-info"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="昵称:">
                  <el-input v-model="admin.nickName"></el-input>
                </el-form-item>
                <el-form-item label="手机号:">
                  <el-input v-model="admin.phone"></el-input>
                </el-form-item>
                <el-form-item label="登录密码:">
                  <el-input type="password" maxlength=16 v-model="admin.password"></el-input>
                  <el-tooltip class="item" effect="dark" content="密码不小于6位数，不大于16位数" placement="right">
                    <i class="el-icon-info"></i>
                  </el-tooltip>
                </el-form-item>
                <el-form-item label-width="101.85px" label="地址:">
                  <el-cascader
                    size="large"
                    :options="options"
                    v-model="selectedOptions"
                    @change="handleChange(options,selectedOptions)">
                  </el-cascader>
                </el-form-item>
                <div style="margin-left:275px;margin-top: 30px">
                  <el-form-item>
                    <el-button type="primary" @click="insertAdmin(admin)">立即创建</el-button>
                    <el-button @click="addAdmin = false">取消</el-button>
                  </el-form-item>
                </div>
              </el-form>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
    import {Message} from 'element-ui';
    import {regionData} from 'element-china-area-data'

    function getMyDate(str) {
        var oDate = new Date(str),
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
                addManager: false,
                username: '',
                jobNumber: '',
                tableData: [],
                dialogFormVisible: false,
                addAdmin: false,
                form: {
                    id: '',
                    jobNumber: '',
                    name: '',
                    address: '',
                    nickName: '',
                    phone: '',
                    clubId: ''
                },
                club: [],
                manager: {
                    address: '',
                    clubId: '',
                    jobNumber: '',
                    name: '',
                    nickName: '',
                    password: '',
                    phone: ''
                },
                admin: {
                    address: '',
                    clubId: 0,
                    jobNumber: '',
                    name: '',
                    nickName: '',
                    password: '',
                    phone: ''
                },
                options: regionData,
                selectedOptions: [],
                area: ''
            }
        },
        created() {
            this.initClub();
            this.initTable();
        },
        methods: {
            initTable() {
                //该方法用于查询店长信息
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/manager/findManager.do",
                    type: 'GET',
                    dataType: "json",
                    async: false,
                    cache: false,
                    processData: false,
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                if (obj.clubId != 0) {
                                    let clubName = that.findClub(obj.clubId);
                                    obj.clubId = clubName;
                                } else {
                                    obj.clubId = '管理员不属于任何俱乐部'
                                }
                                let dateTime = getMyDate(parseInt(obj.createTime));
                                obj.createTime = dateTime;
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
            findClub(id) {
                //该方法用于查询店长所属俱乐部
                let that = this;
                let name;
                $.ajax({
                    url: 'http://localhost/FD/club/findClubById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            name = data.data.name
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
                return name;
            },
            //初始化查询条件
            initClub() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/club/findAllClub',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                that.club.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            deleteAdmin(e) {
                //删除店长
                let that = this;
                $.ajax({
                    url: "http://localhost/FD/manager/deleteManagerById.do",
                    type: 'GET',
                    data: "id=" + e,
                    dataType: "json",
                    success: function (data) {
                        if (data.state == 1) {
                            alert("删除成功");
                            that.$router.go("managerList")
                        } else {
                            Message({
                                showClose: true,
                                message: data.message,
                                type: "error"
                            })
                        }
                    }
                });
            },
            updateDialogFormVisible(e) {
                let that = this;
                //打开表格修改
                that.dialogFormVisible = true;
                //根据id获取店长信息
                $.ajax({
                    url: "http://localhost/FD/manager/findManagerById.do",
                    type: 'GET',
                    data: 'id=' + e,
                    dataType: "json",
                    success: function (data) {
                        if (data.state == 1) {
                            that.form.id = data.data.id;
                            that.form.jobNumber = data.data.jobNumber;
                            that.form.name = data.data.name;
                            that.form.nickName = data.data.nickName;
                            that.form.phone = data.data.phone;
                            that.form.clubId = that.findClub(data.data.clubId);
                            that.form.address = data.data.address;
                        } else {
                            Message({
                                showClose: true,
                                message: data.message,
                                type: "error"
                            })
                        }
                    }
                });
            },
            updateManager(e) {
                //该方法用于修改店长信息
                if (e.jobNumber == "" || e.jobNumber == null) {
                    Message.error({
                        message: '工号不能为空'
                    })
                    return;
                } else if (e.name == "" || e.name == null) {
                    Message.error({
                        message: '姓名不能为空'
                    })
                    return;
                } else if (e.nickName == "" || e.nickName == null) {
                    Message.error({
                        message: '昵称不能为空'
                    })
                    return;
                } else if (e.address == "" || e.address == null) {
                    Message.error({
                        message: '地址不能为空'
                    })
                    return;
                } else if (e.clubId == "" || e.clubId == null) {
                    Message.error({
                        message: '请选择所属俱乐部'
                    })
                    return;
                } else if (e.phone == "" || e.phone == null) {
                    Message.error({
                        message: '手机号不能为空'
                    })
                    return;
                }
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/manager/updateManager.do',
                    data: JSON.stringify({
                        id: e.id,
                        address: e.address,
                        clubId: e.clubId,
                        jobNumber: e.jobNumber,
                        name: e.name,
                        nickName: e.nickName,
                        phone: e.phone
                    }),
                    dataType: 'json',
                    type: 'POST',
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data) {
                        if (data.state == 1) {
                            alert(data.message)
                            that.$router.go("managerList")
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            insertManager(e) {
                //添加店长
                let that = this;
                e.address = that.area;
                if (e.name == "" || e.name == null) {
                    Message.error({
                        message: '姓名不能为空'
                    })
                    return;
                } else if (e.jobNumber == "" || e.jobNumber == null) {
                    Message.error({
                        message: '工号不能为空'
                    })
                    return;
                } else if (e.nickName == "" || e.nickName == null) {
                    Message.error({
                        message: '昵称不能为空'
                    })
                    return;
                } else if (e.phone == "" || e.phone == null) {
                    Message.error({
                        message: '手机号不能为空'
                    })
                    return;
                } else if (e.password == "" || e.password == null) {
                    Message.error({
                        message: '密码不能为空'
                    })
                    return;
                } else if (e.password.length < 6) {
                    Message.error({
                        message: '密码长度不能小于6位'
                    })
                    return;
                } else if (e.address == "" || e.address == null) {
                    Message.error({
                        message: '地址不能为空'
                    })
                    return;
                } else if (e.clubId == "" || e.clubId == null) {
                    Message.error({
                        message: '请选择所属俱乐部'
                    })
                    return;
                }
                $.ajax({
                    url: 'http://localhost/FD/manager/addManager.do',
                    type: 'POST',
                    data: JSON.stringify(e),
                    dataType: '',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: '添加成功',
                                onClose: (that.$router.go("managerList"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            insertAdmin(e) {
                //添加管理员
                let that = this;
                e.address = that.area;
                if (e.name == "" || e.name == null) {
                    Message.error({
                        message: '姓名不能为空'
                    })
                    return;
                } else if (e.jobNumber == "" || e.jobNumber == null) {
                    Message.error({
                        message: '工号不能为空'
                    })
                    return;
                } else if (e.nickName == "" || e.nickName == null) {
                    Message.error({
                        message: '昵称不能为空'
                    })
                    return;
                } else if (e.phone == "" || e.phone == null) {
                    Message.error({
                        message: '手机号不能为空'
                    })
                    return;
                } else if (e.password == "" || e.password == null) {
                    Message.error({
                        message: '密码不能为空'
                    })
                    return;
                } else if (e.password.length < 6) {
                    Message.error({
                        message: '密码长度不能小于6位'
                    })
                    return;
                } else if (e.address == "" || e.address == null) {
                    Message.error({
                        message: '地址不能为空'
                    })
                    return;
                }
                $.ajax({
                    url: 'http://localhost/FD/manager/addManager.do',
                    type: 'POST',
                    data: JSON.stringify(e),
                    dataType: '',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: '添加成功',
                                onClose: (that.$router.go("managerList"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            handleChange(value, res) {
                let that = this;
                let province;
                let city;
                let district;
                for (let i = 0; i < value.length; i++) {
                    if (value[i].value == res[0]) {
                        province = value[i].label;
                        for (let j = 0; j < value[i].children.length; j++) {
                            if (value[i].children[j].value == res[1]) {
                                city = value[i].children[j].label;
                                for (let k = 0; k < value[i].children[j].children.length; k++) {
                                    if (value[i].children[j].children[k].value == res[2]) {
                                        district = value[i].children[j].children[k].label;
                                    }
                                }
                            }
                        }
                    }
                }
                that.area = province + city + district
            },
            soltManager(jobNumber, username) {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/manager/findManagerByJobNumOrName.do',
                    type: 'GET',
                    data: "name=" + username + "&jobNumber=" + jobNumber,
                    dataType: 'json',
                    contentType: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: data.message
                            })
                            that.tableData = []
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                if (obj.clubId != 0) {
                                    let clubName = that.findClub(obj.clubId);
                                    obj.clubId = clubName;
                                } else {
                                    obj.clubId = '管理员不属于任何俱乐部'
                                }
                                let dateTime = getMyDate(parseInt(obj.createTime));
                                obj.createTime = dateTime;
                                that.tableData.push(obj)
                            }
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }

                })
            },
            soltAdmin() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/manager/findAdmin.do',
                    type: 'GET',
                    dataType: 'json',
                    contentType: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: "查询成功"
                            })
                            that.tableData = []
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.clubId = '管理员不属于任何俱乐部'
                                let dateTime = getMyDate(parseInt(obj.createTime));
                                obj.createTime = dateTime;
                                that.tableData.push(obj)
                            }
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }

                })
            },
            resertSolt() {
                let that = this;
                that.tableData = []
                that.jobNumber = ''
                that.username = ''
                that.initTable()
            }
        }
    }
</script>

<style scoped>
  .dialogAdd .el-input {
    width: 221.4px;
  }

  .dialogUpdate .el-input {
    width: 221.4px;
  }

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

  .solt {
    border-bottom: 1px #e8ecf1 solid;
    padding-bottom: 10px;
    padding-top: 10px;
  }


</style>

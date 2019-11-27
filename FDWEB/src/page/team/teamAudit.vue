<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="战队描述：">
              <span>{{ props.row.description}}</span>
            </el-form-item>
            <div v-if="!props.row.reason"></div>
            <div v-else>
              <el-form-item label="拒绝原因：">
                <span>{{ props.row.reason}}</span>
              </el-form-item>
            </div>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="战队名"
        prop="name">
      </el-table-column>
      <el-table-column
        label="队长"
        prop="captain">
      </el-table-column>
      <el-table-column
        label="队长手机号"
        prop="captainPhone">
      </el-table-column>
      <el-table-column
        label="地址"
        prop="address">
      </el-table-column>
      <el-table-column
        label="运动类型"
        prop="motionType">
      </el-table-column>
      <el-table-column
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="open(scope.row)" :disabled="scope.row.status!=0" size="medium"><span
            :id="scope.row.id"></span>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="请填写拒绝原因"
      :visible.sync="dialogReject"
      width="30%">
      <span>原因：</span>
      <el-input type="textarea" v-model="reject"></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogReject = false">取 消</el-button>
    <el-button type="primary" @click="addReject">确 定</el-button></span>
    </el-dialog>
  </div>
</template>

<script>
    import {Message} from 'element-ui'

    export default {
        data() {
            return {
                tableData: [],
                dialogReject: false,
                reject: '',
                auditId: ''
            }
        },
        created() {
            this.initTable();
        },
        methods: {
            initTable() {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/findAllTeamAudit',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj = that.StatusText(obj);
                                obj.motionType = that.findMotionType(obj.motionType);
                                that.tableData.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '请检查网络信息'
                            })
                        }
                    }
                });
            }, findMotionType(id) {
                let that = this;
                let motionType;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/motionType/findMotionTypeById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            motionType = data.data.name;
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
                return motionType;
            },
            StatusText(obj) {
                let that = this;
                setTimeout(function () {
                    if (obj.status === 0) {
                        document.getElementById(obj.id).innerText = "未审核";
                    } else if (obj.status === 1) {
                        document.getElementById(obj.id).innerText = "已通过";
                    } else {
                        document.getElementById(obj.id).innerText = "已拒绝";
                        let reason = that.findReject(obj.id);
                        obj['reason'] = reason;
                    }
                }, 300)
                return obj;
            },
            open(obj) {
                let that = this;
                that.$confirm('是否同意该战队通过审核', '提示', {
                    confirmButtonText: '通过',
                    cancelButtonText: '拒绝',
                    type: 'warning'
                }).then(() => {
                    that.addTeam(obj);
                }).catch(() => {
                    that.openReject(obj.id);
                });
            },
            openReject(id) {
                let that = this;
                that.auditId = '';
                that.auditId = id;
                that.dialogReject = true;
            },
            addTeam(obj) {
                let that = this;
                let form = {
                    address: obj.address,
                    captain: obj.captain,
                    captainPhone: obj.captainPhone,
                    logo: obj.logo,
                    motionType: that.findMotionTypeByName(obj.motionType),
                    name: obj.name
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/addTeam',
                    type: 'POST',
                    data: JSON.stringify(form),
                    dataType: 'json',
                    async: false,
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            that.updateAuditStatus(obj.id, 1)
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            addReject() {
                let that = this;
                if (that.reject == "" || that.reject == null) {
                    Message.error({
                        message: '请填写拒绝原因'
                    })
                    return;
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/adddReject',
                    type: 'GET',
                    data: 'auditId=' + that.auditId + "&reason=" + that.reject,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            that.updateAuditStatus(that.auditId, 2);
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            updateAuditStatus(id, status) {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/updateTeamAuditStatus',
                    type: 'GET',
                    data: 'id=' + id + "&status=" + status,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: "审核成功",
                                onClose: (that.$router.go("/teamAudit"))
                            })
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            findMotionTypeByName(name) {
                let that = this;
                let motionTypeId;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/motionType/findMotionTypeByName',
                    type: 'GET',
                    data: 'name=' + name,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            motionTypeId = data.data.id;
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
                return motionTypeId;
            },
            findReject(id) {
                let that = this;
                let reason;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/findRejectByAuditId',
                    data: 'auditId=' + id,
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        reason = data.data.reason;
                    }
                });
                return reason;
            }
        }
    }
</script>

<style scoped>

</style>

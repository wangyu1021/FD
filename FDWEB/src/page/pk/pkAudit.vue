<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="PK类型：">
              <span>{{ props.row.motionType}}</span>
            </el-form-item>
            <el-form-item label="PK地址：">
              <span>{{ props.row.address}}</span>
            </el-form-item>
            <el-form-item label="PK时间：">
              <span>{{ props.row.timer}}</span>
            </el-form-item>
            <el-form-item label="发起时间：">
              <span>{{ props.row.createTime}}</span>
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
        label="发起战队编号"
        prop="initiatorId">
      </el-table-column>
      <el-table-column
        label="发起战队"
        prop="initiatorName">
      </el-table-column>
      <el-table-column
        label="被挑战战队编号"
        prop="groupId">
      </el-table-column>
      <el-table-column
        label="被挑战战队"
        prop="groupName">
      </el-table-column>
      <el-table-column
        label="发起人"
        prop="initiatorCaptain">
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
                    url: 'http://localhost/FD/pk/findAllPkAudit',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj = that.StatusText(obj);
                                obj.timer=getMyDate(obj.timer);
                                obj.createTime=getMyDate(obj.createTime)
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
                    url: 'http://localhost/FD/motionType/findMotionTypeById',
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
                that.$confirm('是否同意该PK通过审核', '提示', {
                    confirmButtonText: '通过',
                    cancelButtonText: '拒绝',
                    type: 'warning'
                }).then(() => {
                    that.addPk(obj);
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
            addPk(obj) {
                let that = this;
                let pkBo = {
                    address: obj.address,
                    createTime: new Date(obj.createTime),
                    groupId:obj.groupId,
                    groupName: obj.groupName,
                    initiatorCaptain: obj.initiatorCaptain,
                    initiatorId: obj.initiatorId,
                    initiatorName: obj.initiatorName,
                    motionType: that.findMotionTypeByName(obj.motionType),
                    status: 3,
                    timer: new Date(obj.timer)
                };
                console.log(pkBo)
                $.ajax({
                    url: 'http://localhost/FD/pk/addPk',
                    type: 'POST',
                    data: JSON.stringify(pkBo),
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
                    url: 'http://localhost/FD/pk/addReason',
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
                let form={
                    id:id,
                    status:status
                }
                $.ajax({
                    url: 'http://localhost/FD/pk/updatePkAuditStatus',
                    type: 'POST',
                    data: JSON.stringify(form),
                    dataType: 'json',
                    async: false,
                    contentType:'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: "审核成功",
                                onClose: (that.$router.go("/pkAudit"))
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
                    url: 'http://localhost/FD/motionType/findMotionTypeByName',
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
                    url: 'http://localhost/FD/pk/findRejectByAuditId',
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

</style>

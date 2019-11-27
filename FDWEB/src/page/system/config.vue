<template>
  <div>
    <div class="addConfigButton">
      <el-button
        style="width: 100px;padding: 7px 9px;"
        type="primary"
        size="mini"
        @click="addForm = true">添加配置信息
      </el-button>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="code"
        label="配置标识"
        width="180">
      </el-table-column>
      <el-table-column
        prop="content"
        label="配置内容">
      </el-table-column>
      <el-table-column
        prop="extra"
        label="额外配置">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="修改时间">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="props">
          <el-button style="width: 40px;padding: 7px 9px;"
                     size="mini"
                     type="danger"
                     @click="deleteConfig(props.row.id)">删除
          </el-button>
          <el-button style="width: 40px;padding: 7px 9px;"
                     size="mini"
                     type="primary"
                     @click="initForm(props.row.id)">编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog class="dialogUpdate" title="修改配置信息" :visible.sync="updateForm">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="配置标识">
          <el-input v-model="form.code"></el-input>
        </el-form-item>
        <el-form-item label="配置内容">
          <el-input type="textarea" v-model="form.content"></el-input>
        </el-form-item>
        <el-form-item label="额外配置">
          <el-input type="textarea" v-model="form.extra"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateConfig(form)">修改</el-button>
          <el-button @click="updateForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog class="dialogUpdate" title="添加配置信息" :visible.sync="addForm">
      <el-form ref="form" :model="config" label-width="80px">
        <el-form-item label="配置标识">
          <el-input v-model="config.code"></el-input>
        </el-form-item>
        <el-form-item label="配置内容">
          <el-input type="textarea" v-model="config.content"></el-input>
        </el-form-item>
        <el-form-item label="额外配置">
          <el-input type="textarea" v-model="config.extra"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addConfig(config)">添加</el-button>
          <el-button @click="addForm = false">取消</el-button>
        </el-form-item>
      </el-form>
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
                form: {},
                updateForm: false,
                addForm: false,
                config: {}
            }
        },
        created() {
            this.initTable();
        },
        methods: {
            initTable() {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/admin/findAllConfig',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.createTime = getMyDate(obj.createTime);
                                obj.updateTime = getMyDate(obj.updateTime);
                                that.tableData.push(obj);
                            }
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            deleteConfig(id) {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/admin/deleteConfigById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: data.message,
                                onClose: (that.$router.go("/config"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            initForm(id) {
                let that = this;
                that.updateForm = true;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/admin/findConfigById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            that.form = data.data;
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            updateConfig(form) {
                let that = this;
                let configBo = {
                    id: form.id,
                    code: form.code,
                    content: form.content,
                    extra: form.extra
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/admin/updateConfigById',
                    type: 'POST',
                    data: JSON.stringify(configBo),
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: data.message,
                                onClose: (that.$router.go("/config"))
                            })
                        } else {
                            Message.error({
                                message: data.message
                            })
                        }
                    }
                });
            },
            addConfig(form) {
                let that = this;
                let config = {
                    code: form.code,
                    content: form.content,
                    extra: form.extra
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/admin/addConfig',
                    type: 'POST',
                    data: JSON.stringify(config),
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: data.message,
                                onClose: (that.$router.go("/config"))
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
  .addConfigButton {
    border-bottom: 1px #e8ecf1 solid;
    padding-bottom: 10px;
    padding-top: 10px;
    margin-right: 10px;
    float: right;
  }
</style>

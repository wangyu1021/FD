<template>
  <div>
    <div>
      <i class="el-icon-search"></i>
      <span>地址:</span>
      <el-cascader clearable
                   size="large"
                   :options="options"
                   v-model="selectedOptions"
                   @change="handleChange(options,selectedOptions)">
      </el-cascader>
      <span style="font-size: 12px">运动类型:</span>
      <el-select clearable v-model="motionTypeId" placeholder="请选择">
        <el-option
          v-for="item in motionType"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <span>手机号：</span>
      <el-input v-model="phone" style="width:204.6px" placeholder="请输入"></el-input>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini"
                 @click="slotTeam">搜索
      </el-button>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini" @click="resetSolt()">重置</el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          prop="name"
          label="战队名称"
          width="250">
        </el-table-column>
        <el-table-column
          prop="captain"
          label="队长"
          width="150">
        </el-table-column>
        <el-table-column
          prop="captainPhone"
          label="队长手机号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="motionType"
          label="运动类型"
          width="80">
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
          width="280">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="200">
        </el-table-column>
        <el-table-column
          label="状态"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" @click="updateStatus(scope.row.id)" size="small"><span :id="scope.row.id"></span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="openDialog(scope.row.id,scope.row.status)" type="text" size="small">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog class="dialogForm" title="战队信息" :visible.sync="dialogForm">
        <el-table
          :data="form"
          border
          style="width: 100%">
          <el-table-column
            prop="logo"
            label="战队LOGO"
            width="110">
            <el-avatar shape="square" :size="50" :src="ImageURL"></el-avatar>
          </el-table-column>
          <el-table-column
            prop="name"
            label="战队名称"
            width="150">
          </el-table-column>
          <el-table-column
            prop="groupNum"
            label="成长值">
          </el-table-column>
          <el-table-column
            prop="victory"
            label="胜场">
          </el-table-column>
          <el-table-column
            prop="lose"
            label="败场">
          </el-table-column>
          <el-table-column
            prop="rate"
            label="胜率">
          </el-table-column>
          <el-table-column
            prop="pkNum"
            label="总场次">
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>

<script>
    import {Message} from "element-ui"
    import {regionData, CodeToText} from 'element-china-area-data';

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
                motionType: [],
                dialogForm: false,
                form: [],
                ImageURL: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
                selectedOptions: [],
                options: regionData,
                areaAddress: '',
                motionTypeId:'',
                phone:''
            }
        },
        created() {
            this.intiMotionType();
            this.initTable();
        },
        methods: {
            intiMotionType() {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/motionType/findAllMotionType',
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                that.motionType.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            initTable() {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/findAllTeam',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.createTime = getMyDate(obj.createTime);
                                that.StatusText(obj);
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
            },
            StatusText(obj) {
                setTimeout(function () {
                    if (obj.status == 0) {
                        document.getElementById(obj.id).innerText = "解冻";
                    } else {
                        document.getElementById(obj.id).innerText = "冻结";
                    }
                }, 300)
            },
            updateStatus(id) {
                let that = this;
                let status;
                let a = document.getElementById(id).innerText;
                if (a === "解冻") {
                    status = 1
                } else if (a === "冻结") {
                    status = 0
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/updateStatus',
                    type: 'GET',
                    data: "id=" + id + "&status=" + status,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            that.$router.go('/clubList')
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            findMotionType(id) {
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
            openDialog(id, status) {
                let that = this;
                if (status == 0) {
                    Message.error({
                        message: '冻结中，无法进行其他操作'
                    })
                    return;
                }
                that.dialogForm = true;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/findTeamById',
                    type: 'GET',
                    data: "id=" + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        that.form = []
                        if (data.state == 1) {
                            that.form.push(data.data);
                            if (that.form[0].pkNum != 0) {
                                that.form[0].rate = ((that.form[0].victory / that.form[0].pkNum).toFixed(2) * 100) + "%"
                            } else {
                                that.form[0].rate = 0
                            }
                            console.log(that.form)
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
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
                province = CodeToText[res[0]];
                city = CodeToText[res[1]];
                district = CodeToText[res[2]];
                that.areaAddress = province + city + district
            },
            slotTeam(){
                let that = this;
                let form={
                    address:that.areaAddress,
                    captainPhone: that.phone,
                    motionType: that.motionTypeId,
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/team/findTeamByCondition',
                    type: 'POST',
                    data: JSON.stringify(form),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        that.tableData = [];
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.createTime = getMyDate(obj.createTime);
                                that.StatusText(obj);
                                obj.motionType = that.findMotionType(obj.motionType);
                                that.tableData.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            resetSolt() {
                let that = this;
                that.areaAddress = '';
                that.selectedOptions='';
                that.motionTypeId='';
                that.tableData = [];
                that.phone='';
                that.initTable();
            }

        }
    }
</script>

<style scoped>

</style>

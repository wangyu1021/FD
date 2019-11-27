<template>
  <div>
    <div>
      <i class="el-icon-search"></i>
      <span>PK地区:</span>
      <el-cascader clearable
                   size="large"
                   :options="options"
                   v-model="selectedOptions"
                   @change="handleChange(options,selectedOptions)">
      </el-cascader>
      <span style="margin-left: 24px" >运动类型:</span>
      <el-select clearable v-model="motionTypeId" placeholder="请选择">
        <el-option
          v-for="item in motionType"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <span style="margin-left: 29px" >状态：</span>
      <el-select clearable v-model="status" placeholder="请选择">
        <el-option
          v-for="item in statuss"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <span class="demonstration">日期：</span>
      <el-date-picker
        v-model="timer"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="选择日期">
      </el-date-picker><br>
      <span style="margin-left: 9px">发起战队:</span>
      <el-input style="width: 221px;" v-model="initiatorName" placeholder="请输入内容"></el-input>
      <span style="margin-left: 9px">被挑战战队:</span>
      <el-input style="width: 221px;" v-model="groupName" placeholder="请输入内容"></el-input>
      <span style="margin-left: 9px">获胜战队:</span>
      <el-input style="width: 221px;" v-model="victoryTeam" placeholder="请输入内容"></el-input>
      <span style="margin-left: 100px" ></span>
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
          prop="initiatorName"
          label="发起战队"
          width="250">
        </el-table-column>
        <el-table-column
          prop="initiatorCaptain"
          label="发起队长"
          width="150">
        </el-table-column>
        <el-table-column
          prop="groupName"
          label="被挑战战队"
          width="250">
        </el-table-column>
        <el-table-column
          prop="motionType"
          label="运动类型"
          width="80">
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
          width="180">
        </el-table-column>
        <el-table-column
          prop="timer"
          label="PK时间"
          width="200">
        </el-table-column>
        <el-table-column
          label="获胜战队"
          width="124">
          <template slot-scope="scope">
            <div v-if="!scope.row.victoryTeam">
              敬请期待
            </div>
            <div v-else>
               <span>
              {{scope.row.victoryTeam}}
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="状态"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" @click="updateStatus(scope.row.id)"
                       :disabled="scope.row.status==2 || scope.row.status==3 || scope.row.status==4 " size="medium">
              <span :id="scope.row.id"></span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog class="dialogForm" title="选择获胜战队" :visible.sync="dialogVictory" width="18%">
        <el-select v-model="victory" placeholder="请选择获胜战队">
          <el-option :label="form.initiatorName" :value="form.initiatorName"></el-option>
          <el-option :label="form.groupName" :value="form.groupName"></el-option>
        </el-select>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVictory = false">取 消</el-button>
          <el-button type="primary" @click="addVictoryTeam(form.id)">确 定</el-button>
        </div>
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
                form: {},
                selectedOptions: [],
                options: regionData,
                areaAddress: '',
                motionTypeId: '',
                dialogVictory: false,
                victory: '',
                status: '',
                timer:'',
                statuss:[
                    {
                        id:0,
                        name:'未开始'
                    },
                    {
                        id:1,
                        name:'进行中'
                    },
                    {
                        id:2,
                        name:'已结束'
                    },
                    {
                        id:3,
                        name:'备战中'
                    },
                    {
                        id:4,
                        name:'拒绝挑战'
                    }
                ],
                initiatorName:'',
                groupName:'',
                victoryTeam:''
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
                    url: 'https://www.orchardteam.com/FD/pk/findAllPk',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.timer = getMyDate(obj.timer);
                                obj.createTime = getMyDate(obj.createTime)
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
                        document.getElementById(obj.id).innerText = "备战中";
                    } else if (obj.status == 1) {
                        document.getElementById(obj.id).innerText = "进行中";
                    } else if (obj.status == 2) {
                        document.getElementById(obj.id).innerText = "已结束";
                    } else if (obj.status == 3) {
                        document.getElementById(obj.id).innerText = "待接受";
                    } else {
                        document.getElementById(obj.id).innerText = "拒绝挑战";
                    }
                }, 300)
            },
            updateStatus(id) {
                let that = this;
                let status;
                let a = document.getElementById(id).innerText;
                if (a === "未开始") {
                    status = 1
                    let form = {
                        id: id,
                        status: status
                    }
                    that.statusUtil(form);
                } else if (a === "进行中") {
                    that.addVictory(id);
                }

            },
            addVictory(id) {
                let that = this;
                that.$confirm('PK是否结束', '提示', {
                    confirmButtonText: '是',
                    cancelButtonText: '否',
                    type: 'warning'
                }).then(() => {
                    that.dialogVictory = true;
                    that.findPkById(id);
                }).catch(() => {

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
            findPkById(id) {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/pk/findPkById',
                    type: 'GET',
                    data: "id=" + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        that.form = {}
                        if (data.state == 1) {
                            that.form = data.data;
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
            slotTeam() {
                let that = this;
                let form = {
                    address: that.areaAddress,
                    motionType: that.motionTypeId,
                    status: that.status,
                    timer:that.timer,
                    initiatorName:that.initiatorName,
                    groupName:that.groupName,
                    victoryTeam:that.victoryTeam
                }
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/pk/findPkByCondition',
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
                that.selectedOptions = '';
                that.motionTypeId = '';
                that.tableData = [];
                that.status = '';
                that.timer='';
                that.initiatorName='';
                that.groupName='';
                that.victoryTeam='';
                that.initTable();
            },
            addVictoryTeam(id) {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/pk/addVictoryTeam',
                    type: 'GET',
                    data: "id=" + id + "&victoryTeam=" + that.victory,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            let form = {
                                id: id,
                                status: 2
                            }
                            that.statusUtil(form)
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            statusUtil(form) {
                let that = this;
                $.ajax({
                    url: 'https://www.orchardteam.com/FD/pk/updatePkStatus',
                    type: 'POST',
                    data: JSON.stringify(form),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            that.$router.go("/pkList")
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>

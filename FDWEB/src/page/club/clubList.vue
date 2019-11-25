<template>
  <div>
    <div style="margin-bottom: 10px;margin-top: 10px;">
      <i class="el-icon-search"></i>
      <span>地址:</span>
      <el-cascader clearable
                   size="large"
                   :options="options"
                   v-model="selectedOptions"
                   @change="handleChange(options,selectedOptions)">
      </el-cascader>
      <span style="font-size: 12px">时间段:</span>
      <el-select clearable v-model="timeId" placeholder="请选择">
        <el-option
          v-for="item in time"
          :key="item.id"
          :label="item.time"
          :value="item.id">
        </el-option>
      </el-select>
      <span style="font-size: 12px">运动类型:</span>
      <el-select clearable v-model="motionTypeId" placeholder="请选择">
        <el-option
          v-for="item in motionType"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini"
                 @click="soltClub">搜索
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
          label="俱乐部名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="managerName"
          label="店长"
          width="80">
        </el-table-column>
        <el-table-column
          prop="managerPhone"
          label="店长手机"
          width="120">
        </el-table-column>
        <el-table-column
          prop="motionType"
          label="运动类型"
          width="100">
        </el-table-column>
        <el-table-column
          prop="timeId"
          label="时间段"
          width="100">
        </el-table-column>
        <el-table-column
          prop="capacity"
          label="容量"
          width="100">
        </el-table-column>
        <el-table-column
          prop="area"
          label="地址"
          width="100">
        </el-table-column>
        <el-table-column
          prop="address"
          label="详细地址"
          width="100">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="180">
        </el-table-column>
        <el-table-column
          prop="siteNum"
          label="场地"
          width="90">
          <template slot-scope="scope">
            <el-button type="text" @click="initClubSite(scope.row.id)" size="small">详情
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="状态"
          width="90">
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
            <el-button @click="openDialog(scope.row.id)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteClub(scope.row.id)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog class="dialogUpdate" title="修改俱乐部信息" :visible.sync="dialogFormVisible">
        <el-form :inline="true" ref="form" label-width="120px" :model="form">
          <el-form-item label="俱乐部名称：">
            <el-input v-model="form.name" :value="form.name"></el-input>
          </el-form-item>
          <el-form-item label="容量：">
            <el-input v-model="form.capacity" :value="form.capacity"></el-input>
          </el-form-item>
          <el-form-item label="运动类型：">
            <el-select v-model="form.motionType" :value="form.motionType" placeholder="请选择" style="width:206.4px">
              <el-option
                v-for="item in motionType"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间段：">
            <el-select v-model="form.timeId" :value="form.timeId" placeholder="请选择" style="width:206.4px">
              <el-option
                v-for="item in time"
                :key="item.id"
                :label="item.time"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细地址：">
            <el-input v-model="form.address" :value="form.address"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateClub(form)">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog class="dialogUpdate" title="俱乐部场地信息" :visible.sync="dialogClubSite">
        <div v-for="item in clubSite">
          <span>{{item.address}}</span>
          <el-divider></el-divider>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogClubSite = false">取 消</el-button>
          <el-button type="primary" @click="updateClub()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
    import {Message} from 'element-ui';
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
                area: '',
                time: [],
                timeId: '',
                motionType: [],
                motionTypeId: '',
                dialogFormVisible: false,
                form: {},
                selectedOptions: [],
                options: regionData,
                soltArea: '',
                clubSite:[],
                dialogClubSite:false
            }
        },
        created() {
            this.initTime();
            this.intiMotionType();
            this.initTable();
        },
        methods: {
            intiMotionType() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/motionType/findAllMotionType',
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
            initTime() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/time/findAllTime',
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                that.time.push(obj)
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
                    url: 'http://localhost/FD/club/findAllClub',
                    type: 'GET',
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                let s = getMyDate(obj.createTime);
                                obj.createTime = s;
                                let time = that.findTimeById(obj.timeId);
                                obj.timeId = time;
                                let motionType = that.findMotionType(obj.motionType);
                                obj.motionType = motionType;
                                that.StatusText(obj);
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
            findTimeById(id) {
                let that = this;
                let time;
                $.ajax({
                    url: 'http://localhost/FD/time/findTimeById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            time = data.data.time;
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
                return time;
            },
            findMotionType(id) {
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
            updateStatus(id) {
                let that = this;
                let state = {
                    id: id,
                    status: '',
                }
                let a = document.getElementById(id).innerText;
                if (a === "解冻") {
                    state.status = 1
                } else if (a === "冻结") {
                    state.status = 0
                }
                $.ajax({
                    url: 'http://localhost/FD/club/updateStatus',
                    type: 'POST',
                    data: JSON.stringify(state),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            console.log(data);
                            that.$router.go('/clubList')
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
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
            findStatus(id) {
                let status;
                $.ajax({
                    url: 'http://localhost/FD/club/findStatus',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        status = data.data
                    }
                });
                return status;
            },
            openDialog(id) {
                let that = this;
                let status = that.findStatus(id);
                if (status == 0) {
                    Message.error({
                        message: '该俱乐部处于冻结状态，无法修改'
                    })
                    return;
                }
                that.dialogFormVisible = true;
                that.initForm(id);
            },
            //初始化修改页面数据
            initForm(id) {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/club/findClubById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            that.form = data.data;
                        } else {
                            Message.error({
                                message: '初始化数据失败'
                            })
                        }
                    }
                });
            },
            updateClub(form) {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/club/updateClubById',
                    type: 'POST',
                    data: JSON.stringify(form),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: "修改成功",
                                onClose: (that.$router.go("/clubList"))
                            })
                        } else {
                            Message.error({
                                message: '修改失败'
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
                that.soltArea = province + city + district
            },
            resetSolt() {
                let that = this;
                that.timeId = '';
                that.selectedOptions = [];
                that.motionTypeId = ''
                that.tableData = []
                that.soltArea = ''
                that.initTable();
            },
            soltClub() {
                let that = this;
                let form = {
                    timeId: that.timeId,
                    motionType: that.motionTypeId,
                    area: that.soltArea
                }
                $.ajax({
                    url: 'http://localhost/FD/club/findClubByCondition',
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
                                let s = getMyDate(obj.createTime);
                                obj.createTime = s;
                                let time = that.findTimeById(obj.timeId);
                                obj.timeId = time;
                                let motionType = that.findMotionType(obj.motionType);
                                obj.motionType = motionType;
                                that.StatusText(obj);
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
            deleteClub(id) {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/club/deleteClubById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            Message.success({
                                message: "删除成功",
                                onClose: (that.$router.go("/clubList"))
                            })
                        } else {
                            Message.error({
                                message: '删除失败'
                            })
                        }
                    }
                });
            },
            initClubSite(id){
                let that = this;
                that.dialogClubSite=true;
                $.ajax({
                    url: 'http://localhost/FD/club/findClubSiteById',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async: false,
                    success: function (data) {
                        that.clubSite=[]
                        if (data.state == 1) {
                            if(data.data.length<=0){
                                Message.error({
                                    message: "没有场地信息",
                                })
                            }
                            for(let i=0;i<data.data.length;i++){
                                let obj=data.data[i];
                                that.clubSite.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '删除失败'
                            })
                        }
                    }
                });
            }

        }
    }
</script>

<style scoped>

</style>

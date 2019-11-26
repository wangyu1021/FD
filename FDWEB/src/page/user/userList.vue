<template>
  <div>
    <div class="userSlot">
      <i class="el-icon-search"></i>
      <span style="font-size: 12px">角色:</span>
      <el-select v-model="roleId" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <span style="font-size: 12px">手机号:</span>
      <el-input v-model="phone" placeholder="请输入手机号" style="width:200px"></el-input>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini"
                 @click="soltUser(phone,roleId)">搜索
      </el-button>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini" @click="resertSolt">重置</el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%;">
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-form inline label-position="left" class="demo-table-expand">
            <el-form-item label="昵称:">
              <span>{{scope.row.nickName}}</span>
            </el-form-item>
            <el-form-item label="邀请人:">
              <div v-if="scope.row.userId===0">
                <span>无</span>
              </div>
              <div v-else>
                <el-button style="width: 65px;padding: 7px 9px;"
                           size="mini"
                           type="text"
                           @click="inviteUser(scope.row.userId)">查看
                </el-button>
              </div>
            </el-form-item>
            <el-form-item label="我邀请的人:">
              <span>{{scope.row.userId}}</span>
            </el-form-item>
            <el-form-item label="所属团长:">
              <div v-if="scope.row.superior==0">
                <span>无</span>
              </div>
              <div v-else>
                <span>有</span>
              </div>
            </el-form-item>
            <el-form-item label="成长值:">
              <span>{{scope.row.growthValue}}</span>
            </el-form-item>
            <el-form-item label="提成收益:">
              <span>{{scope.row.pushMoney}}</span>
            </el-form-item>
            <el-form-item label="可用收益:">
              <span>{{scope.row.availableIncome}}</span>
            </el-form-item>
            <el-form-item label="参与战队数:">
              <span>{{scope.row.numberTeam}}</span>
            </el-form-item>
            <el-form-item label="是否为队长:">
              <span>{{scope.row.captain}}</span>
            </el-form-item>
            <el-form-item label="创建战队数:">
              <span>{{scope.row.createTeam}}</span>
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
        label="姓名"
        prop="name"
      >
      </el-table-column>
      <el-table-column
        label="角色"
        prop="role"
      >
      </el-table-column>
      <el-table-column
        label="手机号"
        prop="phone"
      >
      </el-table-column>
      <el-table-column
        label="积分"
        prop="integral"
      >
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button style="width: 65px;padding: 7px 9px;"
                     size="mini"
                     type="primary"
                     @click="initStream(scope.row)">账户流水
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="财务流水" :visible.sync="dialogStream">
      <div id="main" :style="{width:'100%',height:'300px'}"></div>
    </el-dialog>
    <el-dialog title="详情" :visible.sync="dialogUser">
      <el-form :inline="true" ref="form" label-width="120px" :model="invite">
        <el-form-item label="昵称:">
          <span>{{invite.nickName}}</span>
        </el-form-item>
        <el-form-item label="姓名:">
          <span>{{invite.name}}</span>
        </el-form-item>
        <el-form-item label="手机号:">
          <span>{{invite.phone}}</span>
        </el-form-item>
        <el-form-item label="积分:">
          <span>{{invite.integral}}</span>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    import {Message} from 'element-ui';

    function getBeforeDate(n) {
        var n = n;
        var d = new Date();
        var year = d.getFullYear();
        var mon = d.getMonth() + 1;
        var day = d.getDate();
        if (day <= n) {
            if (mon > 1) {
                mon = mon - 1;
            } else {
                year = year - 1;
                mon = 12;
            }
        }
        d.setDate(d.getDate() + n); //很重要，+n取得是前一天的时间
        year = d.getFullYear();
        mon = d.getMonth() + 1;
        day = d.getDate();
        let s = (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
        return s;
    }

    export default {
        data() {
            return {
                tableData: [],
                dialogStream: false,
                form: {},
                dialogUser: false,
                invite: {
                    id: '',
                    nickName: '',
                    role: '',
                    name: '',
                    phone: '',
                    userId: '',
                    superior: '',
                    integral: '',
                    growthValue: '',
                    pushMoney: '',
                    availableIncome: '',
                    numberTeam: '',
                    captain: '',
                    createTeam: ''
                },
                phone: '',
                options: [],
                roleId:''
            }
        },
        created() {
            this.initTable()
            this.initOptions()
        },
        methods: {
            initTable() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/user/findAllUser.do',
                    type: 'GET',
                    data: '',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                if (obj.captain == 1) {
                                    obj.captain = '是'
                                } else {
                                    obj.captain = '否'
                                }
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
            initStream(row) {
                let that = this;
                that.dialogStream = true;
                var arr = [];
                for (var i = 0; i > -7; i--) {
                    arr.push(getBeforeDate(i));
                }
                setTimeout(() => {
                    /*ECharts图表*/
                    let myChart = that.$echarts.init(document.getElementById('main'));
                    myChart.setOption({
                        title: {
                            text: row.name + '的财务流水'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['成长值', '可用收益', '提成收益', '积分']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: arr.reverse()
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                name: '成长值',
                                type: 'line',
                                stack: '总量',
                                data: [120, 132, 101, 134, 90, 230, 210]
                            },
                            {
                                name: '可用收益',
                                type: 'line',
                                stack: '总量',
                                data: [220, 182, 191, 234, 290, 330, 310]
                            },
                            {
                                name: '提成收益',
                                type: 'line',
                                stack: '总量',
                                data: [150, 232, 201, 154, 190, 330, 410]
                            },
                            {
                                name: '积分',
                                type: 'line',
                                stack: '总量',
                                data: [320, 332, 301, 334, 390, 330, 320]
                            }
                        ]
                    });
                }, 300)

            },
            inviteUser(id) {
                let that = this;
                that.dialogUser = true;
                $.ajax({
                    url: 'http://localhost/FD/user/findUserById.do',
                    type: 'GET',
                    data: 'id=' + id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            that.invite = data.data;
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
            },
            resertSolt() {
                let that = this;
                that.tableData = []
                that.roleId = ''
                that.phone = ''
                that.initTable();
            },
            initOptions() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/role/findAllRole.do',
                    type: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        if(data.statue=1){
                            for(let i=0;i<data.data.length;i++){
                                let obj=data.data[i];
                                that.options.push(obj)
                            }
                        }else{
                            Message.error({
                                message:data.message
                            })
                        }
                    }
                });
            },
            //查询用户
            soltUser(phone,rId){
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/user/findUserByRP.do',
                    type: 'POST',
                    data:'roleId='+rId+"&phone="+phone,
                    dataType: 'json',
                    success: function (data) {
                        if(data.statue=1){
                            that.tableData=[]
                            for(let i=0;i<data.data.length;i++){
                                let obj = data.data[i];
                                if (obj.captain == 1) {
                                    obj.captain = '是'
                                } else {
                                    obj.captain = '否'
                                }
                                that.tableData.push(obj)
                            }
                        }else{
                            Message.error({
                                message:data.message
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

  .userSlot {
    border-bottom: 1px #e8ecf1 solid;
    padding-bottom: 10px;
    padding-top: 10px;
  }
</style>

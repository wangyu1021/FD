<template>
  <div>
    <div class="userSlot">
      <i class="el-icon-search"></i>
      <span style="font-size: 12px">俱乐部:</span>
      <el-select v-model="clubID" placeholder="请选择">
        <el-option
          v-for="item in club"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini"
                 @click="findGroupByClub(clubID)">搜索
      </el-button>
      <el-button style="width: 40px;padding: 7px 9px;" type="primary" size="mini" @click="resetSolt()">重置</el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          label="序号"
          type="index"
          width="100">
        </el-table-column>
        <el-table-column
          prop="caption"
          label="团长姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="团长手机号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="num"
          label="团员数量"
          width="120">
        </el-table-column>
        <el-table-column
          prop="clubId"
          label="所属俱乐部"
          width="233">
        </el-table-column>
        <el-table-column
          prop="avtivitiesNum"
          label="发起活动数量"
          width="120">
        </el-table-column>
        <el-table-column
          prop="consume"
          label="消费总额"
          width="220">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="220">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
    import {Message} from 'element-ui';

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
                clubID: '',
                club: [],
                tableData: []
            }
        },
        created() {
            this.initClub();
            this.initTable();
        },
        methods: {
            //初始化数据
            initTable() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/group/findAllGroup.do',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                let s = getMyDate(obj.createTime);
                                obj.createTime = s;
                                let clubName = that.findClubName(obj.clubId);
                                obj.clubId = clubName;
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
            //初始化查询条件
            initClub() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/club/findAllClub.do',
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
            //根据id查询俱乐部名字
            findClubName(id) {
                let that = this;
                let name;
                $.ajax({
                    url: 'http://localhost/FD/club/findClubById',
                    type: 'GET',
                    data:'id='+id,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    async:false,
                    success: function (data) {
                        if (data.state == 1) {
                            name=data.data.name
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
                            })
                        }
                    }
                });
                return name;
            },
            resetSolt() {
                let that = this;
                that.clubID = '';
                that.tableData = [];
                that.initTable();
            },
            findGroupByClub(clubID) {
                let that = this;
                if(clubID==''){
                    Message.error({
                        message:'请选择俱乐部'
                    })
                    return;
                }
                $.ajax({
                    url: 'http://localhost/FD/group/findGroupByClub.do',
                    type: 'GET',
                    data:'clubId='+clubID,
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        if (data.state == 1) {
                            that.tableData=[];
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                let s = getMyDate(obj.createTime);
                                obj.createTime = s;
                                let clubName = that.findClubName(obj.clubId);
                                obj.clubId = clubName;
                                that.tableData.push(obj)
                            }
                        } else {
                            Message.error({
                                message: '请检查网络是否连接'
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

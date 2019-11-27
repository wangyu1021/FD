<template>
  <div class="outside">
    <div style="font-size: 20px;margin-left: 20px;padding-top: 20px">
      <span>今日新增</span>
    </div>
    <div class="head" style="float: left">
      <div id="user" :style="{width:'100%',height:'300px'}"></div>
    </div>
    <div class="data">
      <el-col style="width: 350px;" :span="8">
        <el-card shadow="hover">
          <span style="font-size: 20px;color: #99a9bf;padding-bottom: 20px">访问用户数：</span>
          <div style="margin-top: 5px">
            <span style="font-size: 25px">1651人</span>
            <span style="font-size: 18px;color: chartreuse">+23%</span>
          </div>
          <div style="margin-left: 40px;margin-top: 5px;float: left;font-size: 12px;color: #B3C0D1">
            <h1>23%</h1>
            <span>新增用户</span>
          </div>
          <div style="margin-left: 200px;margin-top: 5px;font-size: 12px;color: #B3C0D1">
            <h1>58%</h1>
            <span>老用户</span>
          </div>
        </el-card>
      </el-col>
      <el-col style="width: 350px;padding-top: 16px" :span="8">
        <el-card shadow="hover">
          <span style="font-size: 20px;color: #99a9bf;padding-bottom: 20px">今日新增团长：</span>
          <div>
            <span style="font-size: 30px">322人</span>
            <span style="font-size: 18px;color: chartreuse">+41%</span>
          </div>
        </el-card>
      </el-col>
    </div>
    <div class="main" style="float: left;margin-right: 30px">
      <div id="club" :style="{width:'100%',height:'300px'}"></div>
    </div>
    <div class="main" style="float: left;margin-right: 30px">
      <div id="team" :style="{width:'100%',height:'300px'}"></div>
    </div>
    <div class="main" style="float: left">
      <div id="pk" :style="{width:'100%',height:'300px'}"></div>
    </div>
  </div>
</template>
<script>
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
        let s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
        return s;
    }

    export default {
        data() {
            return {}
        },
        created() {
            this.initUser();
            this.initClub();
            this.initPK();
            this.initTeam();
        },
        methods: {
            initUser() {
                let that = this;
                let arr = [];
                for (let i = 0; i > -7; i--) {
                    arr.push(getBeforeDate(i));
                }
                setTimeout(() => {
                    /*ECharts图表*/
                    let myChart = that.$echarts.init(document.getElementById('user'));
                    myChart.setOption({
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        legend: {
                            data: ['新增注册用户数', '所有注册用户数', '新增团长数', '所有团长数']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: arr.reverse()
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '新增注册用户数',
                                type: 'bar',
                                data: [320, 332, 301, 334, 390, 330, 320]
                            },
                            {
                                name: '新增团长数',
                                type: 'bar',
                                data: [12, 47, 56, 54, 45, 45, 45]
                            },
                            {
                                name: '所有注册用户数',
                                type: 'bar',
                                data: [676, 787, 898, 1542, 1561, 1611, 1651]
                            },
                            {
                                name: '所有团长数',
                                type: 'bar',
                                data: [121, , 213, 341, 242, 151, 322]
                            }
                        ]
                    });
                }, 300)
            },
            initPK() {
                let that = this;
                let lastNum=that.findLastPkNum()
                let todayNum=that.findTodayPkNum();
                setTimeout(() => {
                    /*ECharts图表*/
                    let myChart = that.$echarts.init(document.getElementById('pk'));
                    myChart.setOption({
                        title: {
                            text: '今日PK数据',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: ['今日新增PK数量', '昨日PK总数']
                        },
                        series: [
                            {
                                name: 'PK数据',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: todayNum, name: '今日新增PK数量'},
                                    {value: lastNum, name: '昨日PK总数'}
                                ],
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    });
                }, 300)
            },
            findLastPkNum(){
                let that = this;
                let lastNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/pk/findLastDayPk',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        lastNum=data.data;
                    }
                })
                return lastNum;
            },
            findTodayPkNum(){
                let that = this;
                let todayNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/pk/findTodayPk',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        todayNum=data.data;
                    }
                })
                return todayNum;
            },
            initClub() {
                let that = this;
               let todayNum=that.findTodayClubNum();
               let lastNUm=that.findLastClubNum();
                setTimeout(() => {
                    /*ECharts图表*/
                    let myChart = that.$echarts.init(document.getElementById('club'));
                    myChart.setOption({
                        title: {
                            text: '今日俱乐部数据',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: ['今日新增俱乐部数', '昨日俱乐部总数']
                        },
                        series: [
                            {
                                name: '俱乐部数据',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: todayNum, name: '今日新增俱乐部数'},
                                    {value: lastNUm, name: '昨日俱乐部总数'}
                                ],
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    });
                }, 300)
            },
            findLastClubNum(){
                let that = this;
                let lastNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/club/findLastClubNum',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        lastNum=data.data;
                    }
                })
                return lastNum;
            },
            findTodayClubNum(){
                let that = this;
                let todayNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/club/findTodayClubNum',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        todayNum=data.data;
                    }
                })
                return todayNum;
            },
            initTeam() {
                let that = this;
                let lastNum=that.findLastTeamNum();
                let todayNum=that.findTodayTeamNum();
                setTimeout(() => {
                    /*ECharts图表*/
                    let myChart = that.$echarts.init(document.getElementById('team'));
                    myChart.setOption({
                        title: {
                            text: '今日战队数据',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: ['今日新增战队数', '昨日战队总数']
                        },
                        series: [
                            {
                                name: '战队数据',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: todayNum, name: '今日新增战队数'},
                                    {value: lastNum, name: '昨日战队总数'}
                                ],
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    });
                }, 300)
            },
            findLastTeamNum(){
                let that = this;
                let lastNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/team/findLastNum',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        lastNum=data.data;
                    }
                })
                return lastNum;
            },
            findTodayTeamNum(){
                let that = this;
                let todayNum;
                $.ajax({
                    url:'https://www.orchardteam.com/FD/team/findTodayNum',
                    type:'GET',
                    dataType:'json',
                    contentType:'application/json;charset=utf-8',
                    async:false,
                    success:function(data){
                        todayNum=data.data;
                    }
                })
                return todayNum;
            },

        }
    }
</script>

<style scoped>
  .outside {
    width: 100%;
    height: 100%;
    background-color: #f2f4f5;
    margin-left: auto;
    margin-right: auto;
  }

  .head {
    margin: 20px 0px 0px 10px;
    background-color: #ffffff;
    width: 70%
  }

  .main {
    margin: 20px 0px 0px 10px;
    background-color: #ffffff;
    width: 30%
  }
  .pk {
    margin: 20px 0px 0px 10px;
    background-color: #ffffff;
    width: 30%
  }
  .team {
    margin: 20px 0px 0px 10px;
    background-color: #ffffff;
    width: 30%
  }

  .data {
    margin: 20px 0px 0px 10px;
    background-color: #ffffff;
  }

  .el-card {
    margin-left: 20px;
    height: 140px;
  }
</style>

<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="name"
        label="昵称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="ip"
        label="IP"
        width="180">
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="操作时间">
      </el-table-column>
    </el-table>
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
                tableData: []
            }
        },
        created() {
            this.initTableData()
        },
        methods: {
            initTableData() {
                let that = this;
                $.ajax({
                    url: 'http://localhost/FD/admin/findAllOperation',
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json;charset=-utf-8',
                    async: false,
                    success: function (data) {
                        if (data.state == 1) {
                            for (let i = 0; i < data.data.length; i++) {
                                let obj = data.data[i];
                                obj.createTime=getMyDate(obj.createTime);
                                that.tableData.push(obj);
                            }
                            console.log(that.tableData)
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

</style>

<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-pie-chart"></i> schart图表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="ctitle"><span class="el-icon-caret-right"></span>  场馆租赁场地个数</div>
            <div class="schart-box" v-for="(item,index) in tableData" :key="index">
                <schart class="schart" :canvasId="'canvas'+index" :options="item" />
            </div>

            <div class="ctitle"><span class="el-icon-caret-right"></span>  场馆租赁场地营销金额</div>
            <div class="schart-box" v-for="(item,index) in tableDataByMoney" :key="index">
                <schart class="schart" :canvasId="'canvas1'+index" :options="item" />
            </div>
        </div>

    </div>
</template>

<script>
    import Schart from 'vue-schart';
    export default {
        name: 'sales',
        data() {
            return {
                tableData:[],
                tableDataByMoney:[],
                options: {
                    type: "bar",
                    title: {
                        text: " 馆最近一周各品类销售图"
                    },
                    bgColor: "#fbfbfb",
                    labels: ["周一", "周二", "周三", "周四", "周五","周六","周日"],
                    datasets: [
                        {
                            label: "家电",
                            fillColor: "rgba(241, 49, 74, 0.5)",
                            data: [234, 278, 270, 190, 230]
                        },
                        {
                            label: "百货",
                            data: [164, 178, 190, 135, 160]
                        },
                        {
                            label: "食品",
                            data: [144, 198, 150, 235, 120]
                        }
                    ]
                }
            }
        },
        components:{
            Schart
        },
        mounted(){
          this.getAllOrders()
        },
        methods:{
            getAllOrders(){
                let that = this
                this.$service.get('/sms/getAllOrders')
                    .then((data)=>{
                        that.handleOrders(data.data)
                        that.handleOrdersByMoney(data.data)
                    }).catch((err)=>{
                    console.log(err)
                })
            },
            handleOrdersByMoney(data){
                let that = this
                data.forEach((item)=>{
                    let myDate0 = new Date(item.year+'-'+item.month+'-'+item.day)
                    if(!that.handleWeek(myDate0)){
                        return false
                    }
                    let index,datasetsIndex
                    let r = that.tableDataByMoney.some((item1,index1)=>{
                        if(item1.staNo == item.staNo){
                            index = index1
                            return true
                        }
                    })
                    if(!r){
                        let op = {}
                        op.type = 'bar'
                        op.title = {
                            text:item.sta_name+'馆  上一周场地销售额 (单位:元)'
                        }
                        op.staNo = item.staNo
                        op.bgColor="#fbfbfb"
                        op.labels= ["周一", "周二", "周三", "周四", "周五","周六","周日"]
                        op.datasets = []
                        let tag = {}
                        tag.label = item.tag
                        tag.data=new Array(7).fill(0)
                        tag.data[myDate0.getDay()-1]+=item.scha_price
                        op.datasets.push(tag)
                        that.tableDataByMoney.push(op)
                    }else if (that.tableDataByMoney[index].datasets.some((item3,index3)=>{
                        if (item3.label == item.tag){
                            datasetsIndex = index3
                            return false
                        }
                    })) {
                        let tag = {}
                        tag.label = item.tag
                        tag.fillColor = "rgba(241, 49, 74, 0.5)"
                        tag.data=new Array(7)
                        tag.data[myDate0.getDay()-1]+=item.scha_price
                        that.tableDataByMoney[index].datasets.push(tag)
                    }else{
                        that.tableDataByMoney[index].datasets[datasetsIndex].data[myDate0.getDay()-1]+=item.scha_price
                    }
                })
            },
            handleOrders(data){
                let that = this
                data.forEach((item)=>{
                    let myDate0 = new Date(item.year+'-'+item.month+'-'+item.day)
                    if(!that.handleWeek(myDate0)){
                        return false
                    }
                    let index,datasetsIndex
                    let r = that.tableData.some((item1,index1)=>{
                        if(item1.staNo == item.staNo){
                            index = index1
                            return true
                        }
                    })
                    if(!r){
                        let op = {}
                        op.type = 'bar'
                        op.title = {
                            text:item.sta_name+'馆  上一周场地租赁情况 (单位:个)'
                        }
                        op.staNo = item.staNo
                        op.bgColor="#fbfbfb"
                        op.labels= ["周一", "周二", "周三", "周四", "周五","周六","周日"]
                        op.datasets = []
                            let tag = {}
                            tag.label = item.tag
                            tag.data=new Array(7).fill(0)
                            tag.data[myDate0.getDay()-1]++
                        op.datasets.push(tag)
                        that.tableData.push(op)
                    }else if (that.tableData[index].datasets.some((item3,index3)=>{
                        if (item3.label == item.tag){
                            datasetsIndex = index3
                            return false
                        }
                    })) {
                        let tag = {}
                        tag.label = item.tag
                        tag.fillColor = "rgba(241, 49, 74, 0.5)"
                        tag.data=new Array(7)
                        tag.data[myDate0.getDay()-1]++
                        that.tableData[index].datasets.push(tag)
                    }else{
                        that.tableData[index].datasets[datasetsIndex].data[myDate0.getDay()-1]++
                    }
                })
            },
            handleWeek(date){
                //上周日
                let myDate=new Date()
                myDate.setDate(myDate.getDate()-myDate.getDay())

               //上周一
                let myDate1=new Date()
                myDate1.setDate(myDate1.getDate()-myDate1.getDay()-6)

                if (myDate1<=date&&date<=myDate){
                    return true
                }else{
                    return false
                }
            }
        }

    };
</script>

<style scoped>
    .schart-box {
        display: inline-block;
        margin: 20px;
    }
    .schart {
        width: 600px;
        height: 400px;
    }
    .ctitle{
        margin: 20px;
        color: #565c62;
        font-weight: bold;
        font-size: 20px;
    }
</style>

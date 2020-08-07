<template>
    <div class="orderMana">
       <ul class="omain">
           <li class="staLi" v-for="(item,index) in tableData" :key="index">
               <span class="el-icon-arrow-right" style="cursor: pointer;color: #00a854;font-weight: bold;padding-right: 20px;" @click="item.extend = !item.extend" v-if="!item.extend"></span>
               <span class="el-icon-arrow-down" style="cursor: pointer;color: #00a854;font-weight: bold;padding-right: 20px;"  v-if="item.extend" @click="item.extend = !item.extend"></span>
               <span class="title">场馆名称:</span><span>  {{item.sta_name}}</span>
               <ul v-if="item.extend">
                   <li class="subLi" v-for="(item2,index2) in item.orders" :key="index2">
                       <div>
                           <span class="title" style="margin-right: 10px;">订单编号:  </span><span>{{item2.order_id}}</span>
                           <span style="float: right; margin-right: 30%;" ><span class="title">用户名:  </span><span>{{item2.username}}</span></span>
                       </div>
                       <div style="margin: 10px 0;">
                           <span class="title" style="margin-right: 10px;">场地:  </span><span>{{item2.spaName}}</span>
                           <span style="float: right; margin-right: 30%;" ><span class="title" style="margin:0 10px 0 100px;">标签:</span><span>{{item2.tag}}</span></span>
                       </div>
                       <div style="margin: 10px 0;">
                           <span class="title" style="margin-right: 10px;">时间:</span><span>{{item2.year+'年'+item2.month+'月'+item2.day+'日  '+item2.hanWeekDay+'  '+item2.timeNode}}</span>
                       </div>
                       <div style="margin: 10px 0;">
                           <span class="title" style="margin-right: 10px;">支付金额:</span><span>{{item2.scha_price}}</span>
                           <span style="float: right; margin-right: 25%;" ><span class="title" style="margin:0 10px 0 100px;">支付状态:</span><span>{{item2.pay_status}}</span></span>
                       </div>
                       <div style="margin: 10px 0;">
                           <span :class="item2.outdate=='未过期'?'nooutdate':'outdate'">{{item2.outdate}}</span>
                       </div>
                   </li>
               </ul>
           </li>
       </ul>
    </div>
</template>

<script>
    export default {
        name: 'ordersManage',
        data() {
            return {
                tableData: [],
            }
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
                        let outdateOrderId=[]
                        data.data.forEach(function(item) {
                            if(item.outdate == '已过期'){
                                outdateOrderId.push(item.order_id)
                            }
                        });
                        if (outdateOrderId.length>0){
                            that.$serviceFront.post('/sms/updateOutdateOrder',JSON.stringify(outdateOrderId))
                        }

                    }).catch((err)=>{
                        console.log(err)
                })
            },
            handleOrders(data){
                let that = this
                data.forEach(function(item){
                    let subIndex
                    let result = that.tableData.some((item1,index)=>{
                        if(item.staNo==item1.staNo){
                            subIndex = index
                            return true
                        }
                    })
                    let {staNo, sta_name, ...order} = item
                    if(!result){
                        let r = {}
                        r.staNo = staNo
                        r.sta_name = sta_name
                        r.extend = false
                        r.orders =[]
                        r.orders.push(order)
                        that.tableData.push(r)
                      }else {
                        that.tableData[subIndex].orders.push(order)
                    }
                })
            }
        }
    };
</script>

<style scoped>
    ul,li{
        list-style:none
    }
    .orderMana{
        width: 100%;
        min-height: 100%;
        background: #ffffff;
        padding-top: 20px;
    }
    .omain{
        width: 70%;
        min-height: 80%;
        margin: 0 auto;
        border: 1px solid rgba(95, 39, 18, 0.22);
        box-shadow: 0px 0px 5px 5px rgba(87, 95, 89, 0.31);
        padding: 20px;
    }
    .staLi{
        width: 80%;
        margin: 0 auto;
        color: rgba(26, 9, 3, 0.67);
        font-size: 20px;
        font-weight: bold;
        font-stretch: condensed;
        border-bottom: 1px solid #ccc;
        padding: 20px;
    }
    .staLi:last-child{
        border-bottom: none;
    }
    .title{
        color: rgba(56, 60, 64, 0.52);
    }
    .subLi{
        /*border: 1px solid #ccc;*/
        padding: 30px;
        margin:20px 0 20px 0;
        box-shadow: 0px 0 5px 5px rgba(239, 239, 239, 0.35);
    }
    .outdate{
        color: #a80534;
    }
    .nooutdate{
        color: #00a854;
    }
</style>

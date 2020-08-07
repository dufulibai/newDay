<template>
    <div class="myInfo">
        <div class="userInfo">用户名:  {{this.orders[0].username}}</div>
        <div class="order">
            <div class="title">
                <span @click="groupByorder('all')" :class="{'current':cur=='all'}">我的订单</span>
                <span @click="groupByorder('no')"  :class="{'current':cur=='no'}">未过期订单</span>
                <span @click="groupByorder('yes')" :class="{'current':cur=='yes'}">过期订单</span>
            </div>
            <ul>
                <li v-for="(item,index) in groupOrder" :key="index">
                        <div>
                            <span style="width: 50%;">订单编号:{{item.order_id}}</span>
                            <span :class="item.outdate=='未过期'?'nooutdate':'outdate'">{{item.outdate}}</span>
                        </div>
                        <div>
                            <span @click="queryStaInfo(item)" class="goStaInfo">场馆:{{item.sta_name}}</span>
                            <span>标签:{{item.tag}}</span>
                            <span>场地:{{item.spaName}}</span>
                        </div>
                        <div>
                            <span style="width: 100%">时间:{{item.year}}年{{item.month}}月{{item.day}}日 {{item.hanWeekDay}}  {{item.timeNode}}</span>
                        </div>
                        <div>
                            <span>支付状态:{{item.pay_status}}</span>
                            <span>支付金额:{{item.scha_price}}</span>
                            <span>验证码:{{item.staNo+''+item.spaNo+''+item.year+''+item.month+''+item.day}}</span>
                        </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        name: 'MyInfo',
        data(){
            return{
                orders:'',
                groupOrder:'',
                cur:'all'
            }
        },
        mounted () {
            this.getOrders()
        },
        methods:{
            ...mapActions([
                'commitStaInfoTemp'
            ]),
            queryStaInfo(item){
                let _this = this
                this.$serviceFront.post('/sms/getStaInfo',JSON.stringify({'staNo':item.staNo}))
                    .then(function(data) {
                        console.log(data.data)
                        _this.commitStaInfoTemp(data.data)
                        _this.$router.push({name:'frontStaInfo'})
                    })
                    .catch(function(error) {
                        _this.$message.warning('场馆信息获取失败！')
                    })
            },
            groupByorder(type){
                switch (type) {
                    case 'all':
                        this.groupOrder = this.orders;
                        this.cur = 'all'
                        break;
                    case 'no':
                        this.groupOrder = this.orders.filter(function(item) {
                            return item.outdate == '未过期'
                        });
                        this.cur = 'no'
                        break;
                    case 'yes':
                        this.groupOrder = this.orders.filter(function(item) {
                            return item.outdate == '已过期'
                        });
                        this.cur = 'yes'
                        break;
                }
            },
            checkedOutdate(){
                this.orders.forEach((item)=>{
                    let h = item.timeNode.split('-')
                    let orderTime = item.year+'-'+item.month+'-'+item.day+' '+h[1]
                    let otDate = new Date(orderTime)
                    let now = new Date()
                    if(otDate<now){
                        item.outdate = '已过期'
                    }
                })
            },
            getOrders(){
                let that = this
                this.$serviceFront.get("/sms/getOrders")
                    .then(function(data) {
                        if (data.data){
                            that.orders = data.data
                            that.checkedOutdate()
                            that.groupOrder = that.orders
                            let outdateOrderId = []
                            that.orders.forEach(function(item) {
                                if(item.outdate == '已过期'){
                                    outdateOrderId.push(item.order_id)
                                }
                            });
                            if (outdateOrderId.length>0){
                                that.$serviceFront.post('/sms/updateOutdateOrder',JSON.stringify(outdateOrderId))
                            }
                        }
                    }).catch(function(error) {

                })
            },
        }
    };
</script>

<style scoped>
    .myInfo{
        background: rgba(87, 95, 89, 0.54);
        opacity: 0.8;
        padding-bottom: 20px;
    }
.userInfo,.title{
    width: 90%;
    height: 80px;
    line-height: 80px;
    text-align: left;
    padding-left: 10%;
    color: #ffffff;
    font-weight: 800;
    font-size: 20px;
    border-radius: 5%;
}
    .order{
        width: 80%;
        margin: 0 auto;
        background: rgba(86, 92, 98, 0.87);
        padding-bottom: 20px;
    }
    li{
        width: 90%;
        min-height: 100px;
        background: rgba(255, 255, 255, 0.25);
        border-radius: 2%;
        color: #ffffff;
        opacity: 1;
        font-size: 18px;
        padding-top: 30px;
        padding-left: 30px;
        margin-bottom: 20px;
    }
    li span{
        display: inline-block;
        width: 30%;
        /*margin-right: 50px;*/
        margin-bottom: 20px;
    }
    .nooutdate{
        color: #4ae272;
        font-size: 23px;
    }
    .outdate{
        color: #e22f36;
        font-size: 23px;
    }
    .title span{
        margin: 20px;
        cursor: pointer;
    }
    .current{
        padding: 30px;
        color: #5f2712;
        background: url(images/templatemo_menu_hover.png) center center no-repeat
    }
    .goStaInfo{
        cursor: pointer;
    }
    .goStaInfo:hover{
        color: #0094ff;
    }
</style>

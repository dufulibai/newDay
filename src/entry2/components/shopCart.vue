<template>
    <div id="shopCart">
       <div class="header">
           <h4>购物车</h4>
       </div>
        <div class="shopItem" v-for="(item,index) in checkedRecord" :key="index">
            <div class="el-icon-error delete" @click="deleteRecord(index)"></div>
            <div style="padding-top: 10px;">{{item.spaName}}({{item.tag}})</div>
            <div>{{item.hanWeekDay}}</div>
            <div>{{item.year}}/{{item.month}}/{{item.day}}  {{item.timeNode}}</div>
            <div>￥{{item.scha_price}}</div>
        </div>
        <div class="sum">
            总计：{{sumMoney}}元
        </div>
        <div class="submitOrders" @click="submitOrder">提交订单</div>
    </div>
</template>

<script>
    import {mapState,mapActions} from 'vuex'
    export default {
        name: 'shopCart',
        computed:{
            ...mapState({
                'checkedRecord':state => state.checkedRecord,
            }),
            sumMoney(){
                let sum = 0
                this.checkedRecord.forEach(function(item) {
                    sum += parseFloat(item.scha_price)
                })
                return sum.toFixed(2)
            }
        },
        methods:{
            ...mapActions([
                'commitCheckedRecord'
            ]),
            deleteRecord(index){
                let checkedRecord = JSON.parse(JSON.stringify(this.checkedRecord))
                checkedRecord.splice(index,1)
                this.commitCheckedRecord(checkedRecord)
            },
            submitOrder(){
                if (this.checkedRecord.length>0){
                    let _this = this
                    this.$serviceFront.post('/sms/payOrder',JSON.stringify({totalPrice:_this.sumMoney,orders:this.checkedRecord}))
                        .then(function(data) {
                        })
                        .catch(function(error) {
                            _this.$message.warning('支付失败');
                        })
                    _this.$router.push({name:'payQRcode'})
                }
            }
        }
    };
</script>

<style scoped>
#shopCart{
    position: fixed;
    right: 50px;
    top:10%;
    width: 15%;
    min-height: 200px;
    background: url("images/templatemo_main.png");
    border-radius: 10px;
    z-index: 999;
}
    .header{
        text-align: center;
        line-height: 30px;
        height: 30px;
        border-bottom: 1px solid #5f2712;
    }
    .shopItem{
        text-align: center;
        width: 90%;
        margin: 10px auto;
        min-height:50px;
        background: url("images/templatemo_login.png");
        background-size: cover;
        color: black;
    }
    .sum{
        text-align: center;
        line-height: 40px;
        color: #15620d;
        font-size: 20px;
    }
    .delete{
        margin-top:5px;color:#4a5f56;font-size: 18px;float: right;margin-right: 10px;cursor: pointer;
    }
    .delete:hover{
        color: red;
    }
    .submitOrders{
        margin: 0 auto;
        width: 80%;
        height: 40px;
        background: #00a854;
        color: #fff;
        font-size: 18px;
        line-height: 40px;
        text-align: center;
        margin-bottom: 20px;
        border-radius: 20px;
        margin-top: 10px;
        cursor: pointer;

    }
</style>

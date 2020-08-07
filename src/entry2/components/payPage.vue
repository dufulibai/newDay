<template>
    <div id="pay">

        <div v-if="payData&&index==-1">
            <div class="contain">
                <div class="wxpayLogo"></div>
                <div class="cancelOrder" @click="cancelOrder">取消订单</div>
                <div>
                    <span>创建时间: {{payData.created_at}}</span>
                    <span>订单号:{{payData.order_id}}</span>
                    <span>支付:{{payData.order_price}}元</span>
                </div>

                <div ref="qrcode" class="qrcode"></div>
            </div>
            <div class="count-down">0{{minutes}}:<span v-if="seconds<10&&seconds>=0">0</span>{{seconds}}</div>
        </div>
        <div v-else  class="waitQr">请稍等...</div>
        <div v-if="index>-1" class="waitQr">
           请稍等，您前面还有 {{index+1}} 位等待支付
        </div>
    </div>
</template>

<script>
    import QRcode from 'qrcodejs2'
    import SockJS from "sockjs-client"
    import Stomp from "stompjs"
    import {mapActions} from 'vuex'

    export default {
        name: 'payPage',
        data(){
          return{
              minutes:0,
              seconds:30,
              timerD:'',
              payData:'',
              stompClient:'',
              timerCon:'',
              index:-1
          }
        },
        computed:{

            listenTimer(){
                const {minutes,seconds} = this
                return {minutes,seconds}
            }
        },
        watch: {
            listenTimer(val){
                if (val.minutes==0&&val.seconds==0){
                    clearInterval(this.timerD)
                    this.cancelOrder()
                }
            }
        },
        mounted(){
            this.initWebSocket()
        },
        beforeDestroy(){
            this.disconnect(this.stompClient)
            clearInterval(this.timerCon)
        },
        methods:{
            ...mapActions([
                'commitCheckedRecord'
            ]),
            disconnect(stompClient) {
                if (stompClient) {
                    console.log('断开连接');
                    stompClient.disconnect()
                }
            },
            initWebSocket() {
                this.connection()
                // 断开重连机制,尝试发送消息,捕获异常发生时重连
                let that = this
                 this.timerCon = setInterval(() => {
                    try {
                        that.stompClient.send("test");
                    } catch (err) {
                        console.log("断线了: " + err);
                        that.connection()
                    }
                }, 5000);

            },
            connection() {
                // 建立连接对象
                let socket = new SockJS('http://localhost:8000/gs-guide-websocket');
                // 获取STOMP子协议的客户端对象
                this.stompClient = Stomp.over(socket);
                let token = window.sessionStorage.getItem('token')
                console.log('token')
                console.log(token)
                let headers = {
                    authorization:`Bearer${token}`
                }
                // 向服务器发起websocket连接
                let backmsg
                let that = this
                this.stompClient.connect(headers,(frame) => {
                    console.log('Connected: ' + frame);
                    that.stompClient.subscribe('/user/queue/payResult', (result) => { // 订阅服务端提供的某个topic
                        console.log('广播成功')
                        console.log(result)
                        let backmsg = JSON.parse(result.body)
                        console.log('返回值')
                        console.log(backmsg);  // msg.body存放的是服务端发送给我们的信息
                        that.$router.push({name:'payResult',params:{payAmount:backmsg}})//that.$router
                    });
                    that.stompClient.subscribe('/user/queue/getQrcode', (result) => { // 订阅服务端提供的某个topic
                        let map = JSON.parse(result.body)
                        console.log('返回01')
                        console.log(map);  // msg.body存放的是服务端发送给我们的信息

                        if (map){
                            that.payData = map
                            that.$nextTick(() => {
                                that.generateQRCode()
                                //倒计时
                                that.timer()
                            })
                        }
                    });
                    // that.stompClient.subscribe('/user/queue/waitQrcode', (result) => { // 订阅服务端提供的某个topic
                    //     let map = JSON.parse(result.body)
                    //     console.log('返回02')
                    //     console.log(map);  // msg.body存放的是服务端发送给我们的信息
                    //     that.index = map.index
                    // });
                }, (err) => {
                    // 连接发生错误时的处理函数
                    console.log('失败')
                    console.log(err);
                });
            },

            timer(){
               this.timerD = setInterval(()=>{
                  if (this.minutes>0 && this.seconds<=0){
                      this.minutes--
                      this.seconds= 59
                      return
                  }
                  this.seconds--
              },1000)
            },
            cancelOrder(){
                //去后台取消订单，删除数据库相应数据
                let that = this
                let data = JSON.stringify({orderId:this.payData.order_id,price:this.payData.order_price})
                this.$serviceFront.post("/sms/cancelOrder", data)
                    .then(function(data) {
                        if (data.data=='ok'){
                            console.log(data.data)
                            let checkedRecord = []
                            that.commitCheckedRecord(checkedRecord)
                            that.$router.push({name:'frontStaInfo'})
                        }
                    }).catch(function(error) {
                })
            },
            generateQRCode(){
                var canvas = this.$refs.qrcode;
                let qrcode = new QRcode(canvas, {
                    width: 100,
                    height: 100, // 高度
                    text: this.payData.qr_url, // 二维码内容
                    // render: 'canvas' // 设置渲染方式（有两种方式 table和canvas，默认是canvas）
                    // background: '#f0f'
                    // foreground: '#ff0'
                })
            },
        }
    };
</script>

<style scoped>

#pay{
    position: fixed;
    width:100%;
    height: 100%;
    background: #fff;
    background-size: 100%;
}
    .contain{
        position: absolute;
        left: 45%;
        top: 25%;
        transform: translateX(-25%);
    }
    .contain span{
        margin: 16px;
    }
    .qrcode{
        transform: translate(40%,50%);
    }
    .wxpayLogo{
        display: inline-block;
        width: 400px;
        height: 100px;
        background: url("images/WePayLogo.png") no-repeat;
        background-size: 50% 50%;
        vertical-align: top;
    }
    .count-down{
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        position: absolute;
        top: 65%;
        left: 50%;
    }
    .cancelOrder{
        display: inline-block;
        width: 100px;
        height: 30px;
        background: #0094ff;
        color: #fff;
        font-weight: bold;
        border-radius: 10%;
        text-align: center;
        line-height: 30px;
        vertical-align: top;
        cursor: pointer;
        margin-top: 10px;
    }
    .cancelOrder:hover{
        background: #46c1ff;
        color: #ffffff;
    }
    .waitQr{
        position: absolute;
        top:50% ;
        left: 50%;
        transform: translateY(-25%);
        font-size: 25px;
        color: black;
    }
</style>

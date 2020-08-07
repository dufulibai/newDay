<template>
    <div>
        <p>websocket hello</p>
           <div>
                <label>WebSocket connection:</label>
                <button id="connect" @click="connection" :disabled="connected">Connect</button>
                <button id="disconnect" @click="disconnect" :disabled="!connected">Disconnect</button>
           </div>
           <div>
                <label>What is your name?</label>
                <input type="text" id="name" placeholder="Your name here..." v-model="sendMsg">
                <button id="send" @click="sendName" type="submit">Send</button>
           </div>
        <table id="conversation" v-if="connected">
            <thead>
            <tr>
                <th>Greetings</th>
            </tr>
            </thead>
            <tbody id="greetings">
                <td>{{wbcMsg}}</td>
            </tbody>
        </table>
    </div>
</template>

<script>
    import SockJS from "sockjs-client"
    import Stomp from "stompjs"
    export default {
        name: 'webSocketIndex',
        data(){
            return {
                stompClient:'',
                timer:'',
                connected:'',
                wbcMsg:'',
                sendMsg:''
            }
        },
        mounted(){
            this.initWebSocket();
        },
        beforeDestroy: function () {
            // 页面离开时断开连接,清除定时器
            this.disconnect();
            clearInterval(this.timer);
        },
        methods:{
            initWebSocket() {
                this.connection();
                let that= this;
                // 断开重连机制,尝试发送消息,捕获异常发生时重连
                this.timer = setInterval(() => {
                    try {
                        that.stompClient.send("test");
                    } catch (err) {
                        console.log("断线了: " + err);
                        that.connection();
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
                    // authorization:'jisjais'
                }
                // 向服务器发起websocket连接
                let that = this
                this.stompClient.connect(headers,(frame) => {
                    that.connected = true
                    console.log('Connected: ' + frame);
                    this.stompClient.subscribe('/user/queue/greetings', (greeting) => { // 订阅服务端提供的某个topic
                        console.log('广播成功')
                        that.wbcMsg = JSON.parse(greeting.body).content
                        console.log(JSON.parse(greeting.body).content);  // msg.body存放的是服务端发送给我们的信息
                        that.$router.push({name:'paySeccess',params:{nameU:that.wbcMsg}})
                    });
                    // this.stompClient.send("/app/chat.addUser",
                    //     headers,
                    //     JSON.stringify({sender: '',chatType: 'JOIN'}),
                    // )   //用户加入接口
                }, (err) => {
                    // 连接发生错误时的处理函数
                    console.log('失败')
                    console.log(err);
                });
            },
            sendName(){
                  this.stompClient.send("/app/hello", {}, JSON.stringify({'name': this.sendMsg}))
            },
            disconnect() {
                if (this.stompClient) {
                    this.stompClient.disconnect();
                    this.connected = false
                    console.log("Disconnected");
                }
            },  // 断开连接
        }
    };
</script>

<style scoped>

</style>

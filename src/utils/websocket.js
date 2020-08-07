import SockJS from "sockjs-client"
import Stomp from "stompjs"
let stompClient,timer

//初始化
function initWebSocket() {
     connection()
    // 断开重连机制,尝试发送消息,捕获异常发生时重连
    timer = setInterval(() => {
        try {
            stompClient.send("test");
        } catch (err) {
            console.log("断线了: " + err);
             connection()
        }
    }, 5000);
     return stompClient;
}

//连接
function connection() {
    // 建立连接对象
    let socket = new SockJS('http://localhost:8000/gs-guide-websocket');
    // 获取STOMP子协议的客户端对象
    stompClient = Stomp.over(socket);
    let token = window.sessionStorage.getItem('token')
    console.log('token')
    console.log(token)
    let headers = {
        authorization:`Bearer${token}`
    }
    // 向服务器发起websocket连接
    let backmsg
    let that = this
    stompClient.connect(headers,(frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/payResult', (result) => { // 订阅服务端提供的某个topic
            console.log('广播成功')
            console.log(result)
            let backmsg = JSON.parse(result.body).content
            console.log('返回值')
            console.log(backmsg);  // msg.body存放的是服务端发送给我们的信息
            that.$router.push({name:'payResult',params:{nameU:backmsg}})//that.$router
        });
        stompClient.subscribe('/user/queue/getQrcode', (result) => { // 订阅服务端提供的某个topic
            console.log('广播yes')
            console.log(result)
            let map = JSON.parse(result.body).content
            console.log('返回01')
            console.log(map);  // msg.body存放的是服务端发送给我们的信息
            if (map.index){
                that.index = map.index
            }else {
                that.payData = map
            }
        });
    }, (err) => {
        // 连接发生错误时的处理函数
        console.log('失败')
        console.log(err);
    });

    return stompClient;

}
// 断开连接
function disconnect(stompClient) {
    if (stompClient) {
        console.log('断开连接');
        stompClient.disconnect()
    }
}
export {initWebSocket,connection,disconnect};


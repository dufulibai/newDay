package com.cuit.smsb.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends  Thread {
    public void run() {
        ServerSocket serversocket = null;
        try {
            serversocket = new ServerSocket(7001);
            Socket socket = serversocket.accept();// 接收客户端发送的请求
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

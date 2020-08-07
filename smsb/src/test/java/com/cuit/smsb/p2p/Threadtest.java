package com.cuit.smsb.p2p;

import com.cuit.smsb.Controller.FrontController;

import java.util.List;
import java.util.Map;

public class Threadtest implements Runnable {
    @Override
    public void run() {
        System.out.println("order的geshu");
        System.out.println(FrontController.conQ.size());
        if (!FrontController.conQ.isEmpty()){
            List<Map<String, Object>> poll = FrontController.conQ.poll();
            System.out.println(poll.toString());
            System.out.println("删除一个order");
        }

    }
}

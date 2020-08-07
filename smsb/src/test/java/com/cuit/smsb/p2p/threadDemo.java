package com.cuit.smsb.p2p;

import com.cuit.smsb.Controller.FrontController;

public class threadDemo implements Runnable{

    @Override
    public void run() {
        System.out.println("读取");
        if (FrontController.conQ.isEmpty()){
            System.out.println("删除完了");
        }else {
            System.out.println("还有");
        }
    }
}

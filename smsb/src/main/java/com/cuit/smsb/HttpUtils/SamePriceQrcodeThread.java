package com.cuit.smsb.HttpUtils;

import com.cuit.smsb.Controller.FrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SamePriceQrcodeThread implements Runnable {
    public ConcurrentLinkedQueue<List<Map<String,Object>>> conQ = FrontController.conQ;
    //锁
    public static Object objSock = new Object();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
     double totalPrice;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public SamePriceQrcodeThread(double totalPrice){
           this.totalPrice = totalPrice;
    }


    @Override
    public void run() {
        System.out.println("开始线程=============");
        while (conQ.size()>0){
            synchronized(objSock){
                Map result = HttpUtils.getQrcode(this.totalPrice);
                List<Map<String, Object>> order = conQ.poll();
                order.forEach((item)-> {
                    item.put("order_id",result.get("order_id"));
                });
                result.put("order",order);
                System.out.println("result===========");
                System.out.println(result);
                if (result != null){
                    redisTemplate.opsForValue().set(result.get("qr_price").toString(), result);
                    messagingTemplate.convertAndSendToUser(order.get(0).get("username").toString(),"/queue/getQrcode",result);
                    try {
                        this.wait(2*60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }


        }
        FrontController.threadsName.remove(this.totalPrice+"");
    }
}

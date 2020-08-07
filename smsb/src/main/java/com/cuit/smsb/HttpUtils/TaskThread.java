package com.cuit.smsb.HttpUtils;

import com.cuit.smsb.Controller.FrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TaskThread {
    public ConcurrentLinkedQueue<List<Map<String,Object>>> conQ = FrontController.conQ;
    //锁
    public static Object objSock = new Object();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
//    double totalPrice;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //等待获取Qrcode中
    @Async
   public void waitQrcode(List<Map<String,Object>> order, Principal principal){
       int index=0;
        System.out.println(Thread.currentThread().getName()+"==========");
       while (conQ.contains(order)){

           Iterator<List<Map<String, Object>>> iterator = conQ.iterator();
           while (iterator.hasNext()){
               if(iterator.next().equals(order)){
                   break;
               }
               index++;
           }
           Map map = new HashMap();
           map.put("index",index);
           System.out.println("waitQrcode==========");
           System.out.println(index);
           if (index>0){
               messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/waitQrcode",map);
           } else if (index == 0){
               messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/waitQrcode",map);
               break;
           }
       }
   }
    @Async
    public void getQrcode(double totalPrice){
        System.out.println("开始线程=============");
        while (!conQ.isEmpty()){
            List<Map<String, Object>> order = conQ.poll();
            synchronized(objSock){
                Map result = HttpUtils.getQrcode(totalPrice);
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
                        objSock.wait(30*1000);
                        redisTemplate.delete(result.get("qr_price").toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }

        }
        FrontController.threadsName.remove(totalPrice+"");

    }

}

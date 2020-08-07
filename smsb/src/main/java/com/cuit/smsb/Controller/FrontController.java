package com.cuit.smsb.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cuit.smsb.HttpUtils.HttpUtils;
import com.cuit.smsb.HttpUtils.SamePriceQrcodeThread;
import com.cuit.smsb.HttpUtils.TaskThread;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;
import com.cuit.smsb.Service.FrontService;
import com.cuit.smsb.utils.Match;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alibaba.druid.util.Utils.md5;

@RestController
public class FrontController {
    @Resource
    FrontService frontService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/register")
    public String register(@RequestBody User user){
        System.out.println(user);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        frontService.insertUser(user);
        return "ok";
    }

    @PostMapping("/getAllSpaBySta")
    public List<Map<String, Object>> getAllSpaBySta(@RequestBody Map<String,String> staNo){
        List<Map<String, Object>> staNo1 = frontService.getAllSpaBySta(staNo.get("staNo"));
        return staNo1;
    }

    public static ConcurrentLinkedQueue<List<Map<String,Object>>> conQ = new ConcurrentLinkedQueue<>();
    public static volatile List<String> threadsName = new ArrayList<>();
    @Autowired
    TaskThread taskThread;

    @PostMapping("/payOrder")
    public void payOrder(HttpServletRequest req,@RequestBody Map<String, Object> objectMap,Principal principal){
        double totalPrice= (double) Double.parseDouble((String) objectMap.get("totalPrice"));
        List<Map<String,Object>> order = (List<Map<String, Object>>) objectMap.get("orders");
        System.out.println(totalPrice);
        System.out.println(order.toString());
        if (principal != null){
            order.forEach((item)-> {
                //  item.put("order_id",map.get("order_id"));
                item.put("username",principal.getName());
                item.put("pay_status","未支付");
            });
        }
        if (!conQ.contains(order)){
            conQ.offer(order);
            System.out.println("存入order");
        }else{
            System.out.println("已经存在order");
        }

        //threadsName.remove(totalPrice+"");
        if (!threadsName.contains(totalPrice + "")){
            threadsName.add(totalPrice+"");
            //创建线程
            taskThread.getQrcode(totalPrice);
            System.out.println("创建线程成功!");
        }else{
            System.out.println("已经创建线程");
        }
        return;
    }
    //spring提供的发送消息模板
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
     //支付响应回调
    @PostMapping("/wxn")
    public void wxn(@RequestBody String money){
        System.out.println("响应成功");
        ObjectMapper objectMapper = new ObjectMapper();
        Map <String,Object> jsonMap=null;
        try {
            jsonMap = objectMapper.readValue(money, new TypeReference<Map <String,Object >>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String content = (String) jsonMap.get("content");
        String timestamp = (String) jsonMap.get("timestamp");
        System.out.println(content);
        System.out.println(timestamp);
        String payKey = Match.findMoney(content);
        if (!payKey.isEmpty()) {
            Map s = (Map) redisTemplate.opsForValue().get(payKey);
            List<Map<String,Object>> order = (List<Map<String, Object>>) s.get("order");
            order.forEach((item)-> {
                item.replace("pay_status","已支付");
                item.put("outdate","未过期");
            });
            int sucess = frontService.paySuccess(order);
            if (sucess>0){
                redisTemplate.delete( payKey);
                String username = (String) order.get(0).get("username");
                messagingTemplate.convertAndSendToUser(username,"/queue/payResult",payKey);
                TaskThread.objSock.notify();
            }
        } else {
            System.out.println("NO MATCH");
        }

    }

    //取消订单，删除redis中缓存的订单
    @PostMapping("/cancelOrder")
    public String cancelOrder(@RequestBody String order){

        ObjectMapper objectMapper = new ObjectMapper();
        Map <String,Object> jsonMap=null;
        try {
            jsonMap = objectMapper.readValue(order, new TypeReference<Map <String,Object >>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String orderId = jsonMap.get("orderId").toString();
        String price = (String) jsonMap.get("price");
        redisTemplate.delete(price);
        frontService.deleteCancelOrder(orderId);
        return "ok";
    }

    //查询订单
    @GetMapping("/getOrders")
    public  List<Map<String,Object>> getOrders(Principal principal) throws Exception {
        if (!principal.getName().isEmpty()){
              return frontService.getOrders(principal.getName());
        }else{
                throw new Exception("用户未登录!");
        }
    }
    @GetMapping("/getAllOrders")
    public List<Map<String,Object>> getAllOrders(){
        return frontService.getAllOrders();
    }

    //查询某个场馆信息
    @PostMapping("/getStaInfo")
    public StaInfo getStaInfo(@RequestBody Map<String, String> staNo){
        return frontService.getStaInfo(staNo.get("staNo"));
    }

    //过期的订单更新为过期
    @PostMapping("/updateOutdateOrder")
    public void updateOutdateOrder(@RequestBody ArrayList<String> outdateOrders){
        frontService.updateOutdateOrder(outdateOrders);
    }


}

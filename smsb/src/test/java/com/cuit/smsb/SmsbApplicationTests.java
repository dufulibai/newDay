package com.cuit.smsb;

import com.alibaba.fastjson.JSONObject;
import com.cuit.smsb.HttpUtils.HttpUtils;
import com.cuit.smsb.Mapper.FrontReadMapper;
import com.cuit.smsb.Mapper.UserReadMapper;
import com.cuit.smsb.Mapper.testsms;
import com.cuit.smsb.eggMapper.payOrder;
import com.cuit.smsb.p2p.Threadtest;
import com.cuit.smsb.p2p.threadDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class SmsbApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder p = new BCryptPasswordEncoder();
        String s = p.encode("123");
        System.out.println(s);
        System.out.println("dddd");
    }
    @Resource
    UserReadMapper userReadMapper;
//    @Test
//    void testMapper(){
//        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(user.toString());
//    }
    @Test
    public void postMan(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cg","ccf");
        HttpUtils.post(jsonObject,"http://localhost:8000/notifyPay");
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    FrontReadMapper frontReadMapper;
    @Test
    void contextLoads1() {
        List<Map<String, Object>> user1 =  frontReadMapper.getOrders("user1");
        System.out.println(user1.toString());
    }
}

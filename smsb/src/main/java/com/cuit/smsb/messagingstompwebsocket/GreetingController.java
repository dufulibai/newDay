package com.cuit.smsb.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendToUser("/queue/greetings")
    public Greeting greeting(HelloMessage message, Principal user) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(message.getName());
        System.out.println("===========");
        System.out.println(user.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }
    @MessageMapping("/payResult")
    @SendToUser("/queue/payResult")
    public String payResult(String payStatus,Principal user){
        return payStatus;
    }
}

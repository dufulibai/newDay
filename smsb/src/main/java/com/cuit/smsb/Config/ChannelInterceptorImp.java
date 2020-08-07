package com.cuit.smsb.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.List;

public class ChannelInterceptorImp implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("authorization");
            System.out.println("webSocket token is {}" + jwtToken);
            if (!StringUtils.isEmpty(jwtToken)) {
                Claims claims = Jwts.parser().setSigningKey("libai@123").parseClaimsJws(jwtToken.replace("Bearer",""))
                        .getBody();
                if (!claims.isEmpty()){
                    //  Map sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                    //  sessionAttributes.put(CsrfToken.class.getName(), new DefaultCsrfToken("authorization", "authorization", jwtToken));
                    String username = claims.getSubject();//获取当前登录用户名
                    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(token);
                    accessor.setUser(token);
                }
                //     Map sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                //   sessionAttributes.put(CsrfToken.class.getName(), new DefaultCsrfToken("authorization", "authorization", jwtToken));
            }
        }
        return message;
    }
}

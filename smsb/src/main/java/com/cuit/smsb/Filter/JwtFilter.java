package com.cuit.smsb.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        String jwtToken = req.getHeader("authorization");
        System.out.println("jwtToken");
        System.out.println(jwtToken);
       // req.getSession().setAttribute("user",req.getSession().getAttribute("user"));

        if(jwtToken == null){
            filterChain.doFilter(req,servletResponse);
            return;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey("libai@123").parseClaimsJws(jwtToken.replace("Bearer",""))
                    .getBody();
            if (!claims.isEmpty()){
                String username = claims.getSubject();//获取当前登录用户名
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(req,servletResponse);
            }

        }catch (ExpiredJwtException e){
            resp.setContentType("application/json;charset=utf-8");
            Map<String,String> map = new HashMap<>();
            map.put("invalidToken","invalidToken");
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        }

    }

}

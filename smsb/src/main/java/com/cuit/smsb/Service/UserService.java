package com.cuit.smsb.Service;


import com.cuit.smsb.Mapper.UserReadMapper;
import com.cuit.smsb.Model.Role;
import com.cuit.smsb.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserReadMapper userReadMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReadMapper.findByUsername(username);
        System.out.println(user.toString());
        if (user == null) {
            throw new UsernameNotFoundException("用户名错误！");
        }
        //根据用户名查询当前用户所有权限
        List<Role> roleList = userReadMapper.findRoleByUsername(username);
        //authorities：存放所有用户权限
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role:roleList){
            GrantedAuthority authority = new
                    SimpleGrantedAuthority(role.getName());
            authorities.add( authority );
        }
        //把所有权限赋值给user
        user.setAuthorities(authorities);
        return user;
    }
}

package com.cuit.smsb.Mapper;

import com.cuit.smsb.Model.Role;
import com.cuit.smsb.Model.User;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
//@Mapper
public interface UserReadMapper {
    /**
     * 查询当前用户对象
     */
    User findByUsername(String username);

    /**
     * 查询当前用户拥有的权限
     */
    List<Role> findRoleByUsername(String username);
}

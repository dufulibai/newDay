package com.cuit.smsb.Mapper;

import com.cuit.smsb.Model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface FrontWriteMapper {
    void insertUser(User user);
    void insertUserRoleByUserId(Long userId);
    //处理支付响应
    int insertOrders(List<Map<String, Object>> order);

    void updateOutdateOrder(ArrayList<String> outdateOrders);
}

package com.cuit.smsb.Service;

import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FrontService {
    void insertUser(User user);

    List<Map<String,Object>> getAllSpaBySta(String staNo);
    //处理支付成功响应
    int paySuccess(List<Map<String,Object>> order);
    //删除egg数据库取消的订单
    void deleteCancelOrder(String orderId);

    //获取用户订单
    List<Map<String,Object>> getOrders(String username);
    //获取所有用户订单
    List<Map<String,Object>> getAllOrders();

    StaInfo getStaInfo(String staNo);

    void updateOutdateOrder(ArrayList<String> outdateOrders);
}

package com.cuit.smsb.Mapper;

import com.cuit.smsb.Model.StaInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FrontReadMapper {
    List<Map<String,Object>> getAllSpaBySta(String staNo);

    //获取用户订单
    List<Map<String,Object>> getOrders(String username);
    List<Map<String,Object>> getAllOrders();

    StaInfo getStaInfo(String staNo);
}

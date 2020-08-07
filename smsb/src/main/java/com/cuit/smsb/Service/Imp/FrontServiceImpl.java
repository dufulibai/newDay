package com.cuit.smsb.Service.Imp;

import com.cuit.smsb.Mapper.FrontReadMapper;
import com.cuit.smsb.Mapper.FrontWriteMapper;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;
import com.cuit.smsb.Service.FrontService;
import com.cuit.smsb.eggMapper.payOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FrontServiceImpl implements FrontService {
    @Resource
    FrontWriteMapper frontWriteMapper;
    @Resource
    FrontReadMapper frontReadMapper;
    @Resource
    payOrder po;

    @Override
    public void insertUser(User user) {
        frontWriteMapper.insertUser(user);
        if (user.getUserId()!= null || user.getUserId() != 0) {
              frontWriteMapper.insertUserRoleByUserId(user.getUserId());
        }
    }

    @Override
    public List<Map<String,Object>> getAllSpaBySta(String staNo) {
        List<Map<String, Object>> allSpaBySta = frontReadMapper.getAllSpaBySta(staNo);
        return allSpaBySta;
    }

    @Override
    public int paySuccess(List<Map<String, Object>> order) {
        final int s1 = frontWriteMapper.insertOrders(order);
        final int s2 = po.updateOrders(order.get(0).get("order_id").toString());
        if(s1>0 && s2>0){
            return 1;
        } else {
            return -1;
        }
    }

    public void deleteCancelOrder(String orderId){
        po.deleteCancelOrder(orderId);
    }

    @Override
    public List<Map<String, Object>> getOrders(String username) {
        List<Map<String, Object>> orders = frontReadMapper.getOrders(username);
        if (!orders.isEmpty()){
            return orders;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllOrders() {
        return frontReadMapper.getAllOrders();
    }

    @Override
    public StaInfo getStaInfo(String staNo) {

        return frontReadMapper.getStaInfo(staNo);
    }

    @Override
    public void updateOutdateOrder(ArrayList<String> outdateOrders) {
        frontWriteMapper.updateOutdateOrder(outdateOrders);
    }
}

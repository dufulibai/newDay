package com.cuit.smsb.eggMapper;

public interface payOrder {
    int updateOrders(String order_id);
    void deleteCancelOrder(String orderId);
}

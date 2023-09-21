package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.Order;
import com.zwf.crm.pojo.OrderDetails;

import java.util.List;

public interface OrderDetailsMapper extends BaseMapper<OrderDetails,Integer> {

    public Order queryOrderDetailByOrderId(Integer orderId);

    public List<OrderDetails> queryOrderDetailByOrder(Integer orderId);

}
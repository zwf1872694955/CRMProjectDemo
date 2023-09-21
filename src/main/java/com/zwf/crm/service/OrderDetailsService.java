package com.zwf.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.OrderDetailsMapper;
import com.zwf.crm.pojo.Order;
import com.zwf.crm.pojo.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-18 10:29
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {
    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    public Order queryOrderDetailByOrderId(Integer orderId){
      return   orderDetailsMapper.queryOrderDetailByOrderId(orderId);
    }

    public Map<String,Object> queryOrderAllByOrderId(Integer orderId, Integer page, Integer limit){
        List<OrderDetails> orderDetails = orderDetailsMapper.queryOrderDetailByOrder(orderId);
        PageHelper.startPage(page,limit);
        PageInfo<OrderDetails> pageInfo=new PageInfo<>(orderDetails);
        Map<String,Object> maps=new HashMap<>();
        maps.put("code",0);
        maps.put("msg","");
        maps.put("data",pageInfo.getList());
        maps.put("count",pageInfo.getTotal());
        return maps;
    }
}
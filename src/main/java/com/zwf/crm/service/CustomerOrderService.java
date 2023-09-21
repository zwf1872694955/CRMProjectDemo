package com.zwf.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.CustomerOrderMapper;
import com.zwf.crm.pojo.CustomerOrder;
import com.zwf.crm.query.OrderQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-18 8:56
 */
@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {
      @Resource
    private CustomerOrderMapper customerOrderMapper;

      public List<CustomerOrder> queryOrderByCusId(Integer cusId){
        return   customerOrderMapper.queryOrderByCusId(cusId);
      }

      public Map<String,Object> queryOrderDetailByTable(OrderQuery orderQuery){
          Map<String,Object> maps=new HashMap<>();
          //开启分页
          PageHelper.startPage(orderQuery.getPage(),orderQuery.getLimit());
          PageInfo<CustomerOrder> pageInfo=new PageInfo<>(orderQuery.getCustomerOrderList());
          maps.put("code",0);
          maps.put("data",pageInfo.getList());
          maps.put("msg","");
          maps.put("count",pageInfo.getTotal());
          return maps;
      }

}
package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.CustomerOrder;

import java.util.List;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    public List<CustomerOrder> queryOrderByCusId(Integer cusId);


}
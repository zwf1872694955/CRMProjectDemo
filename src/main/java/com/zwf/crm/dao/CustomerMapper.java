package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.Customer;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer queryCustomerByName(String name);
}
package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;
import com.zwf.crm.pojo.CustomerOrder;

import java.util.List;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-18 9:43
 */
public class OrderQuery extends BaseQuery {
    private List<CustomerOrder> customerOrderList;

    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }
}
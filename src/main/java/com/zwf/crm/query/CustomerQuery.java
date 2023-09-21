package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-17 16:15
 */
public class CustomerQuery extends BaseQuery {
    private String customerName ;  //客户名字
    private String khno;  //客户Id

    private String level; //客户等级

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
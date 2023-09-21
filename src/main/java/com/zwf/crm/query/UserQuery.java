package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-13 16:55
 */
//用户表的多条件查询
public class UserQuery extends BaseQuery {
    private String userName; //用户名
    private String phone; //手机号码
    private String email;  //邮箱地址

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
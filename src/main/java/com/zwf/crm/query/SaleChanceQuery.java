package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-11 15:22
 */
//分页查询实体类
public class SaleChanceQuery extends BaseQuery {
    //客户名字
    private String customName;
    //创建者
    private String createMan;
    //状态  0表示未分配  1表示已分配
    private String state;
      //开发状态
    private Integer devResult;
    //指派人
    private Integer assignMan;

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(Integer assignMan) {
        this.assignMan = assignMan;
    }
}
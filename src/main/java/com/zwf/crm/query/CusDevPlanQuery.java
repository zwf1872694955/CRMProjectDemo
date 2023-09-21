package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-12 20:54
 */
public class CusDevPlanQuery extends BaseQuery {
    private Integer saleChanceId;  //营销机会Id

    public Integer getSaleChanceId() {
        return saleChanceId;
    }

    public void setSaleChanceId(Integer saleChanceId) {
        this.saleChanceId = saleChanceId;
    }
}
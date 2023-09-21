package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.SaleChance;
import com.zwf.crm.query.SaleChanceQuery;

import java.util.List;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {
    List<SaleChance> findSaleChanceByParams(SaleChanceQuery saleChanceQuery);
    //删除多条记录
    Integer deleteSaleChanceByIds(Integer[] ids);

}
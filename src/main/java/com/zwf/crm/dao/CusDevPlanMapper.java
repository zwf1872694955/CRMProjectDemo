package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.CusDevPlan;
import com.zwf.crm.query.CusDevPlanQuery;

import java.util.List;

public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {

     List<CusDevPlan> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery);
}
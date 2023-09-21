package com.zwf.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.CusDevPlanMapper;
import com.zwf.crm.dao.SaleChanceMapper;
import com.zwf.crm.pojo.CusDevPlan;
import com.zwf.crm.pojo.SaleChance;
import com.zwf.crm.query.CusDevPlanQuery;
import com.zwf.crm.query.SaleChanceQuery;
import com.zwf.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-12 20:56
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private CusDevPlanMapper cusDevPlanMapper;
    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String,Object> findCusDevPlanByParams(CusDevPlanQuery cdp){

        Map<String,Object> map=new HashMap<>();
        List<CusDevPlan> data = cusDevPlanMapper.queryCusDevPlanByParams(cdp);
        //返回分页对象
        map.put("code",0);  //code必须为0
        map.put("msg","");
        map.put("count",data.size());
        map.put("data",data);
        return map;
    }

    /**
     *   添加计划项
     *   计划项内容、计划项时间、营销机会id不能为空
     *   数据默认有效  创建时间 更新时间为当前时间
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        checkPlanItemParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan)<1,"计划项数据添加失败！");
    }

    private void checkPlanItemParams(CusDevPlan cusDevPlan) {
        AssertUtil.isTrue(null==cusDevPlan.getSaleChanceId()||saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId())==null,"系统异常请重试！");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanDate().toString()),"计划项时间不能为空！");

   }
    @Transactional(propagation = Propagation.REQUIRED)
   //更新操作
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        //判断id是否为空和是否存在
        AssertUtil.isTrue(null==cusDevPlan.getId()||cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId())==null,"数据不存在！");
        //检查计划项内容、计划项时间、营销机会Id是否为空
        checkPlanItemParams(cusDevPlan);
        //设置更新时间默认为当前时间
        cusDevPlan.setUpdateDate(new Date());
        //更新操作
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)<1,"数据更新失败！");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCusDevDataService(Integer cusId) {
        //判断id是否为空
        AssertUtil.isTrue(null==cusId,"系统异常！");
        //判断数据库中是否存在
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(cusId);
        AssertUtil.isTrue(null==cusDevPlan,"待删除的记录不存在！");
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)<1,"数据删除失败！");
    }
}
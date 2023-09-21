package com.zwf.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.SaleChanceMapper;
import com.zwf.crm.enums.DevResult;
import com.zwf.crm.enums.StateStatus;
import com.zwf.crm.pojo.SaleChance;
import com.zwf.crm.query.SaleChanceQuery;
import com.zwf.crm.utils.AssertUtil;
import com.zwf.crm.utils.PhoneUtil;
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
 * @date 2023-09-11 15:04
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String,Object> findSaleChanceByParam(SaleChanceQuery scq){
        Map<String,Object> map=new HashMap<>();
        //开启分页
        PageHelper.startPage(scq.getPage(),scq.getLimit());
        List<SaleChance> data = saleChanceMapper.findSaleChanceByParams(scq);
        //返回分页对象
        PageInfo<SaleChance> pageInfo=new PageInfo<>(data);
         map.put("code",0);  //code必须为0
         map.put("msg","营销机会数据列表");
         map.put("count",pageInfo.getTotal());
         map.put("data",pageInfo.getList());
        return map;
    }
      @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        //检查联系人 联系电话 客户名字
        checkCustomerParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //检查指派人
        CheckAssignManInfo(saleChance);
        //创建时间和更新时间设为当前时间
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //设置数据有效
        saleChance.setIsValid(1);
        //进行数据添加
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)<1,"数据添加失败！");
    }
     //更新营销机会数据对象
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        //查询数据库中是否有更新的数据和id
        AssertUtil.isTrue(null==saleChance,"待更新的数据不存在！");
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp==null,"待更新的数据不存在！");
        //查询客服姓名 联系人 联系电话是否合法
        checkCustomerParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //设置数据有效 更新时间为当前时间
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        //判断原始数据中的指派人是否设置
        //原始数据未设置指定人
         if(StringUtils.isBlank(temp.getAssignMan())){
             //修改后已设置指派人
             if(!StringUtils.isBlank(saleChance.getAssignMan())){
                 //指派时间为当前时间
                   saleChance.setAssignTime(new Date());
                 //设置为正在开发状态
                 saleChance.setDevResult(DevResult.DEVING.getStatus());
                 //设置为已分配状态
                 saleChance.setState(StateStatus.STATED.getType());
             }
         }else {
             //修改后未设置指派人
             if(StringUtils.isBlank(saleChance.getAssignMan())){
                 //设置指派时间为空
                 saleChance.setAssignTime(null);
                 //设置为正在未开发状态
                 saleChance.setDevResult(DevResult.UNDEV.getStatus());
                 //设置为未分配状态
                 saleChance.setState(StateStatus.UNSTATE.getType());
             }else {
                 //原始数据已设置指派人 修改后设置指派人
                 //设置的指派人是否与原始数据一致
                 if(!saleChance.getAssignMan().equals(temp.getAssignMan())){
                     //指派时间为当前时间
                     saleChance.setAssignTime(new Date());
                     //设置为正在开发状态
                     saleChance.setDevResult(DevResult.DEVING.getStatus());
                     //设置为已分配状态
                     saleChance.setState(StateStatus.STATED.getType());
                 }else {
                     saleChance.setAssignTime(new Date());
                 }
             }
         }
         //执行更新操作
        Integer i = saleChanceMapper.updateByPrimaryKeySelective(saleChance);
         AssertUtil.isTrue(i<1,"更新数据失败！");
    }

    private void CheckAssignManInfo(SaleChance saleChance) {
        if(StringUtils.isBlank(saleChance.getAssignMan())){  //如果指派人为空
            saleChance.setAssignTime(null);  //指派时间为空
            saleChance.setDevResult(DevResult.UNDEV.getStatus()); //未开发
            saleChance.setState(StateStatus.UNSTATE.getType());  //未分配
        }else {
            saleChance.setAssignTime(new Date());  //指派时间为空
            saleChance.setDevResult(DevResult.DEVING.getStatus()); //正在开发
            saleChance.setState(StateStatus.STATED.getType());  //已分配
        }

    }

    private void checkCustomerParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户姓名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"联系电话格式不正确！");
    }

    public void deleteSaleChanceService(Integer[] ids){
        AssertUtil.isTrue(ids.length==0,"待删除的数据不存在！");
        Integer row= saleChanceMapper.deleteSaleChanceByIds(ids);
        AssertUtil.isTrue(row!=ids.length,"数据删除失败！");
    }

    public void updateDevStatusDataService(Integer devStatus, Integer salChanceId) {
        AssertUtil.isTrue(null==salChanceId,"待更新的数据不存在！");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(salChanceId);
        AssertUtil.isTrue(saleChance==null,"待更新的数据不存在！");
        saleChance.setDevResult(devStatus); //更新开发状态
        saleChance.setUpdateDate(new Date());//设置当前时间是更新时间
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"开发状态更新失败！");


    }
}
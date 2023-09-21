package com.zwf.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.CustomerMapper;
import com.zwf.crm.pojo.Customer;
import com.zwf.crm.query.CustomerQuery;
import com.zwf.crm.utils.AssertUtil;
import com.zwf.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-17 16:32
 */
@Service
public class CustomerService extends BaseService<Customer,Integer> {
      @Resource
    private CustomerMapper customerMapper;

      public Map<String,Object> queryCustomerDataByParams(CustomerQuery customerQuery){
          //开启分页
          PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
          List<Customer> customers = customerMapper.selectByParams(customerQuery);
          PageInfo<Customer> pageInfo=new PageInfo<>(customers);
          Map<String,Object> maps=new HashMap<>();
          maps.put("code",0);
          maps.put("msg","");
          maps.put("count",pageInfo.getTotal());
          maps.put("data",pageInfo.getList());
          return maps;
      }

    /**
     *   验证 客户名  非空  唯一
     *   联系电话  非空  符合规则
     *   法人代表  非空
     *   客户编码  随机生成  KH+时间戳
     *   设置默认值
     *   有效   流失状态正常  更新时间  创建时间
     * @param customer
     */
    @Transactional
    public void saveCustomerDataService(Customer customer) {
       checkCustomerParams(customer.getName(),customer.getFr(),customer.getPhone());
       Customer cus=customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(cus!=null,"客户名已被占用！");
        String khno="KH"+System.currentTimeMillis();
        customer.setKhno(khno);
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerMapper.insertSelective(customer)<1,"客户数据添加失败！");
    }

    private void checkCustomerParams(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人代表不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"联系电话不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"电话格式不正确！");
    }

     //判断待更新的记录是否存在
    //判断客户名是否存在 且不是自己
    //判断电话号码是否为空
    //判断法人是否为空
    //设置更新时间  数据更新
    @Transactional
    public void updateCustomerDataService(Customer customer) {
        AssertUtil.isTrue(customer.getId()==null,"待更新记录不存在！");
        Customer customer1 = customerMapper.selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(null==customer1,"待更新记录不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()),"客户名称不能为空！");
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(temp!=null&&!(temp.getId().equals(customer.getId())),"客户名称已被占用！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()),"法人代表不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()),"电话号码不能为空！");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(customer.getPhone())),"电话号码格式不正确！");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer)<1,"客户信息更新失败！");
    }
       @Transactional
    public void deleteCustomerDataService(Integer customerId) {
        AssertUtil.isTrue(customerId==null,"待删除的记录不存在！");
        Customer temp = customerMapper.selectByPrimaryKey(customerId);
        AssertUtil.isTrue(temp==null,"待删除的记录不存在！");
        temp.setIsValid(0);
        temp.setUpdateDate(new Date());
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(temp)<1,"客户信息删除失败！");
    }
}
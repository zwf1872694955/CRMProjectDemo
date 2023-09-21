package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.dao.CustomerOrderMapper;
import com.zwf.crm.pojo.Customer;
import com.zwf.crm.pojo.CustomerOrder;
import com.zwf.crm.query.CustomerQuery;
import com.zwf.crm.service.CustomerOrderService;
import com.zwf.crm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-17 16:44
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;
    @Resource
    private CustomerOrderService customerOrderService;

    @GetMapping("/list")
    @ResponseBody
    public Map<String,Object> queryCustomerDataByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerDataByParams(customerQuery);
    }

    @GetMapping("/index")
    public String goCustomerIndexPage(){
        return "customer/customer";
    }

    @GetMapping("/goSaveOrUpdatePage")
    public String goSaveOrAddPage(Integer customerId, Model model){
        Customer customer = customerService.selectByPrimaryKey(customerId);
        model.addAttribute("customer",customer);
        return "customer/add_update";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultInfo saveCustomerData(Customer customer){
        customerService.saveCustomerDataService(customer);
        return success("客户信息添加成功！");
    }
    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateCustomerData(Customer customer){
        customerService.updateCustomerDataService(customer);
        return success("客户信息更新成功！");
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteCustomerData(Integer customerId){
        customerService.deleteCustomerDataService(customerId);
        return success("客户信息删除成功！");
    }

    @GetMapping("/goOrderPage")
    public String deleteCustomerData(Integer customerId,Model model){
        Customer customer = customerService.selectByPrimaryKey(customerId);
        model.addAttribute("customer",customer);
        return "customer/customer_order";
    }

}
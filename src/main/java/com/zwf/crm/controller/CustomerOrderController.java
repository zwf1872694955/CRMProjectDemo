package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.pojo.CustomerOrder;
import com.zwf.crm.pojo.Order;
import com.zwf.crm.query.OrderQuery;
import com.zwf.crm.service.CustomerOrderService;
import com.zwf.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-18 9:34
 */
@RequestMapping("/order")
@Controller
public class CustomerOrderController extends BaseController {
    @Resource
    private CustomerOrderService customerOrderService;
    @Resource
    private OrderDetailsService orderDetailsService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> goOrderListByCusId(Integer cusId, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10")Integer limit){
        List<CustomerOrder> customerOrders = customerOrderService.queryOrderByCusId(cusId);
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setCustomerOrderList(customerOrders);
        orderQuery.setPage(page);
        orderQuery.setLimit(limit);
        Map<String,Object> maps=customerOrderService.queryOrderDetailByTable(orderQuery);
        return maps;
    }

    @GetMapping("/orderDetailPage")
    public String goOrderDetailByOrderId(Integer orderId, Model model){
        Order order = orderDetailsService.queryOrderDetailByOrderId(orderId);
          model.addAttribute("order",order);
        return "customer/customer_order_detail";
    }


}
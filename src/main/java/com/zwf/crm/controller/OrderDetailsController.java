package com.zwf.crm.controller;

import com.zwf.crm.dao.OrderDetailsMapper;
import com.zwf.crm.pojo.OrderDetails;
import com.zwf.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-18 11:59
 */
@Controller
@RequestMapping("/orderDetails")
public class OrderDetailsController {
    @Resource
    private OrderDetailsService orderDetailsService;

    @GetMapping("/list")
    @ResponseBody
    public Map<String,Object> getOrderDetailsData(Integer orderId, @RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "1") Integer limit){
        return orderDetailsService.queryOrderAllByOrderId(orderId,page,limit);
    }
}
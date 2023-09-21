package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.enums.StateStatus;
import com.zwf.crm.pojo.CusDevPlan;
import com.zwf.crm.pojo.SaleChance;
import com.zwf.crm.query.CusDevPlanQuery;
import com.zwf.crm.query.SaleChanceQuery;
import com.zwf.crm.service.CusDevPlanService;
import com.zwf.crm.service.SaleChanceService;
import com.zwf.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-12 19:35
 */
@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {
   @Resource
    private SaleChanceService saleChanceService;
   @Resource
   private CusDevPlanService cusDevPlanService;

    @RequestMapping("/index")
    public String goIndex(){
        return "cusDevPlan/cus_dev_plan";
    }
    @RequestMapping("/toCusDevPlanDataPage")
 public String toCusDevPlanDataPage(Integer sid, Model model){
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(sid);
          model.addAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";

 }

    @GetMapping("/list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlanList(CusDevPlanQuery cdp){
        return cusDevPlanService.findCusDevPlanByParams(cdp);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultInfo insertCusDevPlanData(CusDevPlan cusDevPlan){
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划项数据添加成功！");

    }
    //打开添加计划项窗口
    @GetMapping("/goAddCusDevPlanPage")
    public String goAddCusDevPlanPage(Integer sId,Model model,Integer cusDevId){
        model.addAttribute("SId",sId);
        //根据客户开发计划ID查询客户开发计划
        CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(cusDevId);
        model.addAttribute("cusDevPlan",cusDevPlan);
        return "cusDevPlan/add_update";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateCusDevPlanData(CusDevPlan cusDevPlan){
//        System.out.println(cusDevPlan.getPlanItem());
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项数据修改成功！");

    }

    @PostMapping("/deleteCusDev")
    @ResponseBody
    public ResultInfo deleteCusDevData(Integer cusId){
        cusDevPlanService.deleteCusDevDataService(cusId);
        return success("计划项数据删除成功！");

    }

}
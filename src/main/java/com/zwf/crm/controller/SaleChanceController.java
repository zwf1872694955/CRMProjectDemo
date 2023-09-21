package com.zwf.crm.controller;

import com.zwf.crm.anno.AuthorAnnotation;
import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.enums.StateStatus;
import com.zwf.crm.pojo.SaleChance;
import com.zwf.crm.query.SaleChanceQuery;
import com.zwf.crm.service.SaleChanceService;
import com.zwf.crm.utils.CookieUtil;
import com.zwf.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-11 15:05
 */
@Controller
@RequestMapping("/sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> querySaleChanceList(SaleChanceQuery scq, HttpServletRequest request,Integer flag){
        //客户开发计划
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if(flag!=null&&flag==1){
            //设置分配状态
            scq.setState(String.valueOf(StateStatus.STATED.getType()));
             //把当前用户设置为指派人
            scq.setAssignMan(userId);
        }

        return saleChanceService.findSaleChanceByParam(scq);
    }
    @AuthorAnnotation(grantCode = "10")
    @RequestMapping("/index")
    public String goIndex(){
        return "saleChance/sale_chance" ;
    }
    @AuthorAnnotation(grantCode = "101002")
    @PostMapping("/add")
     @ResponseBody
     public ResultInfo addSaleChance(HttpServletRequest request, SaleChance saleChance){
        //从cookie中获取用户名
        String username = CookieUtil.getCookieValue(request, "username");
        saleChanceService.addSaleChance(saleChance);
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setMsg("数据添加成功！");
        return resultInfo;
     }

     @GetMapping("/saleChancePage")
     public String goAddPage(Integer saleChanceId, Model model){
           //根据主键查询数据对象
         SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
         model.addAttribute("saleChance",saleChance);
         return "saleChance/add_update";
     }
    @AuthorAnnotation(grantCode = "101004")
    @PostMapping("/update")
    @ResponseBody
     public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("数据修改成功!");
     }
    @AuthorAnnotation(grantCode = "101003")
    @PostMapping("/delete")
    @ResponseBody
     public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChanceService(ids);
        return success("数据删除成功！");
     }
    @PostMapping("/updateDevStatus")
    @ResponseBody
    public ResultInfo updateDevStatusData(Integer devStatus,Integer salChanceId){
        saleChanceService.updateDevStatusDataService(devStatus,salChanceId);

        return success("开发状态更新成功！");
    }



}
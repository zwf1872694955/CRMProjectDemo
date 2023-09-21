package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.pojo.Module;
import com.zwf.crm.pojo.ModuleModel;
import com.zwf.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-15 14:55
 */
@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;
    @PostMapping("/list")
    @ResponseBody
    public List<ModuleModel> queryModuleAll(Integer roleId){
        List<ModuleModel> moduleModels = moduleService.queryAllModuleName(roleId);
        return moduleModels;
    }
    //进入module树形表格界面
    @GetMapping("/index")
    public String goModulePage(){

        return "module/module";
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String,Object> queryAllModulesData(){
      return  moduleService.queryAllModulesByTables();
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addModuleInfo(Module module){
         moduleService.addModuleInfoData(module);
        return success("菜单信息添加成功！");
    }

    @GetMapping("/goAddModulePage")
    public String goAddModulePage(Integer grade, Integer parentId, Model model){
        model.addAttribute("grade",grade);
        model.addAttribute("parentId",parentId);
        return "module/add";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateModuleInfo(Module module){
        moduleService.updateModuleData(module);
        return success("菜单信息更新成功！");
    }

     @GetMapping("/goUpdateModulePage")
    public String goUpdateModulePage(Integer moduleId,Model model){
        Module module = moduleService.selectByPrimaryKey(moduleId);
        model.addAttribute("module",module);
        return "module/update";

    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteModuleData(Integer moduleId){
          moduleService.deleteModuleDataService(moduleId);
        return success("菜单数据删除成功！");
    }

}
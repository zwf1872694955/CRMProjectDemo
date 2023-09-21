package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.pojo.Role;
import com.zwf.crm.query.RoleQuery;
import com.zwf.crm.service.RoleService;
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
 * @date 2023-09-14 14:00
 */
@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @RequestMapping("/lists")
    @ResponseBody
    public List<Map<String,Object>> queryRoleNamesController(Integer userId){
        return roleService.queryRolesService(userId);

    }

    @GetMapping("/params")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){

         return roleService.queryByParamsForTable(roleQuery);

    }
    //跳转到角色管理
    @GetMapping("/index")
    public String goRolePage(){
        return "role/role";
    }

    //新增用户信息
    @PostMapping("/add")
    @ResponseBody
    public ResultInfo addRoleInfo(Role role){
            roleService.AddRoleService(role);
        return success("用户数据新增成功！");

    }
    @GetMapping("/goAddOrUpdateDialog")
    public String goAddOrUpdateDialog(Integer roleId, Model model){
        Role role = roleService.selectByPrimaryKey(roleId);
        model.addAttribute("role",role);
        return "role/add_update";
    }

     @PostMapping("/update")
     @ResponseBody
    public ResultInfo updateRoleInfoData(Role role){
      roleService.updateRoleService(role);
        return success("用户数据更新成功！");
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo deleteRoleInfoData(Integer roleId){
        roleService.deleteRoleService(roleId);
        return success("用户数据删除成功！");
    }

    //进入树状页面
    @RequestMapping("/goModulePage")
    public String goGrantPage(Integer roleId,Model model){
        model.addAttribute("roleId",roleId);
        return "role/grant";
    }
    //进行角色授权管理

    @PostMapping("/grantModule")
    @ResponseBody
    public ResultInfo grantRoleToModule(Integer roleId,Integer[] moduleIds){
        roleService.grantRoleToModuleService(roleId,moduleIds);
        return success("角色授权成功");
    }


}
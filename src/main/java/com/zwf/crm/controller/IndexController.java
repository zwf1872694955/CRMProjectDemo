package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.dao.UserMapper;
import com.zwf.crm.pojo.User;
import com.zwf.crm.service.PermissionService;
import com.zwf.crm.service.UserService;
import com.zwf.crm.utils.CookieUtil;
import com.zwf.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-09 10:25
 */
@Controller
public class IndexController extends BaseController{
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String goIndex(){
        return "index";
    }
    @RequestMapping("/main")
    public String goMain(HttpServletRequest request){
        //获取cookie中的值 并进行解密
        Integer useId = LoginUserUtil.releaseUserIdFromCookie(request);
         //通过UserId查询用户名  存入session中
        User user = userService.selectByPrimaryKey(useId);
        //查询用户所拥有的权限码
        List<String> aclValues=permissionService.queryAclValuesByUserId(useId);
        request.getSession().setAttribute("grantCode",aclValues);
        request.getSession().setAttribute("user",user);
        return "main";
    }
   @RequestMapping("/welcome")
    public String goWelcome(){
        return "welcome";
    }

}
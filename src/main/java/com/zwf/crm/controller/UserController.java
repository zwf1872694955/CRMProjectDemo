package com.zwf.crm.controller;

import com.zwf.crm.base.BaseController;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.exceptions.ParamsException;
import com.zwf.crm.pojo.User;
import com.zwf.crm.query.UserQuery;
import com.zwf.crm.service.UserService;
import com.zwf.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-09 15:23
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

     @PostMapping("/login")
     @ResponseBody
    public ResultInfo loginController(String username,String password){
        ResultInfo resultInfo=new ResultInfo();
            ResultInfo resultInfo1 = userService.LoginService(username, password);
            resultInfo.setResult(resultInfo1.getResult());
        return resultInfo;
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public ResultInfo updatePwdController(HttpServletRequest request,String oldPassword, String newPassword, String confirmPassword){

        int userId = LoginUserUtil.releaseUserIdFromCookie(request);

            ResultInfo resultInfo = userService.updatePasswordService(userId, oldPassword, newPassword, confirmPassword);

        return resultInfo;

    }
     @GetMapping("/toPasswordPage")
    public String toPasswordPage(){

         return "user/password";
    }
    @GetMapping("/assign")
    @ResponseBody
    public List<Map<String,Object>> selectAssignMan(){
         return userService.queryAssignManName();
    }

     //多条件查询
    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> queryUserByParams(UserQuery userQuery){
        Map<String, Object> maps = userService.queryByParamsForTable(userQuery);
        return maps;
    }

     @GetMapping("/index")
    //进入用户列表页面
    public String goUserPage(){
         return "user/user";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultInfo saveUserInfoController(User user){
         userService.insertUserInfoService(user);
         return success("数据添加成功！");
    }

    //打开添加窗口
    @GetMapping("/openSaveUserPage")
    public String goSaveUserPage(Integer userId, Model model){
        User user = userService.selectByPrimaryKey(userId);
        model.addAttribute("userInfo",user);
        return "user/add_update";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultInfo updateUserInfoController(User user){
        userService.updateUserInfoService(user);
        return success("用户信息更新成功！");
    }
    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo DeleteUserInfoController(Integer[] ids){
          userService.deleteUserInfoByIdsService(ids);
         return success("用户数据删除成功！");
    }





}
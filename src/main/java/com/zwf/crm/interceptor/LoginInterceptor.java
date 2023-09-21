package com.zwf.crm.interceptor;

import com.zwf.crm.exceptions.NoLoginException;
import com.zwf.crm.service.UserService;
import com.zwf.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-11 11:04
 */
public class LoginInterceptor implements HandlerInterceptor {
     @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中id和查询数据库中的id是否存在！
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        //如果cookie中没有id或者 未查询到数据库未查询到该Id
        if(null==id||userService.selectByPrimaryKey(id)==null){
            throw new NoLoginException();
        }

        return true;
    }
}
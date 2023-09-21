package com.zwf.crm;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.exceptions.AuthorException;
import com.zwf.crm.exceptions.NoLoginException;
import com.zwf.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-11 9:54
 */
//定义全局异常 实现接口
@Component  //IOC进行托管
public class GlobalExceptionHandle implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //如果是未登录异常  则重定向到登录页面
        if (ex instanceof NoLoginException){
            ModelAndView modelAndView=new ModelAndView("redirect:/index");
            return modelAndView;
        }

        //设置默认的异常视图
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("code","500");
        mv.addObject("msg","系统异常！");
        //判断响应的处理方法上是否声明@ResponseBody 如果声明了就返回JSON数据 如果没声明则返回视图
        if(handler instanceof HandlerMethod){
            HandlerMethod hm= (HandlerMethod) handler;
            //获取处理方法上的注解
            ResponseBody responseBody = hm.getMethod().getDeclaredAnnotation(ResponseBody.class);
            //判断注解是否存在
            if (responseBody==null){
                //注解不存在返回视图 判断异常是否是自定义注解
                if(ex instanceof ParamsException){
                    ParamsException p= (ParamsException) ex;
                    mv.addObject("code",p.getCode());
                    mv.addObject("msg",p.getMsg());
                } else if (ex instanceof AuthorException) {
                    AuthorException a=(AuthorException) ex;
                    mv.addObject("code",a.getCode());
                    mv.addObject("msg",a.getMsg());
                }
                return mv;

            }else {
                //存在注解返回json数据
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常！");
                if(ex instanceof ParamsException){
                    ParamsException p= (ParamsException) ex;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                }else if (ex instanceof AuthorException) {
                    AuthorException a=(AuthorException) ex;
                    resultInfo.setCode(a.getCode());
                    resultInfo.setMsg(a.getMsg());
                }
                response.setContentType("application/json;charset=utf8");
                //用响应流字符流返回json字符串
                String json = JSON.toJSONString(resultInfo);
                PrintWriter ps=null;
                try {
                   ps=response.getWriter();
                   ps.write(json);
                   ps.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                    //没有关闭流导致的未能抛出JSON字符串
                }finally {
                    if(ps!=null){
                        ps.close();
                    }
                }
                return null;

            }

        }
        return mv;
    }
}
package com.zwf.crm.aspect;


import com.zwf.crm.anno.AuthorAnnotation;
import com.zwf.crm.exceptions.AuthorException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-16 9:56
 */
//设置切面 要由IOC托管
@Aspect
@Component
public class AuthorAspect {
    @Autowired
    private HttpSession session;
    //拦截自定义注解进行权限码判断
     @Around(value = "@annotation(com.zwf.crm.anno.AuthorAnnotation)")   //环绕通知
    public Object authorAdvice(ProceedingJoinPoint pjp) throws Throwable {
           //从session中获取登录用户的授权码
         List<String> grantCodes = (List<String>)session.getAttribute("grantCode");
         //获取声明注解的方法上自定义注解的授权码
        MethodSignature methodSignature=(MethodSignature)pjp.getSignature();
         AuthorAnnotation annotation = methodSignature.getMethod().getAnnotation(AuthorAnnotation.class);
         String code = annotation.grantCode();
//         System.out.println(grantCodes+","+code);
         if(grantCodes==null||grantCodes.size()==0){
             throw new AuthorException();
         }
         //session中不包含注解声明的授权码抛出异常
         if(!grantCodes.contains(code)){
             throw new AuthorException();
         }
         Object proceed = pjp.proceed();
         return proceed;
     }

}
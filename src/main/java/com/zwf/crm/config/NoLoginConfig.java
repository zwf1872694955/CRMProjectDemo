package com.zwf.crm.config;

import com.zwf.crm.interceptor.LoginInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-11 11:09
 */
@Configuration
public class NoLoginConfig extends WebMvcConfigurationSupport{

    //把自定义的拦截器注册到Bean中
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
     //修饰符对页面渲染会产生影响
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //默认拦截所有
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/js/**","/images/**","/css/**","/lib/**","/user/login","/index");
    }
     //静态文件放行 设置静态资源
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/","classpath:/views/");
        super.addResourceHandlers(registry);
    }
}
package com.zwf.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-09 10:13
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zwf.crm"})
@EnableAspectJAutoProxy
public class SpringBootApplicationStarter extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootApplicationStarter.class);
    }
}
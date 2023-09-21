package com.zwf.crm.anno;

import java.lang.annotation.*;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-16 9:48
 */
//自定义权限授权注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorAnnotation {
    String grantCode() default "";
}

package com.simple.service.limit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhangshaolong001
 * @Date: 2020/8/17 3:57 PM
 * @Description：监控注解
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiLimit {
    String value() default "UnKnowName";
}

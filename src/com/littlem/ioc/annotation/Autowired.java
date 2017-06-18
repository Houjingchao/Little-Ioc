package com.littlem.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hjc on 2017/6/18.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)//注解信息会被保留
public @interface Autowired {
    /**
     * 要注入的类型
     */
    Class<?> value() default Class.class;

    /**
     * bean 的名字
     */
    String name() default "";
}

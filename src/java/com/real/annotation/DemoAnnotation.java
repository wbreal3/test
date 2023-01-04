package com.real.annotation;

import java.lang.annotation.*;

/**
 * 注解测试
 * @author wangbing
 * @Date 2023/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface DemoAnnotation {
    /** 默认方法打印属性 */
    String value() default "";
}

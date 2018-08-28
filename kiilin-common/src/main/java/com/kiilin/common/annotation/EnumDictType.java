package com.kiilin.common.annotation;


import java.lang.annotation.*;

/**
 * 字典枚举自定义注解
 *
 * @author wangkai
 */
// 在运行时执行
@Retention(RetentionPolicy.RUNTIME)
// 注解适用地方
@Target({ElementType.TYPE})
// 说明该注解将被包含在javadoc中
@Documented
public @interface EnumDictType {

    String type() default "";
}

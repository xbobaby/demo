package com.bob.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisAnnotation {
    int QUERY = 1;
    int SAVE = 2;
    int DELETE = 3;

    //缓存KEY
    String key() default "";
    //取值表达式
    String value() default "";
    int expire() default -1;
    int operation() default QUERY;
}

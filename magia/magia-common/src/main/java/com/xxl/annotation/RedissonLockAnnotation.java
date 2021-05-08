package com.xxl.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedissonLockAnnotation {
    String key();

    long retriesTime() default 5000L;

    long lockTime() default 0L;
}

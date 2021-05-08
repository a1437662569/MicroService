package com.xxl.config;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(-1)
public class DataBaseAspect {


    /**
     * 使用第一个数据源的路径
     */
    @Pointcut("execution(* com.xxl.mapper.master.*.*(..))")
    public void dbPointCut() {}

    /**
     * 使用第二个数据源的路径
     */
    @Pointcut("execution(* com.xxl.mapper.read.*.*(..))")
    public void dbPointCut2() {}

    @Before("dbPointCut()")
    public void beforeSwitchDS(JoinPoint point) {
        DataSourceContextHolder.setDbType(DBTypeEnum.MASTER);
    }

    @Before("dbPointCut2()")
    public void change2(JoinPoint point) {
        DataSourceContextHolder.setDbType(DBTypeEnum.READ);
    }

    @Before("@annotation(com.baomidou.dynamic.datasource.annotation.DS)&&@annotation(ds)")
    public void mybatisPlusDS(JoinPoint point,DS ds) {
        DataSourceContextHolder.setDbType(ds.value());
    }

    @After("dbPointCut()")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDbType();
    }
}


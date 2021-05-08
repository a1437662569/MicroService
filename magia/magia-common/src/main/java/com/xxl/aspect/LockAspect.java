package com.xxl.aspect;

import com.xxl.annotation.RedissonLockAnnotation;
import com.xxl.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 这里主要是通过切面的方式给部分接口加锁，从而减少手动加锁的步骤
 */
@Aspect
@Component
@Slf4j
public class LockAspect {
    @Autowired
    private RedissonClient redisson;


    @Pointcut("@annotation(com.xxl.annotation.RedissonLockAnnotation)")
    public void lockAspect() {
    }

    @Around("lockAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLockAnnotation locking = method.getDeclaredAnnotation(RedissonLockAnnotation.class);
        String prefix = "Redisson:" + joinPoint.getTarget().getClass().getSimpleName() + "." + method.getName() + ".";
        Object proceed = null;
        if (locking != null) {
            boolean isLock = false;
            try {
                ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
                String[] parameterNames = discoverer.getParameterNames(method);
                Object[] args = joinPoint.getArgs();
                if (parameterNames != null) {
                    ExpressionParser parser = new SpelExpressionParser();
                    EvaluationContext ctx = new StandardEvaluationContext();
                    int len = Math.min(args.length, parameterNames.length);
                    for (int i = 0; i < len; i++) {
                        ctx.setVariable(parameterNames[i], args[i]);
                    }
                    String annotationKey = locking.key();
                    Object value = null;
                    if (StringUtil.isNotEmpty(annotationKey)) {
                        if (annotationKey.startsWith("#")) {
                            value = parser.parseExpression(locking.key()).getValue(ctx);
                        } else {
                            value = annotationKey;
                        }
                    }
                    long lockTime = locking.lockTime();
                    String key = prefix + value;
                    RLock lock = redisson.getLock(key);
                    log.info("正在尝试加锁,key:{},超时时间:{}", key, lockTime);
                    try {
                        if (0L == lockTime) {
                            isLock = lock.tryLock(locking.retriesTime(), TimeUnit.MILLISECONDS);
                        } else {
                            isLock = lock.tryLock(locking.retriesTime(), lockTime, TimeUnit.MILLISECONDS);
                        }
                        if (isLock) {
                            log.info("加锁结果:成功,正在处理业务,key:{},超时时间:{}", key, lockTime);
                            proceed = joinPoint.proceed();
                        } else {
                            log.info("加锁结果:失败,key:{},超时时间:{}", key, lockTime);
                        }
                    } finally {
                        if (isLock && lock.isLocked()) {
                            log.info("业务处理结束,释放锁,key:{},超时时间:{}", key, lockTime);
                            lock.unlock();
                        }
                    }
                }
            } catch (Exception e) {
                log.error(StringUtil.printExcetion(e));
            }
        }
        return proceed;
    }

}

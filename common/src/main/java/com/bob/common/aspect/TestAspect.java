package com.bob.common.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {
    Logger Logger = LoggerFactory.getLogger(TestAspect.class);
    @Pointcut("@annotation(org.springframework.beans.factory.annotation.Value)")
    public void aspectTest(){

    }

    @Around("aspectTest()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Logger.info("around------------beginTime--------------------");
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] s = point.getArgs();

        //执行方法
        Object result = point.proceed();

        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        Logger.info("执行{}，耗时{}",className+"."+methodName+"("+JSON.toJSONString(s)+")",time);
        return result;
    }
}

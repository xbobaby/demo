package com.bob.common.aspect;

import com.alibaba.fastjson.JSON;
import com.bob.common.annotation.RedisAnnotation;
import com.bob.common.util.RedissonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class RedisAspect {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RedissonClient redissonClient;

    @Pointcut("@annotation(com.bob.common.annotation.RedisAnnotation)")
    public void redisAspect() {

    }

    @Around("redisAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start("redisAspect around");
        logger.info("redisAspect around executing");
        Object result = null;

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String className = point.getTarget().getClass().getName();
        String methodName = methodSignature.getName();
        Object[] args = point.getArgs();
        String[] paramNames = methodSignature.getParameterNames();

        Method method = methodSignature.getMethod();

        RedisAnnotation redisAnnotation =  method.getAnnotation(RedisAnnotation.class);
        String key = redisAnnotation.key();
        int expire = redisAnnotation.expire();
        int operation = redisAnnotation.operation();
        String value = redisAnnotation.value();
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
        Matcher matcherKey = pattern.matcher(key);
        String cacheKey = "";
        if (matcherKey.find()) {
//            String value =parser.parseExpression("#"+matcher.group(),parserContext).getValue(context, String.class);
            String keyString = parser.parseExpression("#" + matcherKey.group()).getValue(context, String.class);
            cacheKey = key.substring(0, key.indexOf("#{")) + keyString;
        }

        // ParserContext parserContext = new TemplateParserContext();

        if (RedisAnnotation.QUERY == operation) {//查询
            RBucket<String> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
            Object o = keyObject.get();
            if (o != null) {
                logger.info("RedisAspect query key:{} success",cacheKey);
                result = o;
            } else {
                logger.info("RedisAspect query key:{} failed",cacheKey);
                result = point.proceed();
            }
        } else if (RedisAnnotation.SAVE == operation) {//保存
//            logger.info("RedisAspect save key:{}",cacheKey);
            Matcher matcherValue = pattern.matcher(value);
            Object object = null;
            if (matcherValue.find()) {
                object = parser.parseExpression("#" + matcherValue.group()).getValue(context, Object.class);
            }
            RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
            if (expire == -1) {
                keyObject.set(object);
            } else {
                keyObject.set(object, expire, TimeUnit.SECONDS);
            }
            result = point.proceed();
            logger.info("RedisAspect save key:{} success",cacheKey);
        } else if (RedisAnnotation.DELETE == operation) {//删除
//            logger.info("RedisAspect delete key:{}",cacheKey);
            RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
            keyObject.delete();
            result = point.proceed();
            logger.info("RedisAspect delete key:{} success",cacheKey);
        } else {
            //执行方法
            result = point.proceed();
        }


        //执行时长(毫秒)
        sw.stop();
        logger.info("执行{}，耗时{}", className + "." + methodName + "(" + JSON.toJSONString(args) + ")", sw.getTotalTimeMillis());
        return result;
    }
}

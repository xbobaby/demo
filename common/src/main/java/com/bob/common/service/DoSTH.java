package com.bob.common.service;

import com.alibaba.fastjson.JSON;
import com.bob.common.annotation.RedisAnnotation;
import com.bob.common.annotation.TestAnnotation;
import com.bob.common.aspect.TestAspect;
import com.bob.common.util.RedissonUtils;
import com.bob.common.constant.CONSTANT;
import com.bob.common.vo.User;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoSTH {
    org.slf4j.Logger Logger = LoggerFactory.getLogger(TestAspect.class);

    @Autowired
    RedissonClient redissonClient;

    @TestAnnotation
    public void doSomeThing(User user) {
        Logger.info("doSomeThing===================================={}", JSON.toJSONString(user));
//        RedissonClient redissonClient =  redissonAutoConfiguration.redissonSingle();
        RBucket<String> keyObject = RedissonUtils.getRBucket(redissonClient, "key1");
        keyObject.set("bob1");

    }

    @RedisAnnotation(key = CONSTANT.CACHEKEY.CACHE_USER_ID_KEY+"#{id}")
    public User getUser(String id) {
        User user = new User();
        user.setId("2");
        user.setName("lucy");
        Logger.info("DoSTH.getUser===================================={}", JSON.toJSONString(user));
        return user;
    }

    @RedisAnnotation(key = CONSTANT.CACHEKEY.CACHE_USER_ID_KEY+"#{user.id}",operation = RedisAnnotation.SAVE,value="#{user}",expire = 5)
    public void saveUser(User user) {
        Logger.info("DoSTH.saveUser===================================={}", JSON.toJSONString(user));
    }
}

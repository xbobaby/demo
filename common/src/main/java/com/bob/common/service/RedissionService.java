package com.bob.common.service;

import com.bob.common.util.RedissonUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissionService {

    @Autowired
    RedissonClient redissonClient;

    public Object get(String cacheKey){
        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        return keyObject.get();
    }

    public void set(String cacheKey,Object object){
        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        keyObject.set(object);
    }

    public void set(String cacheKey,Object object,int expire){
        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        keyObject.set(object,expire, TimeUnit.SECONDS);
    }

    public boolean delete(String cacheKey){
        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        return keyObject.delete();
    }
}

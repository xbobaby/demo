package com.bob.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.common.service.RedissionService;
import com.bob.common.util.RedissonUtils;
import com.bob.common.vo.RO;
import com.bob.system.entity.StUser;
import com.bob.system.service.IStUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author bob
 * @since 2018-10-14
 */
@RestController
@RequestMapping("/system/st-user")
@Api(value = "StUserController|用户数据操作的控制器")
public class StUserController {

    @Reference(version = "${bob.service.version}")
    IStUserService stUserService;

    @Autowired
    RedissionService redissionService;


    @PostMapping(value = "/allusers", produces = {"application/json;charset=UTF-8"})
    public RO getAllUser(){
        List list = stUserService.listMaps(null);
        return new RO().put("list",list);
    }

    @PostMapping(value = "/getUserByDeptId", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据传入的user对象分页查询用户" , notes = "值通过user传入" )
    public RO<StUser> getUserByDeptId(StUser user){
        Page<StUser> page = stUserService.selectUserByDeptId(new Page<>(1, 1),user);
        return new RO(page);
    }

    @PostMapping(value = "/redisTest", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "redis缓存测试" , notes = "查询redis缓存" )
    public RO<StUser> redisTest(String cacheKey){
//        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        return new RO().put("object",redissionService.get(cacheKey));
    }
}

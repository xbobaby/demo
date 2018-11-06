package com.bob.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.common.constant.ERRORCODE;
import com.bob.common.service.RedissionService;
import com.bob.common.vo.RO;
import com.bob.system.entity.SysUser;
import com.bob.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@RestController
@RequestMapping("/system/sys-user")
@Api(value = "SysUserController|用户数据操作的控制器")
public class SysUserController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(version = "${bob.service.version}")
    ISysUserService sysUserService;

    @Autowired
    RedissionService redissionService;


    @PostMapping(value = "/allusers", produces = {"application/json;charset=UTF-8"})
    public RO getAllUser(){
        List list = sysUserService.listMaps(new QueryWrapper<SysUser>(new SysUser()));
        return new RO().put("list",list);
    }

    @PostMapping(value = "/getUserByDeptId", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据传入的user对象分页查询用户" , notes = "值通过user传入" )
    public RO<SysUser> getUserByDeptId(SysUser user){
        IPage<SysUser> page = sysUserService.page(new Page<>(1, 1),new QueryWrapper<SysUser>(user));
        return new RO(page);
    }

    @PostMapping(value = "/redisTest", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查询redis缓存测试" , notes = "查询redis缓存" )
    public RO<SysUser> redisTest(String cacheKey){
//        RBucket<Object> keyObject = RedissonUtils.getRBucket(redissonClient, cacheKey);
        return new RO().put("object",redissionService.get(cacheKey));
    }

    @PostMapping(value = "/redisDeleteTest", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除redis缓存测试" , notes = "删除redis缓存" )
    public RO<SysUser> redisDeleteTest(String cacheKey){
        logger.info("删除redis缓存,key:{}",cacheKey);
        if (redissionService.delete(cacheKey)){
            return new RO();
        }else{
            return new RO().setError(ERRORCODE.FAILED);
        }

    }
}

package com.bob.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.system.entity.StUser;
import com.bob.system.service.IStUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @RequestMapping(value = "/allusers", produces = {"application/json;charset=UTF-8"})
    public List getAllUser(){
        List list = stUserService.listMaps(null);
        return list;
    }

    @PostMapping(value = "/getUserByDeptId", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据传入的user对象分页查询用户" , notes = "值通过user传入" )
    public Page<StUser> getUserByDeptId(StUser user){
        Page<StUser> page = stUserService.selectUserByDeptId(new Page<>(1, 1),user);
        return page;
    }
}

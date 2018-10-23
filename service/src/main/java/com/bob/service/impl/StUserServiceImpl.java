package com.bob.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.system.entity.StUser;
import com.bob.system.mapper.StUserMapper;
import com.bob.system.service.IStUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author bob
 * @since 2018-10-14
 */
@Service(version = "${bob.service.version}")
public class StUserServiceImpl extends ServiceImpl<StUserMapper, StUser> implements IStUserService {
    @Resource
    StUserMapper stUserMapper;
    @Override
    public Page<StUser> selectUserByDeptId(Page<StUser> page, StUser user) {
        return stUserMapper.selectUserByDeptId(page,  user);
    }
}

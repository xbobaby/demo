package com.bob.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.system.entity.SysUser;
import com.bob.system.mapper.SysUserMapper;
import com.bob.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@Service(version = "${bob.service.version}")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public SysUser getSysUserByUsername(String username){
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>(new SysUser().setUsername(username)));
    }
}

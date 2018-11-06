package com.bob.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.system.entity.SysRole;
import com.bob.system.mapper.SysRoleMapper;
import com.bob.system.service.ISysRoleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@Service(version = "${bob.service.version}")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

}

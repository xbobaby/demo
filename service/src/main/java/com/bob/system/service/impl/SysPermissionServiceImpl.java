package com.bob.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bob.system.entity.SysPermission;
import com.bob.system.mapper.SysPermissionMapper;
import com.bob.system.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@Service(version = "${bob.service.version}")
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

}

package com.bob.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bob.system.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
public interface ISysUserService extends IService<SysUser> {

    public SysUser getSysUserByUsername(String username);

}

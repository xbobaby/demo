package com.bob.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.system.entity.StUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author bob
 * @since 2018-10-14
 */
public interface IStUserService extends IService<StUser> {

    Page<StUser> selectUserByDeptId(Page<StUser> page , StUser user);
}

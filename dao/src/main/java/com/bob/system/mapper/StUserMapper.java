package com.bob.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.system.entity.StUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author bob
 * @since 2018-10-14
 */
public interface StUserMapper extends BaseMapper<StUser> {
    Page<StUser> selectUserByDeptId(@Param("pg") Page<StUser> page , @Param("user") StUser user);

}

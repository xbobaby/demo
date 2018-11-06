package com.bob.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author bob
 * @since 2018-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean available;

    private String description;

    private String role;

    @TableField(exist = false)
    @ApiModelProperty(value="权限集合",hidden=true)
    private List<SysPermission> permissionList;
}

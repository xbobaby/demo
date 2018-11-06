package com.bob.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private String name;

    private String password;

    private String salt;

    private Integer state;

    private String username;

    @TableField(exist = false)
    @ApiModelProperty(value = "令牌",hidden = true)
    private String token;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色集合",hidden = true)
    private List<SysRole> roleList;

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }
}

package com.bob.system.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean available;

    private String name;

    private Long parentId;

    private String parentIds;

    private String permission;

    private String resourceType;

    private String url;


}

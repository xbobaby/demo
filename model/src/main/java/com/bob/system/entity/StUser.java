package com.bob.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author bob
 * @since 2018-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @TableId("PKID")
    private String pkid;

    /**
     * 登录账号
     */
    @TableField("CODE")
    private String code;

    /**
     * 用户姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 办公电话
     */
    @TableField("TELPHONE")
    private String telphone;

    /**
     * 邮件地址
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 登录密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 登录时间
     */
    @TableField("LOGIN_TIME")
    private LocalDateTime loginTime;

    /**
     * 登录IP
     */
    @TableField("LOGIN_IP")
    private String loginIp;

    /**
     * 更新人
     */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 退出时间
     */
    @TableField("LOGOUT_TIME")
    private LocalDateTime logoutTime;

    /**
     * 登录状态
     */
    @TableField("ONLINE_STATUS")
    private String onlineStatus;

    /**
     * 设置允许多账号同时在线,     1：单点登录，2：多点登录
     */
    @TableField("ONLINE_SET")
    private String onlineSet;

    /**
     * 性别
     */
    @TableField("SEX")
    private String sex;

    /**
     * 组织类型 0 擎动 1 渠道
     */
    @TableField("ORG_TYPE")
    private String orgType;

    /**
     * 渠道职务 0 客户经理 1 部门经理
     */
    @TableField("CHAN_POST")
    private String chanPost;

    /**
     * 部门编号 组织类型为擎动写入
     */
    @TableField("DEPT_ID")
    private String deptId;

    /**
     * 渠道编号 组织类型为渠道写入
     */
    @TableField("CHAN_ID")
    private String chanId;

    /**
     * 是否地区限制 0 不限制 1 限制
     */
    @TableField("CITY_LIMIT")
    private String cityLimit;

    /**
     * 是否渠道限制 0 不限制 1 限制
     */
    @TableField("CHAN_LIMIT")
    private String chanLimit;

    /**
     * 是否IP限制，1：限制，0或空：不限制
     */
    @TableField("IP_CONTROL_FLAG")
    private String ipControlFlag;

    /**
     * 用户过期时间(当用户密码过期前N天,提醒用户修改密码)
     */
    @TableField("PWD_EXP_TIME")
    private LocalDateTime pwdExpTime;

    /**
     * 用户预留信息
     */
    @TableField("RESERVE_MSG")
    private String reserveMsg;

    /**
     * 是否需要修改用户密码(譬如是首次登录要强制用户修改密码 1: 需要修改密码 0: 不需要)
     */
    @TableField("NEED_CHG_PWD")
    private String needChgPwd;

    /**
     * sessionId
     */
    @TableField("SESSION_ID")
    private String sessionId;


}

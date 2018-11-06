package com.bob.common.constant;

public enum ERRORCODE {

    /**成功*/
    SUCCESS("000000","成功"),
    /**成功*/
    FAILED("FFFFFF","失败"),

    /**没有登录*/
    UNLOGIN("000001","请登录！"),
    /**登录失败*/
    LOGIN_FAILED("000002","登录失败,请重试！"),
    /**登录失败账号锁定*/
    LOGIN_FAILED_LOCKED("000003","登录失败,账号已锁定！"),
    /**登录失败密码错误*/
    LOGIN_FAILED_PASSWORD("000004","登录失败,密码错误！"),
    /**登录失败，用户不存在*/
    LOGIN_FAILED_NOT_EXIST("000005","登录失败,用户不存在！");
    private String code;
    private String desc;

    ERRORCODE(String errorCode,String errorDesc)
    {
        this.code=errorCode;
        this.desc=errorDesc;
    }

    public String getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }
}

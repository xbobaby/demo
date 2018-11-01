package com.bob.common.constant;

public enum ERRORCODE {

    /**成功*/
    SUCCESS("000000","成功"),
    /**成功*/
    FAILED("FFFFFF","失败");

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

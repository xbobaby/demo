package com.bob.common.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bob.common.constant.CONSTANT;
import com.bob.common.constant.ERRORCODE;

import java.io.Serializable;
import java.util.HashMap;

public class RO<T> extends HashMap<String, Object> implements Serializable {

    public void  setError(String code ,String message){
        put(CONSTANT.RETURN_MESSAGE_KEY.CODE,code);
        put(CONSTANT.RETURN_MESSAGE_KEY.MSG,message);
    }

    public void  setError(ERRORCODE errorCode){
        put(CONSTANT.RETURN_MESSAGE_KEY.CODE,errorCode.getCode());
        put(CONSTANT.RETURN_MESSAGE_KEY.MSG,errorCode.getDesc());
    }

    public RO(){
        put(CONSTANT.RETURN_MESSAGE_KEY.CODE,CONSTANT.RETURN_MESSAGE_KEY.DEFAULT_SUCCESS_CODE);
        put(CONSTANT.RETURN_MESSAGE_KEY.MSG,CONSTANT.RETURN_MESSAGE_KEY.DEFAULT_SUCCESS_MSG);
    }
    public RO(Page<T> page){
/*        this.currentPage=page.getCurrent();
        this.pageSize=page.getSize();
        this.paginationTotal=page.getTotal();
        this.body = page.getRecords();*/
        put(CONSTANT.RETURN_MESSAGE_KEY.CODE,CONSTANT.RETURN_MESSAGE_KEY.DEFAULT_SUCCESS_CODE);
        put(CONSTANT.RETURN_MESSAGE_KEY.MSG,CONSTANT.RETURN_MESSAGE_KEY.DEFAULT_SUCCESS_MSG);
        super.put("page",page);
    }

    public RO put(String code ,Object object){
        super.put(code,object);
        return this;
    }
}

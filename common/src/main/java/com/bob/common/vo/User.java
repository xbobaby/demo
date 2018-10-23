package com.bob.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String sex;

    private String[] authCode;
}

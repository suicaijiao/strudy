package com.study.cae.commons.enums;

/**
 * @description
 * @author: suicaijiao
 * @create: 2020-05-28 13:17
 **/
public enum CommonsEnum {

    IS_DELETE_FALSE(0, "未删除"),
    IS_DELETE_TRUE(1, "已删除"),
    IS_ENABLED_YES(0, "启用"),
    IS_ENABLED_NO(1, "禁用"),
    IS_NO(0, "否"),
    IS_YES(1, "是"),
    ;

    CommonsEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    Integer status;
    String message;

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}



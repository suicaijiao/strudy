package com.study.cae.commons.exception.entity;

import com.study.cae.commons.global.result.ResultCode;

/**
 * 业务异常
 *
 * @author suicaijioa
 * @date 22020-05-23 08:50
 */

public class BusinessException extends RuntimeException {

    //业务类型
    private String bizType;
    //业务代码
    private String bizCode;

    public BusinessException(ResultCode resultEnum) {
        super(resultEnum.getMessage());

        this.bizType = "";
        this.bizCode = resultEnum.getCode();
    }

    public BusinessException(ResultCode resultEnum, String message) {
        super(message);

        this.bizType = "";
        this.bizCode = resultEnum.getCode();
    }

    public BusinessException(ResultCode resultEnum, String message, String bizType) {
        super(message);

        this.bizType = bizType;
        this.bizCode = resultEnum.getCode();
    }


    public BusinessException(ResultCode resultEnum, Throwable cause) {
        super(resultEnum.getMessage(), cause);

        this.bizType = "";
        this.bizCode = resultEnum.getCode();
    }

    public BusinessException(ResultCode resultEnum, String message, Throwable cause) {
        super(message, cause);

        this.bizType = "";
        this.bizCode = resultEnum.getCode();
    }

    public BusinessException(ResultCode resultEnum, String message, String bizType, Throwable cause) {
        super(message, cause);

        this.bizType = bizType;
        this.bizCode = resultEnum.getCode();
    }

    public String getBizType() {
        return bizType;
    }

    public String getBizCode() {
        return bizCode;
    }
}
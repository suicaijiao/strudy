package com.study.cae.commons.global.result;

import lombok.Getter;

/**
 * 错误码格式说明（示例：2000201）：
 * 2-00-02-01
 * 服务级错误（1为系统级错误）
 * 服务模块代码 4位 前2位模块名，如：（sys,stu,ter）后2位服务名（login,register,items）
 * 具体错误代码 2位
 */
public enum ResultCode {
    //(200)
    OK("200", "成功"),
    //region （1）系统级----错误码参照
    //（00-00-00）公用
    SYS_COM_UNKONW("1000", "系统内部异常"),

    SYS_COM_PARAM_ERROR_FAILED("1030", "请求参数不正确"),
    SYS_COM_PARAM_NULL_FAILED("1031", "请求参数不能为空"),
    //(02)查找数据
    SYS_COM_GETDATA_EMPTY_DATA("1020", "暂无数据"),
    SYS_COM_GETDATA_ERROR("1021", "查询错误"),

    // 上传错误
    UPLOAD_ERROR("1031","文件上传失败"),
    UPLOAD_FILE_NULL_ERROR("1032","上传文件不允许为空"),
    UPLOAD_FILEUPLOADTYPE_INVALID("1033", "上传类型无效"),
    UPLOAD_FILE_FILESUFFIX_INVALID("1034", "文件后缀名无效"),

    LOGIN_PASSWORD_ERROR("1040","密码输入错误"),

    SYS_COM_HTTP_ERROR("1050","没有查询结果"),
    ;


    //endregion

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private String code;
    @Getter
    private String message;


}

package com.wz.dianping.common;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
public enum BusinessError {

    /*通用的错误类型10000开头*/
    NO_OBJECT_FOUND(10001,"请求对象不存在"),
    UNKNOWN_ERROR(19999,"未知错误"),
    No_Handler_Found_ERROR(10002,"路径未找到"),
    SERVLET_REQUEST_BINDING_EXCEPTION(10003,"请求参数异常"),
    PARAMETER_VALIDATION_ERROR(10004,"请求参数校验失败"),

    /*用户业务的错误类型20000开头*/
    DUP_KEY_EXCEPTION(20001,"用户已存在"),
    LOGIN_FAIL(20002,"用户不存在"),
    ADMIN_LOGIN_FAIL(20003,"用户未登录"),

    /*品类业务的错误类型30000开头*/
    CREATE_CATEGORY_KEY_EXCEPTION(30001,"该品类已存在"),;

    private Integer errorCode;

    private String errMsg;

    BusinessError(Integer errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

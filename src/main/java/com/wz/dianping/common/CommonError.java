package com.wz.dianping.common;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
public class CommonError {

    /*错误码*/
    private Integer errorCode;


    /*错误描述*/
    private String errMsg;

    public CommonError(Integer errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
    }

    public CommonError(BusinessError businessError){
        this.errorCode = businessError.getErrorCode();
        this.errMsg = businessError.getErrMsg();
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

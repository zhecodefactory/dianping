package com.wz.dianping.common;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
public class BusinessException extends Exception{

    private CommonError commonError;

    public BusinessException(BusinessError businessError){
        super();
        this.commonError = new CommonError(businessError);
    }

    public BusinessException(BusinessError businessError,String errorMsg){
        super();
        this.commonError = new CommonError(businessError);
        this.commonError.setErrMsg(errorMsg);
    }

    public CommonError getCommonError() {
        return commonError;
    }

    public void setCommonError(CommonError commonError) {
        this.commonError = commonError;
    }
}

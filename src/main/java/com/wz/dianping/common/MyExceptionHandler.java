package com.wz.dianping.common;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDao doError(HttpServletRequest request, HttpServletResponse response,Exception e){
        if (e instanceof BusinessException){
            return ResultDao.create(((BusinessException) e).getCommonError(),"FAIL");
        }else if (e instanceof NoHandlerFoundException){
            return ResultDao.create(new CommonError(BusinessError.No_Handler_Found_ERROR),"FAIL");
        }else if (e instanceof ServletRequestBindingException){
            return ResultDao.create(new CommonError(BusinessError.SERVLET_REQUEST_BINDING_EXCEPTION),"FAIL");
        } else {
            return ResultDao.create(((BusinessException)e).getCommonError(),"FAIL");
        }
    }

}

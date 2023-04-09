package com.wz.dianping.common;

import com.wz.dianping.controller.admin.AdminController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
@Aspect
@Configuration
public class ControllerAspect {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    @Around("execution(* com.wz.dianping.controller.admin.*.*(..))&&@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object adminControllerBeforeValidation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AdminPermission annotation = method.getAnnotation(AdminPermission.class);
        if (annotation==null){
            Object proceed = joinPoint.proceed();
            return proceed;
        }
        /*判断当前管理员是否登录*/
        String email = (String) httpServletRequest.getSession().getAttribute(AdminController.CURRENT_ADMIN_SESSION);
        if (email==null){
            if (annotation.responseType().equals("text/html")){
                httpServletResponse.sendRedirect("/admin/admin/loginpage");
                return null;
            }else {
                CommonError commonError = new CommonError(BusinessError.ADMIN_LOGIN_FAIL);
                return commonError;
            }

        }else{
            Object proceed = joinPoint.proceed();
            return proceed;
        }
    }

}

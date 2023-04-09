package com.wz.dianping.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.function.Consumer;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
public class CommonUtil {

    public static String processErrorString(BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        bindingResult.getFieldErrors().stream()
                .forEach(fieldError -> stringBuilder.append(fieldError.getDefaultMessage()+","));

        return stringBuilder.toString();
    }

}

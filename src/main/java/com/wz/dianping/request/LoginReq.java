package com.wz.dianping.request;

import javax.validation.constraints.NotBlank;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.request
 * 备注：
 **/
public class LoginReq {

    @NotBlank(message = "手机号不能为空")
    private String telphone;
    @NotBlank(message = "password不能为空")
    private String password;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

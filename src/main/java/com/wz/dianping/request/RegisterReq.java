package com.wz.dianping.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.request
 * 备注：
 **/
public class RegisterReq {

    @NotBlank(message = "手机号不能为空")
    private String telphone;
    @NotBlank(message = "password不能为空")
    private String password;
    @NotBlank(message = "nickName不能为空")
    private String nickName;
    @NotNull(message = "gender不能为空")
    private Integer gender;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterReq{" +
                "telphone='" + telphone + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                '}';
    }
}

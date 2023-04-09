package com.wz.dianping.request;

import javax.validation.constraints.NotBlank;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.request
 * 备注：
 **/
public class SellerReq {

    @NotBlank(message = "商户名不能为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

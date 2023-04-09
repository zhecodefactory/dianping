package com.wz.dianping.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 作者：王哲
 * 时间：2023049/4/2023
 * 包名：com.wz.dianping.request
 * 备注：
 **/
public class CategoryReq {
    @NotBlank(message = "类别名称不能为空")
    private String name;
    @NotBlank(message = "iconUrl不能为空")
    private String iconUrl;
    @NotNull(message = "sort不能为空")
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

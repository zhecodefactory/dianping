package com.wz.dianping.request;

/**
 * 作者：王哲
 * 时间：2023048/4/2023
 * 包名：com.wz.dianping.request
 * 备注：
 **/
public class PageQuery {
    private Integer page = 1;
    private Integer size = 20;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

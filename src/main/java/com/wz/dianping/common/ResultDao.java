package com.wz.dianping.common;

/**
 * 作者：王哲
 * 时间：2023045/4/2023
 * 包名：com.wz.dianping.common
 * 备注：
 **/
public class ResultDao {

    /*定义返回状态*/
    private String status;

    /*定义携带数据属性*/
    private Object data;

    /*定义通用返回对象的方法*/
    public static ResultDao successResultDao(Object data){
        return create(data,"success");
    }

    public static ResultDao create(Object data,String status){
        ResultDao resultDao = new ResultDao();
        resultDao.setData(data);
        resultDao.setStatus(status);
        return resultDao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

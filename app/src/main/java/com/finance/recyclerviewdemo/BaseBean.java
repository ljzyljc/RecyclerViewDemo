package com.finance.recyclerviewdemo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackie on 2018/7/9.
 */
public class BaseBean<T> implements Serializable{
    private int status;
    private String errMsg;
    private List<T> data;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }

}

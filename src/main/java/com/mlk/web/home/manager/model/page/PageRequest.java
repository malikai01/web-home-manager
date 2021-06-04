package com.mlk.web.home.manager.model.page;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author malikai
 * @date 2021-5-21 17:10
 */
public class PageRequest<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -7391097880029846491L;

    private int pageNum = 1;
    private int pageSize = 20;

    private T paramData;

    public PageRequest() {
    }

    public PageRequest(int pageNum, int pageSize) {
        int pageNumTemp = pageNum <= 0 ? 1 : pageNum;
        int pageSizeTemp = pageSize <= 0 ? 1 : pageSize;
        this.pageNum = pageNumTemp;
        this.pageSize = pageSizeTemp;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum <= 0 ? 1 : pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 1 : pageSize;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public T getParamData() {
        return paramData;
    }

    public void setParamData(T paramData) {
        this.paramData = paramData;
    }

}

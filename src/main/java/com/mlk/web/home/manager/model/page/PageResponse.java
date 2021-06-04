package com.mlk.web.home.manager.model.page;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author malikai
 * @date 2021-5-21 17:09
 */
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = -7391097880029846491L;


    private List<T> resultData;
    private Pagination pagination = new Pagination();

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public List<T> getResultData() {
        return resultData;
    }

    public void setResultData(List<T> resultData) {
        this.resultData = resultData;
    }

}

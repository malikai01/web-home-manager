package com.mlk.web.home.manager.model.page;

import java.io.Serializable;

/**
 * 分页基本信息
 *
 * @author malikai
 * @date 2021-5-21 17:09
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 3568653751004413244L;
    private long totalCount = 0;
    private int pageSize = 20;
    private String sortColumn;
    private int currentPageIndex;
    private int sortEnum;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getSortEnum() {
        return sortEnum;
    }

    public void setSortEnum(int sortEnum) {
        this.sortEnum = sortEnum;
    }
}

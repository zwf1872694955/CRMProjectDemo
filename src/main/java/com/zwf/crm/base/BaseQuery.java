package com.zwf.crm.base;


public class BaseQuery {
    private Integer page=1;  //当前页面
    private Integer limit=10; //每页记录数

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
